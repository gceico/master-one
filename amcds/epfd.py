import time
from threading import Timer
from utils import getKeyForProcess
from message_pb2 import Message, EpfdSuspect, EpfdRestore, PlSend, ProcessId

delta = 0.1


class Epfd:

    def __init__(self, system):
        self.delay = delta
        self.system = system
        self.suspected = dict()
        self.alive = system.processes.copy()

        self.startTimer()

    def getId(self):
        return 'epfd'

    def timerCallback(self):
        message = Message()
        message.abstractionId = 'epfd'
        message.type = Message.Type.EPFD_TIMEOUT

        self.system.events.append(message)

    def startTimer(self):
        t = Timer(self.delay, self.timerCallback)
        t.start()

    def handle(self, m: Message):
        if m.type == Message.Type.EPFD_TIMEOUT:
            self.epdfTimeout()
            return True
        elif m.type == Message.Type.PL_DELIVER:
            x = m.plDeliver
            if x.message.type == Message.Type.EPFD_HEARTBEAT_REQUEST:
                self.plDeliverHeartbeatRequest(x.sender)
                return True
            elif x.message.type == Message.Type.EPFD_HEARTBEAT_REPLY:
                self.plDeliverHeartbeatReply(x.sender)
                return True
        return False

    def epdfTimeout(self):
        if len(self.alive.keys() & self.suspected.keys()):
            self.delay += delta
        for p in self.system.processes.values():
            key = getKeyForProcess(p)
            if (not key in self.alive.keys()) and (not key in self.suspected.keys()):
                self.suspected[key] = p

                message = Message()
                message.abstractionId = 'eld'
                message.epfdSuspect.process.CopyFrom(p)
                message.type = Message.Type.EPFD_SUSPECT

                self.system.events.append(message)

            elif (key in self.alive.keys()) and (key in self.suspected.keys()):
                del self.suspected[key]

                message = Message()
                message.abstractionId = 'eld'
                message.epfdRestore.process.CopyFrom(p)
                message.type = Message.Type.EPFD_RESTORE

                self.system.events.append(message)

            message = Message()

            message.type = Message.Type.PL_SEND
            message.plSend.destination.CopyFrom(p)
            message.plSend.message.abstractionId = 'epfd'
            message.plSend.message.type = Message.Type.EPFD_HEARTBEAT_REQUEST

            self.system.events.append(message)

        self.alive.clear()
        self.startTimer()

    def plDeliverHeartbeatRequest(self, p: ProcessId):
        message = Message()
        message.plSend.destination.CopyFrom(p)
        message.type = Message.Type.PL_SEND
        message.plSend.message.abstractionId = 'epfd'
        message.plSend.message.type = Message.Type.EPFD_HEARTBEAT_REPLY

        self.system.events.append(message)

    def plDeliverHeartbeatReply(self, p: ProcessId):
        key = getKeyForProcess(p)
        self.alive[key] = p

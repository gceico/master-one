from utils import getKeyForProcess, getMax
from message_pb2 import Message, ProcessId, EldTrust


class Eld:

    def __init__(self, system):
        self.leader = None
        self.system = system
        self.suspected = dict()

    def getId(self):
        return 'eld'

    def handle(self, m: Message):
        if m.type == Message.Type.EPFD_SUSPECT:
            self.epfdSuspect(m.epfdSuspect.process)
            return True
        elif m.type == Message.Type.EPFD_RESTORE:
            self.epfdRestore(m.epfdRestore.process)
            return True
        return False

    def epfdSuspect(self, p: ProcessId):
        key = getKeyForProcess(p)
        self.suspected[key] = p
        self.check()

    def epfdRestore(self, p: ProcessId):
        key = getKeyForProcess(p)
        if key in self.suspected.keys():
            del self.suspected[key]
            self.check()

    def check(self):
        notSuspected = dict()
        for key in self.system.processes.keys():
            if key not  in self.suspected.keys():
                notSuspected[key] = self.system.processes[key]
                
        maximum = getMax(notSuspected)
        if not maximum:
            return
        
        if not self.leader or (not self.leader.rank == maximum.rank):
            self.leader = maximum

            message = Message()
            message.abstractionId = 'ec'
            message.type = Message.Type.ELD_TRUST
            message.eldTrust.process.CopyFrom(maximum)
            self.system.events.append(message)

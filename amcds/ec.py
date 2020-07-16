from utils import getKeyForProcess, getMax
from message_pb2 import Message, ProcessId, BebBroadcast, EcNewEpoch_, EcStartEpoch, PlSend


class Ec:

    def __init__(self, system):
        self.lastts = 0
        self.system = system
        self.ts = system.me.rank
        self.trusted = getMax(system.processes)

    def getId(self):
        return 'ec'

    def handle(self, m: Message):
        if m.type == Message.Type.ELD_TRUST:
            self.eldTrust(m.eldTrust.process)
            return True
        elif m.type == Message.Type.BEB_DELIVER:
            x = m.bebDeliver
            if x.message.type == Message.Type.EC_NEW_EPOCH_:
                self.bebDeliver(x.sender, x.message.ecNewEpoch_.timestamp)
                return True
        elif m.type == Message.Type.PL_DELIVER:
            x = m.plDeliver
            if x.message.type == Message.Type.EC_NACK_:
                self.plDeliver(x.sender)
                return True
        return False

    def eldTrust(self, p: ProcessId):
        self.trusted = p
        if getKeyForProcess(p) == getKeyForProcess(self.system.me):
            self.ts += len(self.system.processes)

            message = Message()
            bebBroadcast = BebBroadcast()

            bebBroadcast.message.abstractionId = 'ec'
            bebBroadcast.message.ecNewEpoch_.timestamp = self.ts
            bebBroadcast.message.type = Message.Type.EC_NEW_EPOCH_

            message.abstractionId = 'beb'
            message.bebBroadcast.CopyFrom(bebBroadcast)
            message.type = Message.Type.BEB_BROADCAST
            self.system.events.append(message)

    def bebDeliver(self, p: ProcessId, newTs: int):
        if getKeyForProcess(p) == getKeyForProcess(self.trusted) and newTs > self.lastts:
            message = Message()

            message.abstractionId = 'uc'
            message.ecStartEpoch.newLeader.CopyFrom(p)
            message.ecStartEpoch.newTimestamp = newTs
            message.type = Message.Type.EC_START_EPOCH
            self.system.events.append(message)
        else:
            self.lastts = newTs
            message = Message()

            message.plSend.destination.CopyFrom(p)
            message.type = Message.Type.PL_SEND
            message.plSend.message.abstractionId = 'ec'
            message.plSend.message.type = Message.Type.EC_NACK_

            self.system.events.append(message)

    def plDeliver(self, p: ProcessId):
        if getKeyForProcess(self.trusted) == getKeyForProcess(self.system.me):
            self.ts += len(self.system.processes)
            message = Message()
            bebBroadcast = BebBroadcast()

            bebBroadcast.message.abstractionId = 'ec'
            bebBroadcast.message.ecNewEpoch_.timestamp = self.ts
            bebBroadcast.message.type = Message.Type.EC_NEW_EPOCH_

            message.abstractionId = 'beb'
            message.bebBroadcast.CopyFrom(bebBroadcast)
            message.type = Message.Type.BEB_BROADCAST

            self.system.events.append(message)

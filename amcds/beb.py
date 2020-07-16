from message_pb2 import Message, PlSend, BebDeliver, ProcessId


class Beb:

    def __init__(self, system):
        self.system = system

    def getId(self):
        return 'beb'

    def handle(self, m: Message):

        if m.type == Message.Type.BEB_BROADCAST:
            x = m.bebBroadcast
            self.bebBroadcast(x)
            return True
        elif m.type == Message.Type.PL_DELIVER:
            if m.abstractionId != self.getId():
                return False
            x = m.plDeliver
            self.plDeliver(x.sender, x.message)
            return True
        return False

    def bebBroadcast(self, m):
        for p in self.system.processes.values():
            message = Message()
            plSend = PlSend()
            aId = m.message.abstractionId

            plSend.destination.CopyFrom(p)
            plSend.message.CopyFrom(m.message)

            message.abstractionId = aId
            message.plSend.CopyFrom(plSend)
            message.type = Message.Type.PL_SEND
            self.system.events.append(message)

    def plDeliver(self, p: ProcessId, m):
        message = Message()

        message.bebDeliver.sender.CopyFrom(p)
        message.bebDeliver.message.CopyFrom(m)
        message.abstractionId = m.abstractionId
        message.type = Message.Type.BEB_DELIVER
        
        self.system.events.append(message)

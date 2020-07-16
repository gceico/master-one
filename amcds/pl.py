from utils import  getKeyForProcess, send
from message_pb2 import Message, NetworkMessage


class Pl:
    def __init__(self, system):
        self.system = system

    def getId(self):
        return 'pl'

    def handle(self, m):
        if m.type == Message.Type.PL_SEND:
            self.plSend(m.plSend, m.abstractionId, m.systemId)
            return True

    def plSend(self, pS, aId, sId):
        m = Message()
        nm = NetworkMessage()

        nm.message.CopyFrom(pS.message)
        nm.senderHost = self.system.me.host
        nm.senderListeningPort = self.system.me.port

        m.abstractionId = aId
        m.networkMessage.CopyFrom(nm)
        m.systemId = self.system.systemId
        m.type = Message.Type.NETWORK_MESSAGE

        send(pS.destination.host, pS.destination.port, m)

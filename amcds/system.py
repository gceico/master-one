import time
import uuid
import socket
import struct
import threading
import traceback
from app import App
from utils import getProcessForKey, getKeyForProcess, send
from message_pb2 import Message, ProcessId, NetworkMessage


class System:
    def __init__(self, port, index, owner, hubPort):
        self.hub = ProcessId()
        self.hub.port = hubPort
        self.hub.host = '127.0.0.1'

        self.me = ProcessId()
        self.me.port = port
        self.me.owner = owner
        self.me.index = index
        self.me.host = '127.0.0.1'

        self.events = []
        self.systemId = 'sys-1'
        self.processes = dict()
        self.networkMessages = []
        self.abstractions = [App(self)]
        self.lockNetworkMessages = threading.Lock()

    def registerApp(self):
        message = Message()
        networkMessage = NetworkMessage()
        networkMessage.senderHost = self.me.host
        networkMessage.senderListeningPort = self.me.port
        networkMessage.message.type = Message.Type.APP_REGISTRATION
        networkMessage.message.appRegistration.owner = self.me.owner
        networkMessage.message.appRegistration.index = self.me.index
        message.type = Message.Type.NETWORK_MESSAGE
        message.networkMessage.CopyFrom(networkMessage)
        send(self.hub.host, self.hub.port, message)

    def mainLoop(self):
        while True:
            for i in range(len(self.events)):
                handled = False
                for a in self.abstractions:
                    if not handled:
                        handled = a.handle(self.events[i])
                        x = a.getId()
                if handled:
                    del self.events[i]
                    break

            m = None
            self.lockNetworkMessages.acquire()
            if len(self.networkMessages):
                m = self.networkMessages.pop()
            self.lockNetworkMessages.release()
            if m:
                if m.type == Message.Type.NETWORK_MESSAGE:
                    x = m.networkMessage
                    sender = getProcessForKey(self.processes, x.senderHost, x.senderListeningPort)
                    self.systemId = m.systemId
                    if x.message.type == Message.Type.APP_PROPOSE:
                        self.events.append(x.message)
                    elif sender:
                        message = Message()
                        message.type = Message.Type.PL_DELIVER
                        message.abstractionId = m.abstractionId
                        message.plDeliver.message.CopyFrom(x.message)
                        message.plDeliver.sender.CopyFrom(sender)
                        self.events.append(message)

    def server(self):
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        s.bind((self.me.host, self.me.port))
        s.listen(100)

        while True:
            conn, addr = s.accept()
            n = struct.unpack('>I', conn.recv(4))[0]
            raw = struct.unpack(">%ds" % n, conn.recv(n))[0]
            
            if raw:
                message = Message()
                message.ParseFromString(raw)

                self.lockNetworkMessages.acquire()
                self.networkMessages.append(message)
                self.lockNetworkMessages.release()
            conn.close()
        s.close()

    def main(self):
        server = threading.Thread(target=self.server, args=())
        server.start()

        self.registerApp()
        self.mainLoop()
        server.join()

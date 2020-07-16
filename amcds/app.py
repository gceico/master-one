from utils import send
from utils import getKeyForProcess
from message_pb2 import AppDecide,  UcPropose, Value, PlSend, Message
from uc import Uc
from ec import Ec
from ep import Ep
from pl import Pl
from eld import Eld
from beb import Beb
from epfd import Epfd


class App:

    def __init__(self, system):
        self.system = system

    def getId(self):
        return 'app'

    def handle(self, m: Message):
        if m.type == Message.Type.APP_PROPOSE:
            x = m.appPropose
            print("APP_PROPOSE", self.system.me.index, "-> ", x.value.v)
            self.appPropose(x.value, x.processes)
            return True
        if m.type == Message.Type.UC_DECIDE:
            x = m.ucDecide
            print("UC_DECIDE", self.system.me.index, " -> ", x.value.v)
            self.ucDecide(x.value)
            return True

    def appPropose(self, v: Value, processes: []):
        for p in processes:
            key = getKeyForProcess(p)
            self.system.processes[key] = p
            if key == getKeyForProcess(self.system.me):
                self.system.me.rank = p.rank

        if len(processes):
            self.system.abstractions.append(Pl(self.system))
            self.system.abstractions.append(Ec(self.system))
            self.system.abstractions.append(Uc(self.system))
            self.system.abstractions.append(Eld(self.system))
            self.system.abstractions.append(Beb(self.system))
            self.system.abstractions.append(Epfd(self.system))
        
        m = Message()
        m.abstractionId = 'uc'
        m.ucPropose.value.CopyFrom(v)
        m.type = Message.Type.UC_PROPOSE
        self.system.events.append(m)

    def ucDecide(self, v: Value):
        m = Message()
        appDecide = Message()

        appDecide.appDecide.value.CopyFrom(v)
        appDecide.type = Message.Type.APP_DECIDE

        m.type = Message.Type.PL_SEND
        m.plSend.message.CopyFrom(appDecide)
        m.plSend.destination.CopyFrom(self.system.hub)

        self.system.events.append(m)
        self.system.abstractions = self.system.abstractions[0:2]
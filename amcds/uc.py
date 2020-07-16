from utils import getMax
from ep import EpState, Ep
from message_pb2 import Message, EpPropose, EpAbort,   Value,  UcDecide


class Uc:

    def __init__(self, system):
        self.ets = 0
        self.newts = 0
        self.newl = None
        self.val = Value()
        self.system = system
        self.decided = False
        self.proposed = False
        self.l = getMax(system.processes)

        self.val.v = 0
        self.val.defined = False
        
        self.system.abstractions.append(
            Ep(self.system, self.ets, self.l, EpState(0, self.val)))

    def getId(self):
        return "uc"

    def handle(self, m: Message):
        if m.type == Message.Type.UC_PROPOSE:
            x = m.ucPropose
            self.ucPropose(x.value)
            return True
        elif m.type == Message.Type.EC_START_EPOCH:
            x = m.ecStartEpoch
            self.ecStartEpoch(x.newTimestamp, x.newLeader)
            return True
        elif m.type == Message.Type.EP_ABORTED:
            x = m.epAborted
            self.epAborted(x.ets, x.valueTimestamp, x.value)
            return True
        elif m.type == Message.Type.EP_DECIDE:
            x = m.epDecide
            self.epDecide(x.ets, x.value)
            return True
        return False

    def ucPropose(self, v: Value):
        self.val = v
        self.check()

    def ecStartEpoch(self, newts, newl):
        self.newts = newts
        self.newl = newl
        message = Message()
        message.type = Message.Type.EP_ABORT
        message.abstractionId = 'ep' + self.ets.__str__()

        self.system.events.append(message)
        self.check()

    def epAborted(self, ts, valts, v: Value):
        if self.ets == ts:
            self.ets = self.newts
            self.l = self.newl
            self.proposed = False

            state = EpState(valts, v)
            ep = Ep(self.system, self.ets, self.l, state)
            self.system.abstractions.append(ep)

            self.check()

    def epDecide(self, ts, v: Value):
        if self.ets == ts:
            if self.decided == False:
                self.decided = True
                message = Message()
                ucDecide = UcDecide()

                ucDecide.value.CopyFrom(v)
                message.ucDecide.CopyFrom(ucDecide)
                message.type = Message.Type.UC_DECIDE

                self.system.events.append(message)
            self.check()

    def check(self):
        if self.l == self.system.me and self.val.defined and self.proposed == False:
            self.proposed = True
            message = Message()

            epPropose = EpPropose()
            message.abstractionId = 'ep' + self.ets.__str__()
            epPropose.value.CopyFrom(self.val)
            message.epPropose.CopyFrom(epPropose)
            message.type = Message.Type.EP_PROPOSE

            self.system.events.append(message)

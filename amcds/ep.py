from utils import getKeyForProcess
from message_pb2 import Message, ProcessId, EpAborted, EpDecided_, EpState_, EpDecide, BebBroadcast, Value, PlSend


class EpState:
    def __init__(self, valts: int, val: Value):
        self.valts = valts
        self.val = Value()
        self.val.CopyFrom(val)


class Ep:
    def __init__(self, system, ets: int, l: ProcessId, state: EpState):
        self.l = l
        self.ets = ets
        self.state = state

        self.accepted = 0
        self.halted = False
        self.states = dict()
        self.system = system
        self.tmpval = Value()

        self.tmpval.v = 0
        self.tmpval.defined = False

    def getId(self):
        return "ep" + self.ets.__str__()

    def handle(self, m: Message):
        if self.halted:
            return False
        if m.type == Message.Type.EP_PROPOSE:
            if m.abstractionId != self.getId():
                return False
            self.propose(m.epPropose.value)
            return True
        elif m.type == Message.Type.BEB_DELIVER:
            x = m.bebDeliver
            if x.message.abstractionId != self.getId():
                return False
            if x.message.abstractionId != self.getId():
                return False
            if x.message.type == Message.Type.EP_READ_:
                self.bebDeliverRead(x.sender)
                return True
            elif x.message.type == Message.Type.EP_WRITE_:
                self.bebDeliverWrite(x.sender, x.message.epWrite_.value)
                return True
            elif x.message.type == Message.Type.EP_DECIDED_:
                self.bebDeliverDecided(x.sender, x.message.epDecided_.value)
                return True
        elif m.type == Message.Type.PL_DELIVER:
            x = m.plDeliver
            if x.message.abstractionId != self.getId():
                return False
            if x.message.type == Message.Type.EP_STATE_:
                self.plDeliverState(
                    x.sender, x.message.epState_.valueTimestamp, x.message.epState_.value)
                return True
            elif x.message.type == Message.Type.EP_ACCEPT_:
                self.plDeliverAccept(x.sender)
                return True
        elif m.type == Message.Type.EP_ABORT:
            if m.abstractionId != self.getId():
                return False
            self.abort()
            return True
        return False

    def propose(self, v: Value):
        self.tmpval = v
        message = Message()

        message.abstractionId = 'beb'
        message.type = Message.Type.BEB_BROADCAST
        message.bebBroadcast.message.abstractionId = self.getId()
        message.bebBroadcast.message.type = Message.Type.EP_READ_

        self.system.events.append(message)

    def bebDeliverRead(self, l: ProcessId):
        message = Message()

        message.plSend.destination.CopyFrom(l)
        message.type = Message.Type.PL_SEND
        message.plSend.message.abstractionId = self.getId()
        message.plSend.message.type = Message.Type.EP_STATE_
        message.plSend.message.epState_.value.CopyFrom(self.state.val)
        message.plSend.message.epState_.valueTimestamp = self.state.valts

        self.system.events.append(message)

    def bebDeliverWrite(self, l: ProcessId, v: Value):
        self.state.valts = self.ets
        self.state.val = v
        message = Message()

        message.plSend.destination.CopyFrom(l)
        message.type = Message.Type.PL_SEND
        message.plSend.message.abstractionId = self.getId()
        message.plSend.message.type = Message.Type.EP_ACCEPT_

        self.system.events.append(message)

    def plDeliverAccept(self, q: ProcessId):
        self.accepted += 1

        self.checkAccepted()

    def bebDeliverDecided(self, l: ProcessId, v: Value):
        message = Message()
        message.epDecide.ets = self.ets
        message.epDecide.value.CopyFrom(v)
        message.abstractionId = self.getId()
        message.type = Message.Type.EP_DECIDE

        self.system.events.append(message)

    def plDeliverState(self, q: ProcessId, ts, v):
        key = getKeyForProcess(q)
        self.states[key] = EpState(ts, v)

        self.checkStates()

    def abort(self):
        self.halted = True
        message = Message()
        message.abstractionId = self.getId()
        message.type = Message.Type.EP_ABORTED
        message.epAborted.value.CopyFrom(self.state.val)
        message.epAborted.valueTimestamp = self.state.valts
        self.system.events.append(message)

    def checkStates(self):
        if len(self.states) > len(self.system.processes)/2:
            st = self.highest()
            if st.val.defined:
                self.tmpval = st.val
            for k in self.states.keys():
                del self.states[k]

            message = Message()

            message.abstractionId = 'beb'
            message.type = Message.Type.BEB_BROADCAST
            message.bebBroadcast.message.abstractionId = self.getId()
            message.bebBroadcast.message.type = Message.Type.EP_WRITE_
            message.bebBroadcast.message.epWrite_.value.CopyFrom(self.tmpval)

            self.system.events.append(message)

    def checkAccepted(self):
        if self.accepted > len(self.system.processes)/2:
            self.accepted = 0
            message = Message()
            bB = BebBroadcast()

            bB.message.abstractionId = self.getId()
            bB.message.type = Message.Type.EP_DECIDED_
            bB.message.epDecided_.value.CopyFrom(self.tmpval)

            message.abstractionId = 'beb'
            message.bebBroadcast.CopyFrom(bB)
            message.type = Message.Type.BEB_BROADCAST

            self.system.events.append(message)

    def highest(self):
        v = Value()
        v.v = 0
        v.defined = False

        res = EpState(0, v)
        for st in self.states:
            if st.valts > res.valts:
                res = st
        return res

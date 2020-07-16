
import sys
import uuid
import time
import socket
import struct
from message_pb2 import Message, ProcessId


def send(host: str, port: int, m: Message):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.settimeout(1)
    s.connect((host, port))

    s.send(struct.pack('>I', m.ByteSize()))
    s.send(m.SerializeToString())
    s.close()


def getProcessForKey(processes,  host, port):
    for p in processes.values():
        if p.host == host and p.port == port:
            return p
    return None


def getKeyForProcess(p: ProcessId):
    if p:
        return str(p.host) + ':' + str(p.port)
    else:
        return False


def getMax(processes):
    if len(processes):
        return max(processes.values(), key=lambda k: k.rank)
    return None

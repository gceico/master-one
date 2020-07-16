import threading
from system import System
from random import randint

process1 = threading.Thread(target=System(randint(5101, 5200), 1, 'ceico', 5000).main, args=())
process2 = threading.Thread(target=System(randint(5201, 5300), 2, 'ceico', 5000).main, args=())
process3 = threading.Thread(target=System(randint(5301, 5400), 3, 'ceico', 5000).main, args=())

process1.start()
process2.start()
process3.start()

process1.join()
process2.join()
process3.join()


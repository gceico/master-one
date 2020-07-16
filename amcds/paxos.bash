rm -rf ./paxos.log
lsof -nti:5000 | xargs kill -9
lsof -nti:5001 | xargs kill -9
lsof -nti:5002 | xargs kill -9
lsof -nti:5003 | xargs kill -9
./paxos 127.0.0.1  5000  127.0.0.1  5001 5002 5003

# Simple Consensus

## Model: Fail-Noisy

### app Custom-made application

### uc UniformConsensus Leader-Driven 5.7 225

### ep EpochConsensus Read/Write Epoch 5.6 223

### ec EpochChange Leader-Based 5.5 219

### beb BestEffortBroadcast Basic 3.1 76

### eld EventualLeaderDetector Elect Lower Epoch 2.8 57

### epfd EventuallyPerfectFailureDetector Increasing Timeout 2.7 55

### pl PerfectLink (use TCP)

     +---------- app
     |            |
     |           uc
     |          /  \
     |        ec   ep,ep,ep,ep
     |      /| \  / \
     |  eld  | beb   |
     |   \    \  |  /
     |    +- pl -+
     |       |
     +------+

1. The communication is done using the Google Protobuffer 3.x messages defined below, over TCP. The exchange will be
   asynchronous. When sending a request/response, open the TCP connection, send the message, then close the connection.
   When listening for requests, get the request, then close the socket.

2. The system consists of several processes and one hub. Your job is to implement the processes. The hub and reference
   process binaries will be provided to you by the instructor.

3. Process referencing: Upon starting, a process will connect to the hub and register sending: owner alias, process
   index, process host, process listening port (see AppRegistration). The hub address and port will be configured manually.

4. The evaluation will be done as follows: - Share your screen with the instructor - Start the reference hub and processes along with 3 processes of your implementation
   _HUB HOST + PORT PROCESSES HOST + PORTS_
    > paxos.exe 127.0.0.1 5000 127.0.0.1 5001 5002 5003
    > May 18 12:17:01.474 INF Hub listening on 127.0.0.1:5000
    > May 18 12:17:01.475 INF ref-2: listening on 127.0.0.1:5002
    > May 18 12:17:01.475 INF ref-3: listening on 127.0.0.1:5003
    > May 18 12:17:01.475 INF ref-1: listening on 127.0.0.1:5001
    > May 18 12:17:11.475 INF abc-2: listening on 127.0.0.1:5005
    > May 18 12:17:11.475 INF abc-3: listening on 127.0.0.1:5006
    > May 18 12:17:11.475 INF abc-1: listening on 127.0.0.1:5004 - Assuming your process owner is "abc", here is a walkthrough what you can do at the command prompt > help

## Commands:

-   list - list the nodes (hub only)
-   log [info|debug|trace] - set logging level
-   test owner1 owner2 ... - test owners nodes (hub only)
-   quit - quit the program
-   help - show usage > log info > list
    +---+-------+-----------+--------+--------+--------+
    | # | OWNER | HOST | PORT 1 | PORT 2 | PORT 3 |
    +---+-------+-----------+--------+--------+--------+
    | 1 | ref | 127.0.0.1 | 5001 | 5002 | 5003 |
    +---+-------+-----------+--------+--------+--------+
    | 2 | abc | 127.0.0.1 | 5004 | 5005 | 5006 |
    +---+-------+-----------+--------+--------+--------+ > test ref abc

## Testing ref

> 17:10:17.355 INF sys-1/ref-1 will propose 23
> 17:10:17.356 INF sys-1/ref-2 will propose 74
> 17:10:17.357 INF Starting system sys-1 of process ref-1 ...
> 17:10:17.357 INF sys-1/ref-1: Starting consensus among ref-1, ref-2, ref-3
> 17:10:17.358 INF sys-1/ref-3 will propose 97
> 17:10:17.358 INF Starting system sys-1 of process ref-2 ...
> 17:10:17.358 INF sys-1/ref-2: Starting consensus among ref-1, ref-2, ref-3
> 17:10:17.359 INF Starting system sys-1 of process ref-3 ...
> 17:10:17.359 INF sys-1/ref-3: Starting consensus among ref-1, ref-2, ref-3
> 17:10:17.376 INF sys-1/ref-1 decided 97
> 17:10:17.378 INF sys-1/ref-2 decided 97
> 17:10:17.382 INF sys-1/ref-3 decided 97
> quit
> 17:11:39.573 INF Stopping process ref-1 ...
> 17:11:39.648 INF Stopping process ref-2 ...
> 17:11:39.649 INF Stopping process ref-3 ...
> 17:11:39.649 INF Stopping hub ...
> 17:11:39.650 ERR Failed to send NETWORK_MESSAGE to ref-3 error="write tcp 127.0.0.1:55285->127.0.0.1:5003: wsasend: An existing connection was forcibly closed by the remote host."
> 17:11:40.420 INF Stopped - A few comments on how this works - Log level debug shows all messages except for those related to heartbeat - Log level trace will show everything - The test logs right over the command prompt, but you can always type "blindly" and hit ENTER. This may become
> necessary if the trace logging is too much; just type "log debug" and hit ENTER. - When the algorithm is over, it will seem stuck, but in fact is just waiting for another command. Hit ENTER
> and you will see the prompt again. - Look for INF log entries showing what each process has decided - Everything you see in the console is also logged in file paxos.log - The errors after quit are caused by the stopping heartbeat exchange, and can be ignoreed

## Message sending

-   Network-traveling message
-   When handling MessageA(PlSend(MessageB)) create MessageC(NetworkMessage(MessageB)), setting:
-   MessageC.SystemId = MessageA.SystemId
-   MessageC.AbstractionId = MessageA.AbstractionId
-   NetworkMessage.senderHost = N/A (ignore)
-   NetworkMessage.senderListeningPort = The your listening port
-   Then marshal MEssageC to byte buffer and send:
-   bytes 0 - 3: buffer length
-   bytes 4 - : buffer data
-   When unmarshalling from a buffer received from the network create MessageD(PlDeliver(MessageB)), setting:
-   MessageD.AbstractionId = MessageC.AbstractionId

> message NetworkMessage {
> string senderHost = 1;
> int32 senderListeningPort = 2;
> Message message = 3;
> }

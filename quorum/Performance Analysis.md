# Data Collection and Performance Analysis
Perform Data Collection and Analysis for the Distributed Quorum based Mutual Exclusion

### Overview
1. Model has 7 Quorum Servers, 5 Clients and 1 File Server
2. Data Collection and Analysis has been done for the below
    * Total number of Messages Sent by Quorum, Client & File Server(s)
   * Total number of Messages Received by Quorum, Client & File Server(s)
   * Total Number of Messages Exchanged, Latency Per Critical Section
   * Repeated with varying values and reported their impact on latency
      * Time between a client exiting its critical section and issuing its next request
      * Time spent in the critical section
   * Report any Deadlock situation

### Detailed Analysis
 All the Data Collection points are logged for each node in _distributed-quorum-system/quorum/datalog_ folder for _Client_, _Server_ and _Quorum_ nodes
   * **Total number of Messages Sent by**
      * Quorum Servers
      * Client Servers
      * File Server
   * **Total number of Messages Received by**
      * Quorum Servers
      * Client Servers
      * File Server
   * **Per Critical Section**
      * Total Number of Messages Exchanged
      * Latency (Total elapsed time between making a request and being able to enter the critical section)
   * **Repeated with varying values and reported their impact on latency**
      * Time between a client exiting its critical section and issuing its next request
      * Time spent in the critical section
   * **Report any Deadlock situation**
      * If any Deadlock has been detected at a Client, the details are logged in _distributed-quorum-system/quorum/datalog/client/Client<clientID>.txt_ file

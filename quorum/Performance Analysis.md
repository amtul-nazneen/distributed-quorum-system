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
 #### Round 1
   * **Variable Fields**
      * Time between a client exiting its critical section and issuing its next request: 2-5 sec
      * Time spent in the critical section: 1-3 sec
   * **Total number of Messages Sent by**
      * Quorum Servers: 48
      * Client Servers: 154
      * File Server: 105
   * **Total number of Messages Received by**
      * Quorum Servers: 95
      * Client Servers: 88
      * File Server: 105
   * **Per Critical Section**
      * Messages Exchanged
         * Average: 12 
         * Minimum: 11
         * Maximum: 14
      * Latency
         * Average: 11 sec 
         * Minimum: 06 sec
         * Maximum: 17 sec
 
 #### Round 2
   * **Variable Fields**
      * Time between a client exiting its critical section and issuing its next request: 7-10 sec
      * Time spent in the critical section: 6-8 sec
   * **Total number of Messages Sent by**
      * Quorum Servers: 47
      * Client Servers: 152
      * File Server: 105
   * **Total number of Messages Received by**
      * Quorum Servers: 95
      * Client Servers: 87
      * File Server: 105
   * **Per Critical Section**
      * Messages Exchanged
         * Average: 12
         * Minimum: 11
         * Maximum: 14
      * Latency
         * Average: 27 sec
         * Minimum: 16 sec
         * Maximum: 34 sec
      

 #### Round 3
   * **Variable Fields**
      * Time between a client exiting its critical section and issuing its next request: 12-15 sec
      * Time spent in the critical section: 11-13 sec
   * **Total number of Messages Sent by**
      * Quorum Servers: 47
      * Client Servers: 152
      * File Server: 105
   * **Total number of Messages Received by**
      * Quorum Servers: 94
      * Client Servers: 87
      * File Server: 105
   * **Per Critical Section**
      * Messages Exchanged
         * Average: 12 
         * Minimum: 11
         * Maximum: 14 
      * Latency
         * Average: 41 sec 
         * Minimum: 25 sec
         * Maximum: 49 sec
         
### Impact on latency
* When the above two fields were increased, there was an increase in latency as well
   * Time between a client exiting its critical section and issuing its next request
   * Time spent in the critical section
* The rest of the data points remained unimpacted

### Deadlock Situation
* Through the trial runs, no deadlock has been observed so far
* If any Deadlock has been detected at a Client, the details are logged in _distributed-quorum-system/quorum/datalog/client/Client<clientID>.txt_ file

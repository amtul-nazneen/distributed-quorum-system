# Data Collection and Performance Analysis
Perform Data Collection and Analysis for the Distributed Quorum based Mutual Exclusion

### Overview
1. Model has 7 Quorum Servers, 5 Clients and 1 File Server
2. Data Collection and Analysis has been done for the below
    * Total number of Messages Sent by
      * Quorum Servers
      * Client Servers
      * File Server
   * Total number of Messages Received by
      * Quorum Servers
      * Client Servers
      * File Server
   * Per Critical Section
      * Total Number of Messages Exchanged
      * Latency (Total elapsed time between making a request and being able to enter the critical section)
   * Repeated with varying values and reported their impact on latency
      * Time between a client exiting its critical section and issuing its next request
      * Time spent in the critical section
   * Report any Deadlock situation

### Steps to run the application
1. Open Project in any IDE
2. Generate the jar [Ex: quorum.jar]
3. Copy the entire folder _quorum_[folder path in the project: distributed-quorum-system/quorum] to the dc machines of client, quorum & file servers and place the jar _within_ the _mutex_ folder
     * In case any of the machines are unavailable make changes in the _quorum.app.utils.Constants_ file[Section II] to reflect the new machine to server/client mapping
     * This folder has the s0 directory having file0.txt that is be available to the file server for writing
     * This folder also has Data Log files under the respective node folders in _distributed-quorum-system/quorum/datalog_ folder which capture the data log for no: of messages sent, received and deadlock if any detected
4. Start the quorum servers[1-7] respectively on dc01-dc07 machines
      * Start the servers from machines [dc01-dc07] using ``java -cp quorum.jar quorum.app.quorumServer.QuorumServer <1/2/3/4/5/6/7>``
      * Example: ``java -cp quorum.jar quorum.app.quorumServer.QuorumServer 1``, starts the first quorum server on dc01
      * In case any of the machines are unavailable make changes in the _quorum.app.utils.Constants_ file[Section II] to reflect the new machine to server/client mapping
5. Start the file server on dc10 machine
      * Start the server on dc10 using ``java -cp quorum.jar quorum.app.server.Server``
      * In case the machine is unavailable make changes in the _quorum.app.utils.Constants_ file[Section II] to reflect the new machine to server/client mapping      
6. Start the 5 clients respectively on dc11[client1], dc12[client2], dc13[client3], dc14[client4], dc15[client5] machines
    * Start the clients from machines [dc11-dc15] using ``java -cp quorum.jar quorum.app.client.ClientStarter <1/2/3/4/5>``
    * Example: ``java -cp quorum.jar quorum.app.client.ClientStarter 1``, starts the first client on dc11
    * In case any of the machines are unavailable make changes in the _quorum.app.utils.Constants_ file[Section II] to reflect the new machine to server/client mapping
7. 20 requests per client are randomly generated and their critical execution determination is done 
    * To increase or decrease the request count, make changes in the _quorum.app.utils.Constants_ file[Section I] to reflect the changes
8. All the Data Collection points are logged for each node in _distributed-quorum-system/quorum/datalog_ folder for _Client_, _Server_ and _Quorum_ nodes
9. If any Deadlock has been detected at a Client, the details are logged in _distributed-quorum-system/quorum/datalog/client/Client<clientID>.txt_ file
10. The parameters for timeout can be configured in _quorum.app.utils.Constants_ file [Section III]

# Distributed Quorum System
Implement Distributed Mutual Exclusion in a multi-client, multi-server model using Ricart-Agrawala Algorithm with Roucairol-Carvalho optimization 

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 


### Softwares/SDKs
1. Jdk 1.8.0 and above
2. Any IDE

### Overview
* Model has 3 Servers and 5 Clients
* Servers 1-3 run on machines dc01-dc03 respectively
* Clients 1-5 run on machines dc04-dc08 respectively
* Servers 1-3 write and read files from their storage directory s1-s3 respectively
* Each server has 3 files from which they read and write
     * Initially all the files have some basic content populated

### Steps to run the application
1. Open Project in any IDE
2. Generate the jar [Ex: mutex.jar]
3. Copy the entire folder _mutex_[folder path in the project: distributed-mutual-exclusion/mutex] to the dc machines [dc01-dc08] and place the jar _within_ the _mutex_ folder
     * In case any of the machines are unavailable make changes in the _mutex.app.utils.Constants_ file[Section II] to reflect the new machine to server/client mapping
     * This folder has the 3 directories each having 3 files that are to be available to each server for reading and writing operations
     * This folder also has an _Output.txt_ file to which the critical section task of each process is written in order, which helps in verification later on
4. Start the 3 servers respectively on dc01[server1], dc02[server2], dc03[server3] machines
      * Start the servers from machines [dc01-dc03] using ``java -cp mutex.jar mutex.app.server.Server <1/2/3>``
      * Example: ``java -cp mutex.jar mutex.app.server.Server 1``, starts the first server on dc01
      * In case any of the machines are unavailable make changes in the _mutex.app.utils.Constants_ file[Section II] to reflect the new machine to server/client mapping
5. Start the 5 clients respectively on dc04[client1], dc05[client2], dc06[client3], dc07[client4], dc08[client5] machines
    * Start the clients from machines [dc04-dc08] using ``java -cp mutex.jar mutex.app.client.Client <1/2/3/4/5>``
    * Example: ``java -cp mutex.jar mutex.app.client.Client 1``, starts the first client on dc04
    * In case any of the machines are unavailable make changes in the _mutex.app.utils.Constants_ file[Section II] to reflect the new machine to server/client mapping
6. 20 requests per client are randomly generated and their critical execution determination is done 
    * To increase or decrease the request count, make changes in the _mutex.app.utils.Constants_ file[Section I] to reflect the changes
7. Each critical section operation is logged in a separate text file _mutex\Output.txt_. This contains the ordered list of critical section operations done by all processes
8. Once all the clients complete all their requests, use the _CompareFiles_ class to verify if the contents of a file in all the three servers are the same
     * Command: ``java -cp mutex.jar test.CompareFiles <1/2/3>`` 
     * Example: ``java -cp mutex.jar test.CompareFiles 1``, verifies if file1.txt is consistent across the three servers
9. Sockets are closed after a timeout value that is specifed in the _mutex.app.utils.Constants_ file[Section IV]

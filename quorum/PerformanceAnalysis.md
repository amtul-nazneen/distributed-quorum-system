# Data Collection and Performance Analysis
Perform Data Collection and Analysis for the Distributed Quorum based Mutual Exclusion

### Overview
1. Model has 7 Quorum Servers, 5 Clients and 1 File Server
2. Data Collection and Analysis has been done for the below
    * Total number of Messages Sent by Quorum, Client & File Server(s)
   * Total number of Messages Received by Quorum, Client & File Server(s)
   * Total Number of Messages Exchanged, Latency Per Critical Section
   * Repeated with varying values and reported their impact on latency
      * Time Between Client Exiting Critical Section and Issuing Next request
      * Time Spent In Critical Section
   * Report any Deadlock situation

### Detailed Analysis
 All the Data Collection points are logged for each node in _distributed-quorum-system/quorum/datalog_ folder for _Client_, _Server_ and _Quorum_ nodes
  
#### 1. Message Metrics
  * **Total Messages Sent by**
      * Quorum Servers: 48
      * Client Servers: 154
      * File Server: 105
  * **Total Messages Received by**
      * Quorum Servers: 95
      * Client Servers: 88
      * File Server: 105
  * **Total Messages Exchanged Per Critical section by Client Servers**
         * Average: 12 
         * Minimum: 11
         * Maximum: 14

#### 2. Analysis by Varying Client Time Parameters
* **Multiple Runs by varying below fields **
    * **T1**- _Time Between Client Exiting Critical Section and Issuing Next request_
    * **T2**- _Time Spent In Critical Section_


#### Round 1
   * **Variable Fields **
     * T1 --->  2 - 5 sec 
     * T2 ---> 1 - 3 sec
   * **Latency per Critical Section **
     * Average:  11 sec 
     * Minimum: 06 sec
     * Maximum: 17 sec
   * **Deadlock Occurance **
     * Deadlock Timeout: 90 sec 
     * Deadlock Occurred: No
      
#### Round 2
   * **Variable Fields **
     * T1 --->  7 - 10 sec 
     * T2 ---> 6 - 8 sec
   * **Latency per Critical Section **
     * Average:  27 sec 
     * Minimum: 16 sec
     * Maximum: 34 sec
   * **Deadlock Occurance **
     * Deadlock Timeout: 90 sec 
     * Deadlock Occurred: No      

#### Round 3
   * **Variable Fields **
     * T1 --->  12 - 15 sec 
     * T2 ---> 11 - 13 sec
   * **Latency per Critical Section **
     * Average:  41 sec 
     * Minimum: 25 sec
     * Maximum: 49 sec
   * **Deadlock Occurance **
     * Deadlock Timeout: 90 sec 
     * Deadlock Occurred: No
     
#### Round 4
   * **Variable Fields **
     * T1 --->  50 - 60 sec 
     * T2 ---> 1 - 3 sec
   * **Latency per Critical Section **
     * Average:  3 sec 
     * Minimum:  3 sec
     * Maximum:  6 sec
   * **Deadlock Occurance **
     * Deadlock Timeout: 90 sec 
     * Deadlock Occurred: No
     
#### Round 5
   * **Variable Fields **
     * T1 --->  2 - 5 sec 
     * T2 ---> 80 - 90 sec
   * **Latency per Critical Section **
     * Average:  200 sec 
     * Minimum:   84 sec [when deadlock doesn't occurs]
     * Maximum:   330 sec [when deadlock occurs]
   * **Deadlock Occurance **
     * Deadlock Timeout: 180 sec 
     * Deadlock Occurred: Yes
     
#### Round 6
   * **Variable Fields **
     * T1 --->  80 - 90 sec 
     * T2 ---> 6 - 8 sec
   * **Latency per Critical Section **
     * Average: 4 sec 
     * Minimum: 3 sec
     * Maximum: 17 sec
   * **Deadlock Occurance **
     * Deadlock Timeout: 90 sec 
     * Deadlock Occurred: No
         
#### 3. Impact on Latency
1. With increase in _Time Spent In Critical Section_ field
    * Latency is increased
    * The remaining performance metrics (no. of messages sent/received, no. of messages exchanged per critical section etc) remain unimpacted
    * This field is primarily determining the Latency 
    * If this value reaches close to the Deadlock Detection Timeout value then the deadlock is seen in the clients along with high latency
2. With increase in _Time Between Client Exiting Critical Section and Issuing Next request_ field
	* The latency relatively reduced 
    * The latency was the maximum of _Time Spent In Critical Section_ range, it didn't increase beyond this
    * Example: In Round 4, the latency was 3 sec which is the maximum amount of time spent in the critical section
    * The remaining performance metrics (no. of messages sent/received, no. of messages exchanged per critical section etc) remain unimpacted as well
    * Increase in this value would not result in a deadlock. If this value reached closer to the Deadlock Detection Timeout value, this didn't cause a deadlock

#### 4. Deadlock Occurrence
* If the _Time Spent In Critical Section_ (T2) is increased and if the value is close to the Deadlock Detection Timeout value, then deadlock is observed
* If _Time Between Client Exiting Critical Section and Issuing Next request_ (T1) is increased and even if the value reaches to a very high value that is close to the Deadlock Detection Timeout value, no deadlock is observed
* Any Deadlock that has been detected at a Client, its details are logged in _distributed-quorum-system/quorum/datalog/client/Client<clientID>.txt_ file

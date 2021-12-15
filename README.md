# Synopsis

In this project I built a multi-threaded TCP server, The server handles each client request (algorithmic problem) in a separate thread, solves it and prints the result.

## Goals

Gain experience and knowledge in developing a multi-threaded environment and understand the various emphases that I need to pay attention to in such a project.

## Motivation

During the project I experimented with the following:

- Client-Server Architecture - Creating a ServerSocket and binding it to a port and creating a client socket and connecting it to the server. 

- Threads and Multi-Threaded environment: I opened a new thread for each client that wants to connect to the server, So when a new client wants to connect to the server,
                                           the other clients will not be affected. (blocking request - socket.accept ())
                                           
- I used a Java object called ThreadPoolExecutor to manage and schedule the various threads.

- I used synchornized data structures that are suitable for a multi-threaded environment.

- I used a Volatile variable created in RAM and shared between all threads (all threads can read from it at any given time, but only one thread can write to it at any given time)
   I created the volatile variable in the TCPServer class in order to be able to update all the threads regarding the server status (up / down).

# Simple Java Http Server

A lightweight, custom-built HTTP server implemented in Java, 
capable of serving static files from a configurable web root. 
This project demonstrates low-level networking, HTTP parsing, 
multi-threading, and basic web server functionality.

---

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [How It Works](#how-it-works)
- [Project Structure](#project-structure)
- [Testing](#testing)


---
## Features
- Parses HTTP requests and headers manually.
- Handles GET requests for static files.
- Supports configurable port and web root via JSON config.
- Serves files with proper MIME types.
- Multi-threaded: each incoming connection handled in a separate thread.
- Fully tested with unit tests for HTTP parsing, headers, and file handling.
- Demo page - index.html 

---

## Technologies
- Java 17+ – Core language and threading 
- SLF4J + Logback – Logging 
- JUnit 5 – Unit testing 
- CSS / HTML / JS – For the demo web page

---

## Installation
1. Clone the repository:
    - git clone https://github.com/<your-username>/simple-java-http-server.git 
    - cd simple-java-http-server
2. Build with Maven
   - mvn clean compile
3. Configure the server
   - Edit src/main/resources/http.json to set your port and webroot

---

## Usage
1. Run the server from your IDE or via command line:
2. Then open your browser and navigate to: http://localhost:8080/

---

## How It Works
The server operates in five main stages:
1. Server Listener Thread
    - Listens on a configurable port for incoming TCP connections.
    - Each connection is handed off to a separate HttpConnectionWorkerThread, allowing multiple clients to connect simultaneously.
2. Worker Thread
   - Reads the raw bytes from the client socket.
   - Calls the HttpParser to parse the HTTP request.
3. HTTP Parsing
   - HttpParser breaks the request into:
     - Request line (method, path, HTTP version)
     - Headers (Host, User-Agent, Accept, etc.)
     - Body (ignored for GET requests)
   - Validates the HTTP method (currently only GET) and HTTP version.
   - Throws descriptive exceptions if the request is invalid.
4. WebRoot Handling
   - The WebRootHandler locates the requested file in the configured web root.
   - Checks if the path is valid and prevents directory traversal (..).
   - Determines the MIME type and reads the file contents into a byte array.
5. HTTP Response
   - The worker thread sends a valid HTTP response over the socket:
   - Status line (e.g., HTTP/1.1 200 OK)
   - Headers (Content-Type, Content-Length)
   - Body (file contents)
   - If the file is missing or the request is invalid, sends appropriate error codes (e.g., 404 Not Found, 400 Bad Request).

---

## Project Structure
    src/\
    ├─ main/\
    │  ├─ java/com/gjthras08/httpserver/\
    │  ├─ java/com/gjthras08/http/\
    │  ├─ resources/http.json\
    │  └─ WebRoot/\
    ├─ test/java/com/gjthras08/http/\
    └─ test/java/com/gjthras08/httpserver/core/io/\

---

## Testing
- The project includes unit tests for:
- HTTP request parsing 
- Header validation 
- Supported HTTP versions 
- File path handling in web root 
- MIME type detection
- Run tests with Maven: mvn test

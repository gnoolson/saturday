[Documentation](/doc)

<h1 id="main">Client</h1>

A client is an MQTT client whose primary purpose is subscribing to selected topics and publishing messages.

Under the hood, the client is based on `org.eclipse.paho.client.mqttv3.MqttAsyncClient` with MQTT protocol version 3.1.1 support.

It operates asynchronously. Outgoing messages are placed into a queue first, allowing publishing to run in the background without blocking script execution.

Undelivered outgoing messages are stored in RAM and will be lost if **Saturday** is restarted.

Additional global client parameters can be configured in the **Saturday configuration** file:

```properties
# maximum number of messages in the queue
gnoolson.saturday.client.paho.max_buffer_size=1000          

# maximum number of unacknowledged messages the client can hold
gnoolson.saturday.client.paho.max_inflight_messages=100     

# maximum timeout waiting for asynchronous client action acknowledgment (ms)
gnoolson.saturday.client.paho.action_timeout=10000       

# maximum client connection timeout (s)
gnoolson.saturday.client.paho.connection_timeout=30      

# false = throw an error when the buffer is full; true = overwrite the oldest messages
gnoolson.saturday.client.paho.delete_oldest_messages=false  

# false = client and broker preserve session state across reconnects; true = start a clean session on every connection
gnoolson.saturday.client.paho.clean_session=false           
```

---



<h1 id="fields">Fields</h1>



<h3 id="project">Project</h3>

The project to which this client belongs. Required field.

---



<h3 id="name">Name</h3>

A unique client name within the project. Only Latin letters, digits, and the `#` and `_` characters are allowed. Maximum 32 characters. Required field.


---



<h3 id="description">Description</h3>

A text description of the client. Maximum 5000 characters. Optional field.


---



<h3 id="client-uri">URI</h3>

The URI used to connect to the MQTT broker. Example: `tcp://127.0.0.1:1883`

---



<h3 id="auth">Authentication</h3>

If authentication is enabled, the username and password cannot be empty. Maximum 64 characters per field.


---



<h3 id="connection-handler">Connect handler</h3>

The selected script will be executed once the client connects to the MQTT broker. Optional field.

---



<h3 id="disconnection-handler">Disconnect handler</h3>

The selected script will be executed once the client disconnects from the MQTT broker. Optional field.

---
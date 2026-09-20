[Documentation](/docs)

<h1 id="main">Client</h1>

An MQTT client whose primary purpose is to subscribe to selected topics and publish messages.

The client is based on `org.eclipse.paho` and uses MQTT protocol version `3.1.1`.

---

<h1 id="fields">Fields</h1>



<h3 id="project">Project</h3>

The Project to which this Client belongs. Required field.

---



<h3 id="name">Name</h3>

A unique Client name within the Project. Only Latin letters, digits, and the `#` and `_` characters are allowed. Maximum length: 32 characters. Required field.

---



<h3 id="description">Description</h3>

A text description of the Client. Maximum length: 1000 characters. Optional field.

---



<h3 id="client-uri">URI</h3>

The URI used to connect to the MQTT broker.

Example: `tcp://127.0.0.1:1883`

---



<h3 id="auth">Authentication</h3>

If authentication is enabled, both the username and password must be specified. The maximum length for each field is 64 characters.

---




<h3 id="connection-handler">Connection Handler</h3>

The selected Script will be executed after the Client connects to the MQTT broker. Optional field.

---



<h3 id="disconnection-handler">Disconnection Handler</h3>

The selected Script will be executed after the Client disconnects from the MQTT broker. Optional field.

---




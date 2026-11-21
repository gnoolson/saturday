[Documentation](/doc)

<h1 id="main">Subscription</h1>

The client subscribes to the specified topic filter to receive incoming messages, which are then processed by the selected script.

---

<h1 id="fields">Fields</h1>



<h3 id="topic-filter">MQTT topic filter</h3>

An MQTT topic filter in standard MQTT Topic Filter format. The `+` (single-level) and `#` (multi-level) wildcard characters are supported.
Cannot be empty.

---



<h3 id="description">Description</h3>

A text description of the subscription. Maximum 5000 characters. Optional field.

---

<h3 id="qos">QoS</h3>

When subscribing, the QoS parameter defines the maximum level of delivery guarantee with which the broker is permitted to forward incoming messages to that specific client. Required field.

---

<h3 id="script">Message handler</h3>

The selected script will be executed when a message matching this subscription is received. Required field.

---
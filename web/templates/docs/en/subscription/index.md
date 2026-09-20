[Documentation](/docs)

<h1 id="main">Subscription</h1>

The Client subscribes to the specified MQTT topic filter to receive incoming messages, which are then processed by the selected Script.

---

<h1 id="fields">Fields</h1>



<h3 id="topic-filter">MQTT Topic Filter</h3>

An MQTT topic filter in the MQTT Topic Filter format.

The `+` (single-level) and `#` (multi-level) wildcard characters are supported.

This field cannot be empty.

---



<h3 id="description">Description</h3>

A text description of the Subscription. Maximum length: **500** characters. This field is optional.

---



<h3 id="script">Message Handler</h3>

The selected Script is executed whenever a message is received through this Subscription. This field is required.

---




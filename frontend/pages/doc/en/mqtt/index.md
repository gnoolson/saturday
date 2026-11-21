[Documentation](/doc)

<h1 id="main">MQTT</h1>

**MQTT** is a lightweight network messaging protocol designed for Internet of Things (IoT) devices and unreliable networks. It operates on a Publish/Subscribe model, where devices do not communicate directly with each other, but instead exchange data through a central server - the broker.

With minimal packet overhead, the protocol places almost no load on the CPU and consumes very little network bandwidth.

### How MQTT architecture works

- **Broker** - the central intermediary server. It receives all messages, filters them, and distributes them to interested clients.
- **Publisher** - a client that sends data.
- **Subscriber** - a client that receives data.

---

### Key protocol concepts

#### Topic

A **topic** is a hierarchical address or channel to which messages are published. Topic levels are separated by the `/` character.

Topic example: `home/kitchen/temperature`.

Subscription wildcards:
- `+` matches a single level (`home/+/temperature` subscribes to temperature readings across all rooms).
- `#` matches all subsequent levels (`home/kitchen/#` subscribes to all data from the kitchen).

---

#### Payload

The **payload** is the data transmitted inside a packet to a specific topic. MQTT does not enforce any data format and treats the payload simply as a raw byte array (up to 256 MB maximum; in practice, just a few dozen bytes are typically sent).

Format examples:
- Plain text or numbers: `23.5` or `ON`.
- Structured JSON: `{"temp": 23.5, "humidity": 48}`.
- Binary data: images, audio chunks, or encrypted bytes.

**Important note:** the broker does not inspect the payload - it simply routes the packet based on the topic name, leaving data decoding to the subscriber.

---

#### QoS (Quality of Service)

**QoS** defines the delivery guarantee level for a message between a client and the broker. The protocol provides three levels to balance speed and reliability:
- **QoS 0** (At most once / "Fire and forget"): the message is sent without acknowledgment. This is the fastest mode, but messages may be lost if the connection drops (suitable for frequent sensor readings).
- **QoS 1** (At least once): the broker or client must acknowledge receipt. If no acknowledgment is received, the message is re-sent, which may result in duplicates.
- **QoS 2** (Exactly once): the slowest yet most reliable level, using a four-step handshake. It guarantees that a message is delivered without loss or duplication (used for critical commands such as unlocking a door).

---

#### Retained messages

**Retained** is a flag that instructs the broker to store the last known message for a specific topic.

**Why it is needed:** by default, a new subscriber only receives messages published after it connects. If a sensor reports its status once an hour, a newly connected client would have to wait a long time for data.

**How it works:** when a message is published with `retained = true`, the broker stores it. As soon as a new subscriber connects to that topic, the broker immediately delivers the stored value.

**Clearing a retained message:** to delete a retained message, publish an empty payload (0 bytes) to the same topic with the `retained = true` flag.
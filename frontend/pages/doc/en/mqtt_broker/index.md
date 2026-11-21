[Documentation](/doc)

<h1 id="main">MQTT broker</h1>

**Saturday** includes a built-in MQTT broker (`io.moquette.broker`).

Full broker configuration can be performed in the **Saturday configuration** file. 

Broker settings defined in the configuration file take precedence over the **Saturday** UI settings (such as the connection port, anonymous access, and the user database). 

Setting `gnoolson.saturday.broker.use_broker_configuration_only` in the configuration file completely disables Saturday's built-in broker settings.
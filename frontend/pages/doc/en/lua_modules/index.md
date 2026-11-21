<div id="top"></div>
<a href="#top" class="l-scroll-to-top">↑</a>

[Documentation](/doc)

# Injected services

[Args](/doc/lua_modules#args)  
[Dashboard](/doc/lua_modules#dashboard)  
[Std](/doc/lua_modules#std)  
[Output](/doc/lua_modules#output)  
[Log](/doc/lua_modules#log)  
[Client](/doc/lua_modules#client)  

# Classes

[ByteArray](/doc/lua_modules#byte_array)

# Additional modules

[open_dashboard](/doc/lua_modules#open_dashboard)  
[client_info](/doc/lua_modules#client_info)
[db](/plugin/db/doc)  
[http_client](/plugin/http_client/doc)  
[cache](/plugin/cache/doc)  
[json](/plugin/json/doc)  
[now](/plugin/now/doc)  
[tg](/plugin/tg/doc)  
[timer](/plugin/timer/doc)  
[locker](/plugin/locker/doc)
[serial](/plugin/serial/doc)

---

### Injected services

_Each script has its own instance of an injected service. Its functions are accessed using the `.` operator._
<h1 id="args" class="pt-5">Args</h1>

The `Args` service is used to retrieve arguments passed into a script.
Every script receives a standard set of arguments: project ID, script ID, trigger source, and additional metadata.

Custom arguments can be passed in the following cases:
- when launching a script using the [timer](/doc/lua_modules#timer) module;
- when launching a script from a plugin.

## Example

```lua
Args.projectId    -- project identifier (string)
Args.scriptId     -- script identifier (string)
Args.source       -- script trigger source (string)
```

### Script triggered by a dashboard

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.dashboardId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- script ID (string)
DASHBOARD,                          -- trigger source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- dashboard ID (string)
]]
```

### Script triggered by a schedule

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.scheduleId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- script ID (string)
SCHEDULE,                           -- trigger source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- schedule ID (string)
]]
```

### Script triggered by a subscription

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.subscriptionId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- script ID (string)
SUBSCRIPTION,                       -- trigger source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- subscription ID (string)
]]
```

### Script triggered by autostart

```lua
Output.print(Args.projectId, Args.scriptId, Args.source)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- script ID (string)
AUTOSTART                           -- trigger source (string)
]]
```

### Script triggered by the "Client connected to broker" event

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.clientId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- script ID (string)
CLIENT_CONNECTED,                   -- trigger source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- client ID (string)
]]
```

### Script triggered by the "Client disconnected from broker" event

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.clientId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- script ID (string)
CLIENT_DISCONNECTED,                -- trigger source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- client ID (string)
]]
```

### Script triggered by the "Script error" event

```lua
Output.print(
    Args.projectId,
    Args.scriptId,
    Args.source,
    Args.sourceScriptId,
    Args.numberOfErrors,
    Args.maxNumberOfErrorsAllowed,
    Args.reason,
    Args.stack
)

--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- script ID (string)
SCRIPT_ERROR,                       -- trigger source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- ID of the script where the error occurred (string)
1,                                  -- current error count (number)
10,                                 -- maximum allowed errors (number)
reason,                             -- error reason (string)
stack                               -- error stack trace (string)
]]
```

### Script triggered by the Lua editor

```lua
Output.print(Args.projectId, Args.scriptId, Args.source)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- script ID (string)
DEV                                 -- trigger source (string). Value can be overridden in mock data (JSON).
]]
```

### Script triggered by a Lua library (using Timer as an example)

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.luaLib)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- script ID (string)
LUA_LIB,                            -- trigger source (string)
timer,                              -- Lua library ID (or plugin ID) (string)
]]
```

---
<h1 class="pt-5" id="dashboard">Dashboard</h1>

The `Dashboard` service is used to receive incoming messages from a dashboard and send messages back to it.

Receiving and sending messages via this service is only possible in scripts that were triggered by a dashboard.

To send messages to open dashboards without triggering a script from the dashboard itself, use the [open_dashboard](#open_dashboard) module.

## Example

```lua
-- returns true if an incoming message from the dashboard is available
local flag = Dashboard.isIncomingMessageAvailable()

-- returns the incoming message object (table)
-- this is the same object passed via:
-- dashboard.execute({ key : "value" })
-- returns nil if no message is available
local incomingMessage = Dashboard.getIncomingMessage()

-- returns the open dashboard identifier (string)
-- each open dashboard instance has its own unique identifier
-- returns nil if the dashboard is unavailable
local id = Dashboard.getOpenDashboardId()

-- sends a message to the dashboard
-- if the dashboard is already closed or unavailable, the message will be ignored
local outgoingMessage = {
    key = "Pi",
    value = 3.14
}

Dashboard.sendMessage(outgoingMessage)
```

---

<h1 class="pt-5" id="std">Std</h1>

The `Std` service is used to redirect the **STDOUT** and **STDERR** streams.

## Example

```lua
-- captures the STDOUT stream and invokes the callback function,
-- passing a single line of text (UTF-8)
Std.captureRowOut(function(row)

end)

-- captures the STDERR stream and invokes the callback function,
-- passing a single line of text (UTF-8)
Std.captureRowErr(function(row)

end)

-- captures the STDOUT stream and invokes the callback function,
-- passing a single byte (number -128...127)
Std.captureByteOut(function(value)

end)

-- captures the STDERR stream and invokes the callback function,
-- passing a single byte (number -128...127)
Std.captureByteErr(function(value)

end)

-- restores the standard STDOUT stream (java.lang.System.out)
Std.restoreOut()

-- restores the standard STDERR stream (java.lang.System.err)
Std.restoreErr()



-- example of capturing STDOUT into a buffer
local buffer = {}

Std.captureRowOut(function(row)
    table.insert(buffer, row)
end)

os.execute("ls -l")

Std.restoreOut()

Output.print(table.concat(buffer, "\n"))



-- example of capturing STDOUT into a ByteArray
local ba = ByteArray.allocate(10000);
local index = 0

Std.captureByteOut(function(value)
    ba:setInt8(value, index)
    index = index + 1
end)

os.execute("ls -l")

Std.restoreOut()

Output.print(ba:getString(0))


```


---
<h1 class="pt-5" id="output">Output</h1>

The `Output` service is used to print messages to the **Dev** output console.

If the script is not running in **Dev** mode, calls to `Output.print()` are ignored.

## Example

```lua

Output.print("message_1", "message_2", "message_n")

```

---
<h1 class="pt-5" id="log">Log</h1>

The `Log` service is used to write messages to the log. 

## Example

```lua

-- writes a message at the "debug" level
Log.debug("debug")
Log.debug({Pi = 3.14})
Log.debug("string", {Pi = 3.14})

-- writes a message at the "info" level
Log.info("info")
Log.info({Pi = 3.14})
Log.info("string", {Pi = 3.14})

-- writes a message at the "warn" level
Log.warn("warning")
Log.warn({Pi = 3.14})
Log.warn("string", {Pi = 3.14})

-- writes a message at the "error" level
Log.error("error")
Log.error({Pi = 3.14})
Log.error("string", {Pi = 3.14})

-- returns true if the "debug" logging level is enabled
local flag = Log.isDebugEnabled()

-- expensive message preparation should only be executed
-- when the "debug" level is enabled
if Log.isDebugEnabled() then
    Log.debug(prepareReallyLongDebugMessage())
end

```


---
<h1 id="client" class="pt-5">Client</h1>

The `Client` service is used to access an MQTT client.

It allows receiving incoming messages and publishing outgoing ones. Message payloads are represented as instances of the `ByteArray` class.

Receiving incoming messages and sending outgoing ones directly is only possible in scripts triggered by a subscription.

If a script is triggered by another source, you must call the `setup(clientId)` function and specify the client ID on whose behalf messages will be published.

## Example

```lua
-- returns true if an incoming message is available
local flag = Client.isIncomingMessageAvailable()

-- returns the incoming message (table)
-- returns nil if no message is available
local incomingMessage = Client.getIncomingMessage()

-- MQTT topic (string)
local topic = incomingMessage.topic

-- MQTT topic filter through which the message was received (string)
local topicFilter = incomingMessage.topicFilter

-- QoS level (number)
local qos = incomingMessage.qos

-- retrieving the payload
local payload = incomingMessage.payload
local jsonStr = payload:getString(0)

-- selects the client to be used for sending messages
-- requires passing the client ID
-- calling setup() is not needed if the script was triggered by a subscription
Client.setup("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")

-- returns the ID of the current client
-- returns nil if no client is selected
Output.print(Client.clientId)

-- publishing a message
local outgoingMessage = {
    topic = "project/super_topic",
    qos = 0,
    payload = ByteArray.fromString("{\"key\":\"value\"}"),
    retained = false -- optional. Default value is false.
}

-- returns true if an enabled client received this message.
local ok = Client.sendMessage(outgoingMessage)
```

---
### Classes

<h1 id="byte_array" class="pt-5">ByteArray</h1>

The `ByteArray` class is used to handle message payloads in the `Client` service. It wraps a Java `byte[]` array and provides convenient methods for reading, writing, and converting binary data.

> **Note:** the `position` parameter is a zero-based array index (`0`, not `1`).

## Methods and functions  

- [allocate](/doc/lua_modules#ba-allocate)
- [fromString](/doc/lua_modules#ba-fromString)
- [fromHexString](/doc/lua_modules#ba-fromHexString)
- [fromBase64String](/doc/lua_modules#ba-fromBase64String)
- [fromJBytes](/doc/lua_modules#ba-fromJBytes)
- [split](/doc/lua_modules#ba-split)
- [length](/doc/lua_modules#ba-length)
- [payloadLength](/doc/lua_modules#ba-payloadLength)
- [toHexString](/doc/lua_modules#ba-toHexString)
- [toBase64String](/doc/lua_modules#ba-toBase64String)
- [toBinaryString](/doc/lua_modules#ba-toBinaryString)
- [getJBytes](/doc/lua_modules#ba-getJBytes)
- [put](/doc/lua_modules#ba-put)
- [merge](/doc/lua_modules#ba-merge)
- [copy](/doc/lua_modules#ba-copy)
- [getBoolean](/doc/lua_modules#ba-getBoolean) 
- [getInt8](/doc/lua_modules#ba-getInt8)
- [getUint8](/doc/lua_modules#ba-getUint8)
- [getInt16Be](/doc/lua_modules#ba-getInt16Be)
- [getInt16Le](/doc/lua_modules#ba-getInt16Le)
- [getUint16Be](/doc/lua_modules#ba-getUint16Be)
- [getUint16Be](/doc/lua_modules#ba-getUint16Be)
- [getInt32Be](/doc/lua_modules#ba-getInt32Be)
- [getInt32Le](/doc/lua_modules#ba-getInt32Le)
- [getUint32Be](/doc/lua_modules#ba-getUint32Be)
- [getUint32Le](/doc/lua_modules#ba-getUint32Le)
- [getInt64Be](/doc/lua_modules#ba-getInt64Be)
- [getInt64Le](/doc/lua_modules#ba-getInt64Le)
- [getFloatBe](/doc/lua_modules#ba-getFloatBe)
- [getFloatLe](/doc/lua_modules#ba-getFloatLe)
- [getDoubleBe](/doc/lua_modules#ba-getDoubleBe)
- [getDoubleLe](/doc/lua_modules#ba-getDoubleLe)
- [getUint8Sequence](/doc/lua_modules#ba-getUint8Sequence)
- [getInt8Sequence](/doc/lua_modules#ba-getInt8Sequence)
- [getString](/doc/lua_modules#ba-getString)
- [getCString](/doc/lua_modules#ba-getCString)
- [setBoolean](/doc/lua_modules#ba-setBoolean)
- [setInt8](/doc/lua_modules#ba-setInt8)
- [setUint8](/doc/lua_modules#ba-setUint8)
- [setInt16Be](/doc/lua_modules#ba-setInt16Be)
- [setInt16Le](/doc/lua_modules#ba-setInt16Le)
- [setUint16Be](/doc/lua_modules#ba-setUint16Be)
- [setUint16Le](/doc/lua_modules#ba-setUint16Le)
- [setInt32Be](/doc/lua_modules#ba-setInt32Be)
- [setInt32Le](/doc/lua_modules#ba-setInt32Le)
- [setUint32Be](/doc/lua_modules#ba-setUint32Be)
- [setUint32Le](/doc/lua_modules#ba-setUint32Le)
- [setInt64Be](/doc/lua_modules#ba-setInt64Be)
- [setInt64Le](/doc/lua_modules#ba-setInt64Le)
- [setFloatBe](/doc/lua_modules#ba-setFloatBe)
- [setFloatLe](/doc/lua_modules#ba-setFloatLe)
- [setDoubleBe](/doc/lua_modules#ba-setDoubleBe)
- [setDoubleLe](/doc/lua_modules#ba-setDoubleLe)
- [setUint8Sequence](/doc/lua_modules#ba-setUint8Sequence)
- [setInt8Sequence](/doc/lua_modules#ba-setInt8Sequence)
- [setString](/doc/lua_modules#ba-setString)
- [setCString](/doc/lua_modules#ba-setCString)

[C-struct Example](/doc/lua_modules#ba-example)
## Static functions
<span id="ba-allocate"></span>

- `allocate(length)` - creates a new `ByteArray` instance.

```lua
local data = ByteArray.allocate(64)
```

Allocates `64` bytes of memory and returns a new `ByteArray` instance.

---
<span id="ba-fromString"></span>

- `fromString(string)` - creates a `ByteArray` from a UTF-8 string.

```lua
local data = ByteArray.fromString("{\"key\":\"value\"}")
```

Converts the string into a UTF-8 byte array and returns a `ByteArray` instance.

---
<span id="ba-fromHexString"></span>

- `fromHexString(hexString)` - creates a `ByteArray` from a HEX string.

```lua
local data = ByteArray.fromHexString("48656C6C6F20576F726C64")
```

Converts the HEX string into a byte array and returns a `ByteArray` instance.

---
<span id="ba-fromBase64String"></span>

- `fromBase64String(base64String)` - creates a `ByteArray` from a Base64 string.

```lua
local data = ByteArray.fromBase64String("aGVsbG8gd29ybGQ=")
```

Converts the Base64 string into a byte array and returns a `ByteArray` instance.

---
<span id="ba-fromJBytes"></span>

- `fromJBytes(userdata)` - creates a `ByteArray` from a Java `byte[]`.

```lua
local data = ByteArray.fromJBytes(anotherByteArray:getJBytes())
```

Creates a `ByteArray` instance from a Java `byte[]` array.

---
## Instance methods

_In the examples below, `data` is an instance of the `ByteArray` class._
<span id="ba-split"></span>

- `split(position, length, chunkSize)` - slices the data, splits it into equal-sized chunks, and returns an array (`table`) of `ByteArray` instances.

```lua
local array = data:split(2, 16, 4)
```

Creates a `16`-byte slice starting at position `2` and splits it into `4`-byte chunks.

```
[0x00, 0x01,
0x02, 0x03, 0x04, 0x05,
0x06, 0x07, 0x08, 0x09,
0x0A, 0x0B, 0x0C, 0x0D,
0x0E, 0x0F, 0x10, 0x11,
0x12, 0x13, 0x14, 0x15, 0x16]
```

This returns an array of `4` `ByteArray` instances.
```
[0x02, 0x03, 0x04, 0x05]
[0x06, 0x07, 0x08, 0x09]
[0x0A, 0x0B, 0x0C, 0x0D]
[0x0E, 0x0F, 0x10, 0x11]
```

---
<span id="ba-length"></span>

- `length()` - returns the total size of the underlying byte array.

```lua
local length = data:length()
```

Returns a `number`.

---
<span id="ba-payloadLength"></span>

- `payloadLength()` - returns the number of bytes up to the last non-zero byte. Trailing `0x00` bytes are ignored.

## Example

The array contains `8` bytes (`0xFF, 0xFF, 0xFF, 0x00, 0xAA, 0x00, 0x00, 0x00`),
but only its first `5` bytes (`0xFF, 0xFF, 0xFF, 0x00, 0xAA`) are considered occupied.

```lua
local payloadLength = data:payloadLength()
```

Returns a `number`.

---
<span id="ba-toHexString"></span>

- `toHexString()` - returns the data as a HEX string.

```lua
local str = data:toHexString()
```

Returns a HEX string.

---
<span id="ba-toBase64String"></span>

- `toBase64String()` - returns the data as a Base64 string.

```lua
local str = data:toBase64String()
```

Returns a Base64 string.

---
<span id="ba-toBinaryString"></span>

- `toBinaryString()` - returns the data as a binary string.

```lua
local str = data:toBinaryString()
```

Returns a binary string.

---
<span id="ba-getJBytes"></span>

- `getJBytes()` - returns the underlying Java `byte[]` array.

```lua
local bytes = data:getJBytes()
```

Returns `userdata` wrapping a Java `byte[]`.

---
<span id="ba-put"></span>

- `put(anotherByteArray, position)` - copies data from another `ByteArray` into the current one.

```lua
local bytes = data:put(data2, 8)
```

Writing starts at position `8`. Returns the number of bytes written (`number`, equal to the length of `anotherByteArray`).

---
<span id="ba-merge"></span>

- `merge(anotherByteArray, position)` - merges the current `ByteArray` with another one.

```lua
local newData = data:merge(data2, 8)
```

Merging starts at position `8`. Returns a new `ByteArray` instance.

---
<span id="ba-copy"></span>

- `copy(position, length)` - creates a copy of a slice of the data.

```lua
local newData = data:copy(2, 4)
```

Creates a `4`-byte copy starting at position `2` and returns a new `ByteArray` instance.

---
<span id="ba-getBoolean"></span>

- `getBoolean(position)` - returns a `boolean` value.

```lua
local flag = data:getBoolean(0)
```

Reads `1` byte at the specified position and returns a `boolean`.

---
<span id="ba-getInt8"></span>

- `getInt8(position)` - returns an `int8` value.

```lua
local value = data:getInt8(0)
```

Reads `1` byte at the specified position, converts it to `int8`, and returns a `number`.

---
<span id="ba-getUint8"></span>

- `getUint8(position)` - returns a `uint8` value.

```lua
local value = data:getUint8(0)
```

Reads `1` byte at the specified position, converts it to `uint8`, and returns a `number`.

---
<span id="ba-getInt16Be"></span>

- `getInt16Be(position)` - returns an `int16` value in **Big Endian** format.

```lua
local value = data:getInt16Be(0)
```

Reads `2` bytes at the specified position and returns a `number`.

---
<span id="ba-getInt16Le"></span>

- `getInt16Le(position)` - returns an `int16` value in **Little Endian** format.

```lua
local value = data:getInt16Le(0)
```

Reads `2` bytes at the specified position and returns a `number`.

---
<span id="ba-getUint16Be"></span>

- `getUint16Be(position)` - returns a `uint16` value in **Big Endian** format.

```lua
local value = data:getUint16Be(0)
```

Reads `2` bytes at the specified position and returns a `number`.

---
<span id="ba-getUint16Le"></span>

- `getUint16Le(position)` - returns a `uint16` value in **Little Endian** format.

```lua
local value = data:getUint16Le(0)
```

Reads `2` bytes at the specified position and returns a `number`.

---
<span id="ba-getInt32Be"></span>

- `getInt32Be(position)` - returns an `int32` value in **Big Endian** format.

```lua
local value = data:getInt32Be(0)
```

Reads `4` bytes at the specified position and returns a `number`.

---
<span id="ba-getInt32Le"></span>

- `getInt32Le(position)` - returns an `int32` value in **Little Endian** format.

```lua
local value = data:getInt32Le(0)
```

Reads `4` bytes at the specified position and returns a `number`.

---
<span id="ba-getUint32Be"></span>

- `getUint32Be(position)` - returns a `uint32` value in **Big Endian** format.

```lua
local value = data:getUint32Be(0)
```

Reads `4` bytes at the specified position and returns a `number`.

---
<span id="ba-getUint32Le"></span>

- `getUint32Le(position)` - returns a `uint32` value in **Little Endian** format.

```lua
local value = data:getUint32Le(0)
```

Reads `4` bytes at the specified position and returns a `number`.

---
<span id="ba-getInt64Be"></span>

- `getInt64Be(position)` - returns an `int64` value in **Big Endian** format.

```lua
local value = data:getInt64Be(0)
```

Reads `8` bytes at the specified position and returns a `number`.

---
<span id="ba-getInt64Le"></span>

- `getInt64Le(position)` - returns an `int64` value in **Little Endian** format.

```lua
local value = data:getInt64Le(0)
```

Reads `8` bytes at the specified position and returns a `number`.

---
<span id="ba-getFloatBe"></span>

- `getFloatBe(position)` - returns a `float` value in **Big Endian** format.

```lua
local value = data:getFloatBe(0)
```

Reads `4` bytes at the specified position and returns a `number`.

---
<span id="ba-getFloatLe"></span>

- `getFloatLe(position)` - returns a `float` value in **Little Endian** format.

```lua
local value = data:getFloatLe(0)
```

Reads `4` bytes at the specified position and returns a `number`.

---
<span id="ba-getDoubleBe"></span>

- `getDoubleBe(position)` - returns a `double` value in **Big Endian** format.

```lua
local value = data:getDoubleBe(0)
```

Reads `8` bytes at the specified position and returns a `number`.

---
<span id="ba-getDoubleLe"></span>

- `getDoubleLe(position)` - returns a `double` value in **Little Endian** format.

```lua
local value = data:getDoubleLe(0)
```

Reads `8` bytes at the specified position and returns a `number`.

---
<span id="ba-getUint8Sequence"></span>

- `getUint8Sequence(position)` - returns a sequence of bytes as an array of numbers from `0` to `255`.

```lua
local table = data:getUint8Sequence(0)
```

Reads bytes starting at the specified position and returns an array (`table`).

---
<span id="ba-getInt8Sequence"></span>

- `getInt8Sequence(position)` - returns a sequence of bytes as an array of numbers from `-128` to `127`.

```lua
local table = data:getInt8Sequence(0)
```

Reads bytes starting at the specified position and returns an array (`table`).

---
<span id="ba-getString"></span>

- `getString(position, [length])` - returns a string.

```lua
local str = data:getString(0, 10)
-- or
local str = data:getString(0)
```

Reads data starting at the specified position and decodes `length` bytes—or all remaining bytes if `length` is omitted—into a UTF-8 string.

---
<span id="ba-getCString"></span>

- `getCString(position, [length])` - returns a string.

```lua
local str = data:getCString(0, 10)
-- or
local str = data:getCString(0)
```

Reads data starting at the specified position and decodes `length` bytes—or all remaining bytes if `length` is omitted—**stopping at the first `\0` character**, into a UTF-8 string.

---
<span id="ba-setBoolean"></span>

- `setBoolean(flag, position)` - writes a `boolean` value.

```lua
local bytes = data:setBoolean(true, 0)
```

Converts the `boolean` into an `int8` value and writes it at the specified position. 
Returns the number of bytes written (`number`).

---
<span id="ba-setInt8"></span>

- `setInt8(number, position)` - writes an `int8` value.

```lua
local bytes = data:setInt8(-127, 0)
```

Converts the `number` into an `int8` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setUint8"></span>

- `setUint8(number, position)` - writes a `uint8` value.

```lua
local bytes = data:setUint8(255, 0)
```

Converts the `number` into a `uint8` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setInt16Be"></span>

- `setInt16Be(number, position)` - writes an `int16` value in **Big Endian** format.

```lua
local bytes = data:setInt16Be(-32768, 0)
```

Converts the `number` into an `int16` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setInt16Le"></span>

- `setInt16Le(number, position)` - writes an `int16` value in **Little Endian** format.

```lua
local bytes = data:setInt16Le(-32768, 0)
```

Converts the `number` into an `int16` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setUint16Be"></span>

- `setUint16Be(number, position)` - writes a `uint16` value in **Big Endian** format.

```lua
local bytes = data:setUint16Be(65535, 0)
```

Converts the `number` into a `uint16` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setUint16Le"></span>

- `setUint16Le(number, position)` - writes a `uint16` value in **Little Endian** format.

```lua
local bytes = data:setUint16Le(65535, 0)
```

Converts the `number` into a `uint16` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setInt32Be"></span>

- `setInt32Be(number, position)` - writes an `int32` value in **Big Endian** format.

```lua
local bytes = data:setInt32Be(-2147483648, 0)
```

Converts the `number` into an `int32` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setInt32Le"></span>

- `setInt32Le(number, position)` - writes an `int32` value in **Little Endian** format.

```lua
local bytes = data:setInt32Le(-2147483648, 0)
```

Converts the `number` into an `int32` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setUint32Be"></span>

- `setUint32Be(number, position)` - writes a `uint32` value in **Big Endian** format.

```lua
local bytes = data:setUint32Be(4294967295, 0)
```

Converts the `number` into a `uint32` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setUint32Le"></span>

- `setUint32Le(number, position)` - writes a `uint32` value in **Little Endian** format.

```lua
local bytes = data:setUint32Le(4294967295, 0)
```

Converts the `number` into a `uint32` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setInt64Be"></span>

- `setInt64Be(number, position)` - writes an `int64` value in **Big Endian** format.

```lua
local bytes = data:setInt64Be(-1000000000000, 0)
```

Converts the `number` into an `int64` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setInt64Le"></span>

- `setInt64Le(number, position)` - writes an `int64` value in **Little Endian** format.

```lua
local bytes = data:setInt64Le(-1000000000000, 0)
```

Converts the `number` into an `int64` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setFloatBe"></span>

- `setFloatBe(number, position)` - writes a `float` value in **Big Endian** format.

```lua
local bytes = data:setFloatBe(3.1415, 0)
```

Converts the `number` into a `float` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setFloatLe"></span>

- `setFloatLe(number, position)` - writes a `float` value in **Little Endian** format.

```lua
local bytes = data:setFloatLe(3.1415, 0)
```

Converts the `number` into a `float` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setDoubleBe"></span>

- `setDoubleBe(number, position)` - writes a `double` value in **Big Endian** format.

```lua
local bytes = data:setDoubleBe(3.1415, 0)
```

Converts the `number` into a `double` value and writes it at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setDoubleLe"></span>

- `setDoubleLe(number, position)` - writes a `double` value in **Little Endian** format.

```lua
local bytes = data:setDoubleLe(3.1415, 0)
```

Converts the `number` into a `double` value and writes it in **Little Endian** format at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setUint8Sequence"></span>

- `setUint8Sequence(table, position)` - writes a sequence of bytes.

```lua
local bytes = data:setUint8Sequence({0x01, 0x02, 0x03, 0x04}, 0)
```

Converts a `table` containing numbers in the `0` to `255` range into a `byte[]` and writes it starting at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setInt8Sequence"></span>

- `setInt8Sequence(table, position)` - writes a sequence of bytes.

```lua
local bytes = data:setInt8Sequence({0x01, 0x02, 0x03, 0x04}, 0) 
```

Converts a `table` containing numbers in the `-128` to `127` range into a `byte[]` and writes it starting at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setString"></span>

- `setString(string, position)` - writes a string.

```lua
local bytes = data:setString("hello world", 0)
```

Converts a `string` into a UTF-8 `byte[]` and writes it starting at the specified position.
Returns the number of bytes written (`number`).

---
<span id="ba-setCString"></span>

- `setCString(string, position)` - writes a null-terminated string.

```lua
local bytes = data:setCString("hello world", 0)
```

Converts a `string` into a UTF-8 `byte[]` and writes it starting at the specified position, appending `\0` immediately after the last written byte.
Returns the number of bytes written (`number`).

---
<span id="ba-example"></span>
## Example

C struct represented as a byte array.

```lua
--[[ 
struct __attribute__((packed)) struct_1_t {
    int8_t a;       // 1 byte
    int64_t b;      // 8 bytes
    uint16_t c[4];  // 8 bytes
    char d[10];     // 10 bytes
    bool e;         // 1 byte
};  // Total size - 28 bytes
]]

-- SEND
local struct1 = ByteArray.allocate(28)
local position = 0
position = position + struct1:setInt8(-127, position)
position = position + struct1:setInt64Le(-9223372036854775808, position)

local cArray = ByteArray.allocate(8)
local position2 = 0
position2 = position2 + cArray:setUint16Le(65535, position2)
position2 = position2 + cArray:setUint16Le(50000, position2)
position2 = position2 + cArray:setUint16Le(40000, position2)
position2 = position2 + cArray:setUint16Le(30000, position2)

position = position + struct1:put(cArray, position)
position = position + struct1:setCString("hello XYZ", position)
position = position + struct1:setBoolean(true, position)

--[[
Client.sendMessage({
    topic = "super_topic",
    qos = 0,
    payload = struct1
})
]]
-- END SEND



-- RECEIVE
-- local struct1 = Client.getIncomingMessage().payload
local position3 = 0
local a = struct1:getInt8(position3)
position3 = position3 + 1
local b = struct1:getInt64Le(position3)
position3 = position3 + 8

-- Position - 9; Size - 8 bytes; One part - 2 bytes (int16_t)
local cArray2 = struct1:split(position3, 8, 2)

local c1 = cArray2[1]:getUint16Le(0)
local c2 = cArray2[2]:getUint16Le(0)
local c3 = cArray2[3]:getUint16Le(0)
local c4 = cArray2[4]:getUint16Le(0)

local d = struct1:getCString(position3 + 8);
local e = struct1:getBoolean(position3 + 9);

Output.print(a, b, c1, c2, c3, c4, d, e)
-- END RECEIVE
```

---

<h1 class="pt-5" id="open_dashboard">open_dashboard</h1>

The `open_dashboard` module is used to retrieve information about open dashboards (dashboards currently open in a browser) and send messages to them.

## Example
```lua
local OpenDashboard = require("open_dashboard")

-- retrieves all open dashboards for a project (table, array);
-- requires passing the project ID
local openDashboards = OpenDashboard.byProjectId(Args.projectId)

-- retrieves all open instances of a specific dashboard (table, array);
-- requires passing the dashboard ID
openDashboards = OpenDashboard.byDashboardId("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")

-- sends a message to all open dashboards
for i = 1, #openDashboards do
    local openDashboard = openDashboards[i]

    -- openDashboard.name         -- dashboard name
    -- openDashboard.id           -- open dashboard ID (each open instance has its own unique ID)
    -- openDashboard.projectId    -- project ID
    -- openDashboard.dashboardId  -- dashboard ID

    openDashboard:sendMessage({key = "value"})
end
```

---
<h1 class="pt-5" id="client_info">client_info</h1>

The `client_info` module is used to retrieve information about clients (ID, project ID, name, URI, and connection status).

Messages can only be published using the injected [Client](/doc/lua_modules#client) service.

## Example
```lua
local ClientInfo = require("client_info")

-- retrieves all clients for a project (table, array);
-- requires passing the project ID
local clients = ClientInfo.byProjectId(Args.projectId)

for i = 1, #clients do
    local client = clients[i]

    -- client.id           -- client ID
    -- client.name         -- client name
    -- client.projectId    -- project ID
    -- client.uri          -- client URI
    -- client.connected    -- connection status
end

-- retrieves a client by its ID (table)
local client = ClientInfo.byClientId("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")
```

---
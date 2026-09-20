<div id="top"></div>
<a href="#top" class="scroll-to-top">↑</a>

[Documentation](/docs)

# Injected Services

[Args](/docs/lua_modules#args)  
[Dashboard](/docs/lua_modules#dashboard)  
[Std](/docs/lua_modules#std)  
[Output](/docs/lua_modules#output)  
[Log](/docs/lua_modules#log)  
[Client](/docs/lua_modules#client)  

# Additional Classes

[ByteArray](/docs/lua_modules#byte_array)

# Additional Modules

[open_dashboard](/docs/lua_modules#open_dashboard)  
[client_info](/docs/lua_modules#client_info)
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

### Injected Services

*Each Script has its own instance of every injected service. Service functions are accessed using the `.` operator.*



<h3 id="args" class="pt-5">Args</h3>

The `Args` service provides access to the arguments passed to a Script.
Every Script receives a default set of arguments: the Project ID, Script ID, execution source, and additional context data.

Custom arguments can be passed in the following cases:
- when a Script is started by the [timer](/docs/lua_modules#timer) module;
- when a Script is started from a Plugin.

##### Example

```lua
Args.projectId    -- Project ID (string)
Args.scriptId     -- Script ID (string)
Args.source       -- Script execution source (string)
```

###### Script Started by a Dashboard

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.dashboardId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID (string)
DASHBOARD,                          -- Execution source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Dashboard ID (string)
]]
```

###### Script Started by a Schedule

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.scheduleId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID (string)
SCHEDULE,                           -- Execution source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Schedule ID (string)
]]
```

###### Script Started by a Subscription

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.subscriptionId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID (string)
SUBSCRIPTION,                       -- Execution source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Subscription ID (string)
]]
```

###### Script Started by Autostart

```lua
Output.print(Args.projectId, Args.scriptId, Args.source)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID (string)
AUTOSTART                           -- Execution source (string)
]]
```

###### Script Started by the "Client Connected" Event

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.clientId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID (string)
CLIENT_CONNECTED,                   -- Execution source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Client ID (string)
]]
```

###### Script Started by the "Client Disconnected" Event

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.clientId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID (string)
CLIENT_DISCONNECTED,                -- Execution source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Client ID (string)
]]
```

###### Script Started by the "Script Error" Event

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
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID (string)
SCRIPT_ERROR,                       -- Execution source (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID where the error occurred (string)
1,                                  -- Current number of errors (number)
10,                                 -- Maximum allowed number of errors (number)
reason,                             -- Error reason (string)
stack                               -- Error stack trace (string)
]]
```

###### Script Started from the Lua Editor

```lua
Output.print(Args.projectId, Args.scriptId, Args.source)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID (string)
DEV                                 -- Execution source (string). This value can be changed in the Lua editor.
]]
```

###### Script Started by a Lua Library (Timer Example)

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.luaLib)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Project ID (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Script ID (string)
LUA_LIB,                            -- Execution source (string)
timer,                              -- Lua library ID (or Plugin ID) (string)
]]
```

---



<h3 class="pt-5" id="dashboard">Dashboard</h3>

The `Dashboard` service is used to receive incoming messages from a Dashboard and send messages back to it.

Messages can only be received and sent from Scripts that were started by a Dashboard.

To send messages to open Dashboards without starting a Script from a Dashboard, use the [open_dashboard](#open_dashboard) module.

##### Example

```lua
-- returns true if an incoming message is available
local flag = Dashboard.isIncomingMessageAvailable()

-- returns the incoming message (table)
-- this is the same object that was passed to:
-- dashboard.execute({ key : "value" })
-- returns nil if no message is available
local incomingMessage = Dashboard.getIncomingMessage()

-- returns the Open Dashboard ID (string)
-- every open Dashboard has its own unique ID
-- returns nil if the Dashboard is unavailable
local id = Dashboard.getOpenDashboardId()

-- send a message to the Dashboard
-- if the Dashboard is already closed or unavailable,
-- the message will be ignored
local outgoingMessage = {
    key = "Pi",
    value = 3.14
}

Dashboard.sendMessage(outgoingMessage)
```

---



<h3 class="pt-5" id="std">Std</h3>

The `Std` service is used to redirect the **STDOUT** and **STDERR** output streams.

##### Example

```lua
-- captures the STDOUT stream and invokes the callback function,
-- passing a single line of text as its argument (UTF-8)
Std.captureRowOut(function(row)

end)

-- captures the STDERR stream and invokes the callback function,
-- passing a single line of text as its argument (UTF-8)
Std.captureRowErr(function(row)

end)

-- captures the STDOUT stream and invokes the callback function,
-- passing one byte as its argument (number in the range -128 to 127)
Std.captureByteOut(function(value)

end)

-- captures the STDERR stream and invokes the callback function,
-- passing one byte as its argument (number in the range -128 to 127)
Std.captureByteErr(function(value)

end)

-- restores the default STDOUT stream (java.lang.System.out)
Std.restoreOut()

-- restores the default STDERR stream (java.lang.System.err)
Std.restoreErr()



-- example of capturing the STDOUT stream into a buffer
local buffer = {}

Std.captureRowOut(function(row)
    table.insert(buffer, row)
end)

os.execute("ls -l")

Std.restoreOut() 

Output.print(table.concat(buffer, "\n"))



-- example of capturing the STDOUT stream into a ByteArray
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



<h3 class="pt-5" id="output">Output</h3>

The `Output` service is used for printing messages to the **Dev** output.

If the Script is not running in **Dev** mode, calls to `Output.print()` are ignored.

##### Example

```lua

Output.print("message_1", "message_2", "message_n")

```


---



<h3 class="pt-5" id="log">Log</h3>

The `Log` service is used to write messages to the log.

##### Example

```lua

-- write a debug-level message
Log.debug("debug")
Log.debug({Pi = 3.14})
Log.debug("string", {Pi = 3.14})

-- write an info-level message
Log.info("info")
Log.info({Pi = 3.14})
Log.info("string", {Pi = 3.14})

-- write a warning-level message
Log.warn("warning")
Log.warn({Pi = 3.14})
Log.warn("string", {Pi = 3.14})

-- write an error-level message
Log.error("error")
Log.error({Pi = 3.14})
Log.error("string", {Pi = 3.14})

-- returns true if the debug logging level is enabled
local flag = Log.isDebugEnabled()

-- perform expensive message preparation only
-- if the debug logging level is enabled
if Log.isDebugEnabled() then
    Log.debug(prepareReallyLongDebugMessage())
end

```

---



<h3 id="client" class="pt-5">Client</h3>

The `Client` service provides access to the MQTT Client.

It allows receiving incoming messages and sending outgoing messages. Message payloads are represented by `ByteArray` instances.

Incoming and outgoing messages can only be accessed from Scripts that were started by a Subscription.

If the Script was started in any other way, call `setup(clientId)` and specify the Client ID that should be used to send messages.

##### Example

```lua
-- returns true if an incoming message is available
local flag = Client.isIncomingMessageAvailable()

-- returns the incoming message (table)
-- returns nil if no message is available
local incomingMessage = Client.getIncomingMessage()

-- MQTT topic (string)
local topic = incomingMessage.topic

-- MQTT Topic Filter that matched the message (string)
local topicFilter = incomingMessage.topicFilter

-- QoS level (number)
local qos = incomingMessage.qos

-- get the payload
local payload = incomingMessage.payload
local jsonStr = payload:getString(0)

-- select the Client used to send messages
-- specify the Client ID
-- if the Script was started by a Subscription,
-- calling setup() is not required
Client.setup("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")

-- returns the current Client ID
-- returns nil if no Client is selected
Output.print(Client.clientId)

-- send a message
local outgoingMessage = {
    topic = "project/super_topic",
    qos = 0,
    payload = ByteArray.fromString("{\"key\":\"value\"}")
}

-- returns true if the message was sent successfully
local ok = Client.sendMessage(outgoingMessage)
```

---



# Additional Classes




<h3 id="byte_array" class="pt-5">ByteArray</h3>

The `ByteArray` class is used to work with message payloads in the `Client` service. It is a wrapper around Java `byte[]` that provides convenient functions for reading, writing, and converting binary data.

> **Note:** the `position` parameter is a zero-based array index (starts from `0`, not `1`).

### Functionality  

- [allocate](/docs/lua_modules#ba-allocate)
- [fromString](/docs/lua_modules#ba-fromString)
- [fromHexString](/docs/lua_modules#ba-fromHexString)
- [fromBase64String](/docs/lua_modules#ba-fromBase64String)
- [fromJBytes](/docs/lua_modules#ba-fromJBytes)
- [split](/docs/lua_modules#ba-split)
- [length](/docs/lua_modules#ba-length)
- [payloadLength](/docs/lua_modules#ba-payloadLength)
- [toHexString](/docs/lua_modules#ba-toHexString)
- [toBase64String](/docs/lua_modules#ba-toBase64String)
- [getJBytes](/docs/lua_modules#ba-getJBytes)
- [put](/docs/lua_modules#ba-put)
- [merge](/docs/lua_modules#ba-merge)
- [copy](/docs/lua_modules#ba-copy)
- [getInt8](/docs/lua_modules#ba-getInt8)
- [getUint8](/docs/lua_modules#ba-getUint8)
- [getInt16Be](/docs/lua_modules#ba-getInt16Be)
- [getInt16Le](/docs/lua_modules#ba-getInt16Le)
- [getUint16Be](/docs/lua_modules#ba-getUint16Be)
- [getUint16Be](/docs/lua_modules#ba-getUint16Be)
- [getInt32Be](/docs/lua_modules#ba-getInt32Be)
- [getInt32Le](/docs/lua_modules#ba-getInt32Le)
- [getUint32Be](/docs/lua_modules#ba-getUint32Be)
- [getUint32Le](/docs/lua_modules#ba-getUint32Le)
- [getInt64Be](/docs/lua_modules#ba-getInt64Be)
- [getInt64Le](/docs/lua_modules#ba-getInt64Le)
- [getFloatBe](/docs/lua_modules#ba-getFloatBe)
- [getFloatLe](/docs/lua_modules#ba-getFloatLe)
- [getDoubleBe](/docs/lua_modules#ba-getDoubleBe)
- [getDoubleLe](/docs/lua_modules#ba-getDoubleLe)
- [getUint8Sequence](/docs/lua_modules#ba-getUint8Sequence)
- [getInt8Sequence](/docs/lua_modules#ba-getInt8Sequence)
- [getString](/docs/lua_modules#ba-getString)
- [getSafeString](/docs/lua_modules#ba-getSafeString)
- [setInt8](/docs/lua_modules#ba-setInt8)
- [setUint8](/docs/lua_modules#ba-setUint8)
- [setInt16Be](/docs/lua_modules#ba-setInt16Be)
- [setInt16Le](/docs/lua_modules#ba-setInt16Le)
- [setUint16Be](/docs/lua_modules#ba-setUint16Be)
- [setUint16Le](/docs/lua_modules#ba-setUint16Le)
- [setInt32Be](/docs/lua_modules#ba-setInt32Be)
- [setInt32Le](/docs/lua_modules#ba-setInt32Le)
- [setUint32Be](/docs/lua_modules#ba-setUint32Be)
- [setUint32Le](/docs/lua_modules#ba-setUint32Le)
- [setInt64Be](/docs/lua_modules#ba-setInt64Be)
- [setInt64Le](/docs/lua_modules#ba-setInt64Le)
- [setFloatBe](/docs/lua_modules#ba-setFloatBe)
- [setFloatLe](/docs/lua_modules#ba-setFloatLe)
- [setDoubleBe](/docs/lua_modules#ba-setDoubleBe)
- [setDoubleLe](/docs/lua_modules#ba-setDoubleLe)
- [setUint8Sequence](/docs/lua_modules#ba-setUint8Sequence)
- [setInt8Sequence](/docs/lua_modules#ba-setInt8Sequence)
- [setString](/docs/lua_modules#ba-setString)



##### Static Functions



<span id="ba-allocate"></span>

- `allocate(length)` — creates a new `ByteArray` instance.

```lua
local data = ByteArray.allocate(64)
```

Allocates `64` bytes of memory and returns a new `ByteArray` instance.

---



<span id="ba-fromString"></span>

- `fromString(string)` — creates a `ByteArray` from a UTF-8 string.

```lua
local data = ByteArray.fromString("{\"key\":\"value\"}")
```

Converts the string to a UTF-8 byte array and returns a `ByteArray` instance.

---



<span id="ba-fromHexString"></span>

- `fromHexString(hexString)` — creates a `ByteArray` from a hexadecimal string.

```lua
local data = ByteArray.fromHexString("48656C6C6F20576F726C64")
```

Converts the hexadecimal string to a byte array and returns a `ByteArray` instance.

---



<span id="ba-fromBase64String"></span>

- `fromBase64String(base64String)` — creates a `ByteArray` from a Base64 string.

```lua
local data = ByteArray.fromBase64String("aGVsbG8gd29ybGQ=")
```

Converts the Base64 string to a byte array and returns a `ByteArray` instance.

---



<span id="ba-fromJBytes"></span>

- `fromJBytes(userdata)` — creates a `ByteArray` from a Java `byte[]`.

```lua
local data = ByteArray.fromJBytes(anotherByteArray:getJBytes())
```

Creates a `ByteArray` instance from a Java `byte[]`.

---



##### Instance Methods

*In the examples below, `data` is a `ByteArray` instance.*



<span id="ba-split"></span>

- `split(position, length, chunkSize)` — creates a slice of the data, splits it into equal-sized chunks, and returns an array (`table`) of `ByteArray` instances.

```lua
local array = data:split(2, 16, 4)
```

Creates a slice starting at position `2` with a length of `16` bytes and splits it into chunks of `4` bytes each.

```
[0x00, 0x01,
0x02, 0x03, 0x04, 0x05,
0x06, 0x07, 0x08, 0x09,
0x0A, 0x0B, 0x0C, 0x0D,
0x0E, 0x0F, 0x10, 0x11,
0x12, 0x13, 0x14, 0x15, 0x16]
```

Returns an array containing `4` `ByteArray` instances.

```
[0x02, 0x03, 0x04, 0x05]
[0x06, 0x07, 0x08, 0x09]
[0x0A, 0x0B, 0x0C, 0x0D]
[0x0E, 0x0F, 0x10, 0x11]
```

---



<span id="ba-length"></span>

- `length()` — returns the size of the internal byte array.

```lua
local length = data:length()
```

Returns a value of type `number`.

---



<span id="ba-payloadLength"></span>

- `payloadLength()` — Returns the number of bytes up to the last non-zero byte. Trailing `0x00` bytes are not included.

##### Example

The array contains `8` bytes (`0xFF, 0xFF, 0xFF, 0x00, 0xAA, 0x00, 0x00, 0x00`),
but only the first `5` bytes are considered part of the payload
(`0xFF, 0xFF, 0xFF, 0x00, 0xAA`).

```lua
local payloadLength = data:payloadLength()
```

Returns a value of type `number`.

---



<span id="ba-getHexString"></span>

- `getHexString()` — returns the data as a hexadecimal string.

```lua
local str = data:getHexString()
```

Returns a hexadecimal string.

---



<span id="ba-toBase64String"></span>

- `toBase64String()` — returns the data as a Base64 string.

```lua
local str = data:toBase64String()
```

Returns a Base64 string.

---



<span id="ba-getJBytes"></span>

- `getJBytes()` — returns the internal Java `byte[]`.

```lua
local bytes = data:getJBytes()
```

Returns a `userdata` object containing the Java `byte[]`.

---



<span id="ba-put"></span>

- `put(anotherByteArray, position)` — copies the contents of another `ByteArray` into the current one.

```lua
data:put(data2, 8)
```

Copies the data starting at position `8`.

---



<span id="ba-merge"></span>

- `merge(anotherByteArray, position)` — merges the current `ByteArray` with another one.

```lua
local newData = data:merge(data2, 8)
```

Performs the merge starting at position `8` and returns a new `ByteArray` instance.

---



<span id="ba-copy"></span>

- `copy(position, length)` — creates a copy of a portion of the data.

```lua
local newData = data:copy(2, 4)
```

Creates a copy of `4` bytes starting at position `2` and returns a new `ByteArray` instance.

---



<span id="ba-getInt8"></span>

- `getInt8(position)` — returns an `int8` value.

```lua
local value = data:getInt8(0)
```

Reads `1` byte from the specified position, converts it to `int8`, and returns a value of type `number`.

---



<span id="ba-getUint8"></span>

- `getUint8(position)` — returns a `uint8` value.

```lua
local value = data:getUint8(0)
```

Reads `1` byte from the specified position, converts it to `uint8`, and returns a value of type `number`.

---



<span id="ba-getInt16Be"></span>

- `getInt16Be(position)` — returns an `int16` value in **Big-endian** format.

```lua
local value = data:getInt16Be(0)
```

Reads `2` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getInt16Le"></span>

- `getInt16Le(position)` — returns an `int16` value in **Little-endian** format.

```lua
local value = data:getInt16Le(0)
```

Reads `2` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getUint16Be"></span>

- `getUint16Be(position)` — returns a `uint16` value in **Big-endian** format.

```lua
local value = data:getUint16Be(0)
```

Reads `2` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getUint16Le"></span>

- `getUint16Le(position)` — returns a `uint16` value in **Little-endian** format.

```lua
local value = data:getUint16Le(0)
```

Reads `2` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getInt32Be"></span>

- `getInt32Be(position)` — returns an `int32` value in **Big-endian** format.

```lua
local value = data:getInt32Be(0)
```

Reads `4` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getInt32Le"></span>

- `getInt32Le(position)` — returns an `int32` value in **Little-endian** format.

```lua
local value = data:getInt32Le(0)
```

Reads `4` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getUint32Be"></span>

- `getUint32Be(position)` — returns a `uint32` value in **Big-endian** format.

```lua
local value = data:getUint32Be(0)
```

Reads `4` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getUint32Le"></span>

- `getUint32Le(position)` — returns a `uint32` value in **Little-endian** format.

```lua
local value = data:getUint32Le(0)
```

Reads `4` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getInt64Be"></span>

- `getInt64Be(position)` — returns an `int64` value in **Big-endian** format.

```lua
local value = data:getInt64Be(0)
```

Reads `8` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getInt64Le"></span>

- `getInt64Le(position)` — returns an `int64` value in **Little-endian** format.

```lua
local value = data:getInt64Le(0)
```

Reads `8` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getFloatBe"></span>

- `getFloatBe(position)` — returns a `float` value in **Big-endian** format.

```lua
local value = data:getFloatBe(0)
```

Reads `4` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getFloatLe"></span>

- `getFloatLe(position)` — returns a `float` value in **Little-endian** format.

```lua
local value = data:getFloatLe(0)
```

Reads `4` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getDoubleBe"></span>

- `getDoubleBe(position)` — returns a `double` value in **Big-endian** format.

```lua
local value = data:getDoubleBe(0)
```

Reads `8` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getDoubleLe"></span>

- `getDoubleLe(position)` — returns a `double` value in **Little-endian** format.

```lua
local value = data:getDoubleLe(0)
```

Reads `8` bytes from the specified position and returns a value of type `number`.

---



<span id="ba-getUint8Sequence"></span>

- `getUint8Sequence(position)` — returns a sequence of bytes as an array of numbers from `0` to `255`.

```lua
local table = data:getUint8Sequence(0)
```

Reads the bytes starting at the specified position and returns them as an array (`table`).

---



<span id="ba-getInt8Sequence"></span>

- `getInt8Sequence(position)` — returns a sequence of bytes as an array of numbers from `-128` to `127`.

```lua
local table = data:getInt8Sequence(0)
```

Reads the bytes starting at the specified position and returns them as an array (`table`).

---



<span id="ba-getString"></span>

- `getString(position, [length])` — returns a string.

```lua
local str = data:getString(0, 10)
-- or
local str = data:getString(0)
```

Reads data starting at the specified position and converts either `length` bytes or all remaining bytes (if `length` is omitted) to a UTF-8 string.

---



<span id="ba-getSafeString"></span>

- `getSafeString(position, [length])` — returns a string.

```lua
local str = data:getSafeString(0, 10)
-- or
local str = data:getSafeString(0)
```

Reads data starting at the specified position and converts either `length` bytes, all remaining bytes (if `length` is omitted), or bytes up to the first `\0` character into a UTF-8 string.

---



<span id="ba-setInt8"></span>

- `setInt8(number, position)` — writes an `int8` value.

```lua
data:setInt8(-127, 0)
```

Converts `number` to an `int8` value and writes it at the specified position.

---



<span id="ba-setUint8"></span>

- `setUint8(number, position)` — writes a `uint8` value.

```lua
data:setUint8(255, 0)
```

Converts `number` to a `uint8` value and writes it at the specified position.

---



<span id="ba-setInt16Be"></span>

- `setInt16Be(number, position)` — writes an `int16` value in **Big-endian** format.

```lua
data:setInt16Be(-32768, 0)
```

Converts `number` to an `int16` value and writes it at the specified position.

---



<span id="ba-setInt16Le"></span>

- `setInt16Le(number, position)` — writes an `int16` value in **Little-endian** format.

```lua
data:setInt16Le(-32768, 0)
```

Converts `number` to an `int16` value and writes it at the specified position.

---



<span id="ba-setUint16Be"></span>

- `setUint16Be(number, position)` — writes a `uint16` value in **Big-endian** format.

```lua
data:setUint16Be(65535, 0)
```

Converts `number` to a `uint16` value and writes it at the specified position.

---



<span id="ba-setUint16Le"></span>

- `setUint16Le(number, position)` — writes a `uint16` value in **Little-endian** format.
-
```lua
data:setUint16Le(65535, 0)
```

Converts `number` to a `uint16` value and writes it at the specified position.

---



<span id="ba-setInt32Be"></span>

- `setInt32Be(number, position)` — writes an `int32` value in **Big-endian** format.

```lua
data:setInt32Be(-2147483648, 0)
```

Converts `number` to an` int32` value and writes it at the specified position.

---



<span id="ba-setInt32Le"></span>

- `setInt32Le(number, position)` — writes an `int32` value in **Little-endian** format.

```lua
data:setInt32Le(-2147483648, 0)
```

Converts `number` to an `int32` value and writes it at the specified position.

---



<span id="ba-setUint32Be"></span>

- `setUint32Be(number, position)` — writes a `uint32` value in **Big-endian** format.

```lua
data:setUint32Be(4294967295, 0)
```

Converts `number` to a `uint32` value and writes it at the specified position.

---



<span id="ba-setUint32Le"></span>

- `setUint32Le(number, position)` — writes a `uint32` value in **Little-endian** format.

```lua
data:setUint32Le(4294967295, 0)
```

Converts `number` to a `uint32` value and writes it at the specified position.

---



<span id="ba-setInt64Be"></span>

- `setInt64Be(number, position)` — writes an `int64` value in **Big-endian** format.

```lua
data:setInt64Be(-1000000000000, 0)
```

Converts `number` to an `int64` value and writes it at the specified position.

---



<span id="ba-setInt64Le"></span>

- `setInt64Le(number, position)` — writes an `int64` value in **Little-endian** format.

```lua
data:setInt64Le(-1000000000000, 0)
```

Converts `number` to an `int64` value and writes it at the specified position.

---



<span id="ba-setFloatBe"></span>

- `setFloatBe(number, position)` — writes a `float` value in **Big-endian** format.

```lua
data:setFloatBe(3.1415, 0)
```

Converts `number` to a `float` value and writes it at the specified position.

---



<span id="ba-setFloatLe"></span>

- `setFloatLe(number, position)` — writes a `float` value in **Little-endian** format.

```lua
data:setFloatLe(3.1415, 0)
```

Converts `number` to a `float` value and writes it at the specified position.

---



<span id="ba-setDoubleBe"></span>

- `setDoubleBe(number, position)` — writes a `double` value in **Big-endian** format.

```lua
data:setDoubleBe(3.1415, 0)
```

Converts `number` to a `double` value and writes it at the specified position.

---



<span id="ba-setDoubleLe"></span>

- `setDoubleLe(number, position)` — writes a `double` value in **Little-endian** format.

```lua
data:setDoubleLe(3.1415, 0)
```

Converts `number` to a `double` value and writes it at the specified position.

---



<span id="ba-setUint8Sequence"></span>

- `setUint8Sequence(table, position)` — writes a sequence of bytes.

```lua
data:setUint8Sequence({0x01, 0x02, 0x03, 0x04}, 0)
```

Converts `table`, containing numbers in the range from `0` to `255`, to a Java `byte[]` and writes it starting at the specified position.

---




<span id="ba-setInt8Sequence"></span>

- `setInt8Sequence(table, position)` — writes a sequence of bytes.

```lua
data:setInt8Sequence({0x01, 0x02, 0x03, 0x04}, 0)
```

Converts `table`, containing numbers in the range from `-128` to `127`, to a Java `byte[]` and writes it starting at the specified position.

---




<span id="ba-setString"></span>

- `setString(string, position)` — writes a UTF-8 string.

```lua
data:setString("hello world", 0)
```

Converts `string` to a UTF-8 encoded `byte[]` and writes it starting at the specified position.

---



##### Example

Reading data from a C structure. The structure is received from an ESP32 as a byte array.

```lua
--[[ Structure definition
struct __attribute__((packed)) struct_1_t {
    uint8_t a;     // 1 byte
    uint32_t b;    // 4 bytes
    int16_t c[4];  // 8 bytes
};
]]

local struct1 = incomingMessage.payload

local a = struct1:getUint8(0)
local b = struct1:getUint32Le(1)

-- Starting at position 5:
-- length: 8 bytes;
-- element size: 2 bytes (int16_t).
local arrayC = struct1:split(5, 8, 2)

local c1 = arrayC[1]:getInt16Le(0)
local c2 = arrayC[2]:getInt16Le(0)
local c3 = arrayC[3]:getInt16Le(0)
local c4 = arrayC[4]:getInt16Le(0)

Output.print(a, b, c1, c2, c3, c4)
```

Sending data as a C structure. The structure is sent to the ESP32 as a byte array.

```lua
--[[ Structure definition
struct __attribute__((packed)) struct_2_t {
    int8_t a;       // 1 byte
    int64_t b;      // 8 bytes
    uint16_t c[4];  // 8 bytes
    char d[10];     // 10 bytes
};  // Total size: 27 bytes
]]

local struct2 = ByteArray.allocate(27)

struct2:setInt8(-127, 0)
struct2:setInt64Le(-9223372036854775808, 1)

local arrayC = ByteArray.allocate(8)
arrayC:setUint16Le(65535, 0)
arrayC:setUint16Le(50000, 2)
arrayC:setUint16Le(40000, 4)
arrayC:setUint16Le(30000, 6)

struct2:put(arrayC, 9)

-- the last byte will be '\0'
struct2:setString("hello 123", 17)

-- send struct2
```

---



<h3 class="pt-5" id="open_dashboard">open_dashboard</h3>

The `open_dashboard` module is used to retrieve information about open Dashboards (Dashboards that are currently open in a browser) and send messages to them.

##### Example
```lua
local OpenDashboard = require("open_dashboard")

-- returns all open Dashboards in the Project (table, array);
-- the Project ID must be specified
local openDashboards = OpenDashboard.byProjectId(Args.projectId)

-- returns all open instances of a Dashboard (table, array);
-- the Dashboard ID must be specified
openDashboards = OpenDashboard.byDashboardId("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")

-- sends a message to all open Dashboards
for i = 1, #openDashboards do
    local openDashboard = openDashboards[i]

    -- openDashboard.name         -- Dashboard name
    -- openDashboard.id           -- Open Dashboard ID (each open instance has its own unique ID)
    -- openDashboard.projectId    -- Project ID
    -- openDashboard.dashboardId  -- Dashboard ID

    openDashboard:sendMessage({key = "value"})
end
```

---



<h3 class="pt-5" id="client_info">client_info</h3>

The `client_info` module is used to retrieve information about Clients (ID, Project ID, name, URI, and connection status).

Messages can only be sent using the injected [Client](/docs/lua_modules#client) service.

##### Example
```lua
local ClientInfo = require("client_info")

-- returns all Clients in the Project (table, array);
-- the Project ID must be specified
local clients = ClientInfo.byProjectId(Args.projectId)

for i = 1, #clients do
    local client = clients[i]

    -- client.id           -- Client ID
    -- client.name         -- Client name
    -- client.projectId    -- Project ID
    -- client.uri          -- Client URI
    -- client.connected    -- Connection status
end

-- returns a Client by its ID (table)
local client = ClientInfo.byClientId("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")
```

---




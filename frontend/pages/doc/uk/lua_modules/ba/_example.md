<span id="ba-example"></span>
## Приклад

C-Структура як масив байтів.

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

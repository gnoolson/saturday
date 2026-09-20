[Documentation](/docs)

# Lua Quick Guide

This section is **not** a complete Lua tutorial. It contains only the basic language constructs required to write Scripts.

### Useful Links

- Official Lua website — https://www.lua.org/
- Official Lua 5.3 Reference Manual — https://www.lua.org/manual/5.3/
- Programming in Lua — https://www.lua.org/pil/

---



### Data Types

**number** — 0, 123, 3.14  
**string** — "simple string"  
**boolean** — true / false  
**table** — {}  
**nil** — absence of a value  
**userdata** — external data  
**function** — function  
**coroutine** — coroutine

---



### Naming Convention

```
Type                Convention
Modules             snake_case
Tables (classes)    PascalCase
Methods/functions   camelCase
Local variables     camelCase
Object fields       camelCase
Constants           UPPER_SNAKE_CASE
Private fields      _camelCase
```

---



### Comments

```lua
-- single-line comment

--[[
multi-line
comment
]]
```

---



### Variables

```
globalValue = 3.14           -- global variable
local localValue = 100       -- local variable
```

---



### Conditions

```
local flag = true

if flag then
-- action
end

flag = false

if not flag then
-- action
end

local x = 60

if x == 100 then
-- action
elseif x ~= 60 then
-- "~=" means "not equal"
elseif x < 18 then
-- action
elseif (x >= 16) and (x < 18) then
-- logical "and"
elseif (x > 10000) or (x < -10000) then
-- logical "or"
else
-- executed if none of the conditions are met
end
```

---



### Checking for nil

```lua
if value == nil then
    -- value is absent
end

if value ~= nil then
    -- value exists
end
```

---



### Strings

```lua
local name = "Donald"

local hero = name .. " Duck" -- string concatenation

local multiLine = [[
first row
second row
"other row"
'qwerty'
]]
```

---



### Functions

```lua
function getSum(num1, num2)
    return num1 + num2
end

local result = getSum(2, 3)

function fun()
    return "value1", "value2", "valueN" -- returning multiple values
end

local v1, v2, vN = fun()
```

---



### Tables (Objects, Arrays)

```lua
local value1 = {} -- empty table

local value2 = {
    pi = 3.14,
    e = 2.71828
}

local pi = value2.pi

local value3 = {
    ["simple string"] = 123456789
}

local simpleString = value3["simple string"] -- string as a key

local value4 = {
    [3.14] = 987654321
}

local v = value4[3.14] -- number as a key

local value5 = {1, 2, 3, "str", {}, nil, 4}
local secondValue = value5[2] -- indexing starts from 1

local value6 = {
    action = function()
        -- action
    end
}

value6.action()

local value7 = {
    message = "Hello",

    sayHello = function(self)
        Output.print(self.message)
    end
}

value7.sayHello(value7) -- call using "."
value7:sayHello()       -- call using ":" (self is passed automatically)
```

---



### Loops

```lua
local i = 1

while i <= 10 do
    i = i + 1

    if i == 8 then
        break
    end
end

for i = 1, 10, 1 do
    -- start value, end value, step
end

local months = {
    "January",
    "February",
    "March",
    "April"
}

for index, value in ipairs(months) do
    -- iterate over an array
end

for key, value in pairs(months) do
    -- iterate over a table
end

local length = #months -- array length
Output.print(length)
```

---



### Modules

```lua
local module = require("module_name")

package.path = package.path .. ";/data/projects/java/saturday/lualib/?.lua"
```

---





### Error Handling (pcall)

```lua
local function divide(a, b)
    -- error("message")

    return a / b
end

local success, result = pcall(divide, 10, "apple")

if success then
    Output.print("Success! Result: " .. result)
else
    Output.print("Error: " .. result)
end
```

---



### OOP

```lua
Box = {}
Box.__index = Box

function Box:new(height, width, length)
    local newObj = {
        height = height,
        width = width,
        length = length
    }

    setmetatable(newObj, Box)

    return newObj
end

function Box:volume()
    return self.height * self.width * self.length
end

local box = Box:new(2, 2, 4)
local volume = box:volume()
```

---



### Coroutines

A coroutine is a function whose execution can be suspended and resumed later. It does **not** run in parallel. Execution is controlled using `yield`.

```lua
local co1 = coroutine.create(function()
    Output.print("step 1")

    coroutine.yield()

    Output.print("step 2")
end)

coroutine.resume(co1)
coroutine.resume(co1)

--[[ output

step 1
step 2

]]

local co2 = coroutine.create(function(x)
    Output.print("start", x)

    local y = coroutine.yield(x + 1)

    Output.print("resume with", y)
end)

local success, value = coroutine.resume(co2, 10)

Output.print(success)
Output.print(value)

coroutine.resume(co2, 20)
```

---




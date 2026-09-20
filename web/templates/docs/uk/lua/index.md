[Документація](/docs)

# Коротко про Lua

Цей розділ не є повним посібником з Lua. Він містить лише базові конструкції, необхідні для написання Сценаріїв.

### Корисні посилання

- Офіційний сайт Lua — https://www.lua.org/
- Офіційний довідник Lua 5.3 — https://www.lua.org/manual/5.3/
- Programming in Lua — https://www.lua.org/pil/

---



### Типи даних

**number** — 0, 123, 3.14  
**string** — "simple string"  
**boolean** — true / false  
**table** — {}  
**nil** — відсутність значення  
**userdata** — зовнішні дані  
**function** — функція  
**coroutine** — корутина

---



### Конвенція іменування

```
Тип                             Convention
Модулі                          snake_case
Таблиці (класи)                 PascalCase
Методи                          camelCase
Локальні змінні                 camelCase
Поля об'єктів                   camelCase
Константи                       UPPER_SNAKE_CASE
Приватні поля                   _camelCase
Локальні функції                camelCase
```

---



### Коментарі

```lua
-- однорядковий коментар

--[[
багаторядковий
коментар
]]
```

---



### Змінні

```lua
globalValue = 3.14           -- глобальна змінна
local localValue = 100       -- локальна змінна
```


---



### Умови

```lua
local flag = true

if flag then
    -- дія
end

flag = false

if not flag then
    -- дія
end

local x = 60

if x == 100 then
    -- дія
elseif x ~= 60 then
    -- "~=" означає "не дорівнює"
elseif x < 18 then
    -- дія
elseif (x >= 16) and (x < 18) then
    -- логічне "і"
elseif (x > 10000) or (x < -10000) then
    -- логічне "або"
else
    -- виконується, якщо жодна умова не виконалася
end
```

---



### Перевірка nil

```lua
if value == nil then
    -- значення відсутнє
end

if value ~= nil then
    -- значення існує
end
```

---



### Текст

```lua
local name = "Donald"

local hero = name .. " Duck" -- конкатенація рядків

local multiLine = [[
first row
second row
"other row"
'qwerty'
]]
```

---



### Функції

```lua
function getSum(num1, num2)
    return num1 + num2
end

local result = getSum(2, 3)

function fun()
    return "value1", "value2", "valueN" -- повернення декількох значень
end

local v1, v2, vN = fun()
```

---



### Таблиці (об'єкти, масиви)

```lua
local value1 = {} -- порожня таблиця

local value2 = {
    pi = 3.14,
    e = 2.71828
}

local pi = value2.pi

local value3 = {
    ["simple string"] = 123456789
}

local simpleString = value3["simple string"] -- рядок як ключ

local value4 = {
    [3.14] = 987654321
}

local v = value4[3.14] -- число як ключ

local value5 = {1, 2, 3, "str", {}, nil, 4}
local secondValue = value5[2] -- індексація починається з 1

local value6 = {
    action = function()
        -- дія
    end
}

value6.action()

local value7 = {
    message = "Hello",

    sayHello = function(self)
        Output.print(self.message)
    end
}

value7.sayHello(value7) -- виклик через "."
value7:sayHello()       -- виклик через ":" (self передається автоматично)
```

---



### Цикли

```lua
local i = 1

while i <= 10 do
    i = i + 1

    if i == 8 then
        break
    end
end

for i = 1, 10, 1 do
    -- початкове значення, кінцеве значення, крок
end

local months = {
    "January",
    "February",
    "March",
    "April"
}

for index, value in ipairs(months) do
    -- для масивів
end

for key, value in pairs(months) do
    -- для таблиць
end

local length = #months -- довжина масиву
Output.print(length)
```

---



### Модулі

```lua
local module = require("module_name")

package.path = package.path .. ";/data/projects/java/saturday/lualib/?.lua"
```

---



### Обробка помилок (pcall)

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



### Coroutine

Корутина — це функція, виконання якої можна призупинити та пізніше продовжити. Вона не виконується паралельно. Керування здійснюється за допомогою `yield`.

```lua
local co1 = coroutine.create(function()
    Output.print("step 1")

    coroutine.yield()

    Output.print("step 2")
end)

coroutine.resume(co1)
coroutine.resume(co1)

--[[ результат

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




<div id="top"></div>
<a href="#top" class="scroll-to-top">↑</a>

[Документація](/docs)



# Додаткові інжектовані сервіси

[Args](/docs/lua_modules#args)  
[Dashboard](/docs/lua_modules#dashboard)  
[Std](/docs/lua_modules#std)  
[Output](/docs/lua_modules#output)  
[Log](/docs/lua_modules#log)  
[Client](/docs/lua_modules#client)  



# Додаткові класи

[ByteArray](/docs/lua_modules#byte_array)



# Додаткові модулі

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


### Інжектовані сервіси

_Кожен Сценарій має власний екземпляр інжектованого сервісу. Доступ до його функцій здійснюється через оператор `.`._



<h3 id="args" class="pt-5">Args</h3>

Сервіс `Args` використовується для отримання аргументів, переданих у Сценарій.
Кожен Сценарій отримує базовий набір аргументів: ідентифікатор Проєкту, ідентифікатор Сценарію, джерело запуску та додаткові дані.

Передати власні аргументи можна в таких випадках:
- запуск Сценарію модулем [timer](/docs/lua_modules#timer);
- запуск Сценарію з Плагіна.

##### Приклад

```lua
Args.projectId    -- ідентифікатор Проєкту (string)
Args.scriptId     -- ідентифікатор Сценарію (string)
Args.source       -- джерело запуску Сценарію (string)
```

###### Панель запустила Сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.dashboardId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію (string)
DASHBOARD,                          -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Id Панелі (string)
]]
```

###### Розклад запустив Сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.scheduleId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію (string)
SCHEDULE,                           -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Id Розкладу (string)
]]
```

###### Підписка запустила Сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.subscriptionId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію (string)
SUBSCRIPTION,                       -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Id Підписки (string)
]]
```

###### Автостарт запустив Сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію (string)
AUTOSTART                           -- Джерело запуску (string)
]]
```

###### Подія «Клієнт під'єднався до брокера» запустила Сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.clientId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію (string)
CLIENT_CONNECTED,                   -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Клієнта (string)
]]
```

###### Подія «Клієнт від'єднався від брокера» запустила Сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.clientId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію (string)
CLIENT_DISCONNECTED,                -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Клієнта (string)
]]
```

###### Подія «Помилка Сценарію» запустила Сценарій

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
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію (string)
SCRIPT_ERROR,                       -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію, в якому виникла помилка (string)
1,                                  -- поточна кількість помилок (number)
10,                                 -- максимально допустима кількість помилок (number)
reason,                             -- причина помилки (string)
stack                               -- стек помилки (string)
]]
```

###### Редактор Lua запустив Сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію (string)
DEV                                 -- Джерело запуску (string). Значення можна змінити в редакторі Lua.
]]
```

###### Lua-бібліотека запустила Сценарій (на прикладі Таймера)

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.luaLib)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id Сценарію (string)
LUA_LIB,                            -- Джерело запуску (string)
timer,                              -- Id Lua-бібліотеки (або Id Плагіна) (string)
]]
```

---



<h3 class="pt-5" id="dashboard">Dashboard</h3>

Сервіс `Dashboard` використовується для отримання вхідних повідомлень від JS Панелі та надсилання повідомлень у зворотному напрямку.

Отримувати та надсилати повідомлення можна лише в тих Сценаріях, які були запущені з Панелі.

Для надсилання повідомлень відкритим Панелям без запуску Сценарію з Панелі, можна використовувати модуль [open_dashboard](#open_dashboard).

##### Приклад

```lua
-- повертає true, якщо доступне вхідне повідомлення від Панелі
local flag = Dashboard.isIncomingMessageAvailable()

-- повертає об'єкт вхідного повідомлення (table)
-- це той самий об'єкт, який було передано через:
-- dashboard.execute({ key : "value" })
-- якщо повідомлення відсутнє — повертає nil
local incomingMessage = Dashboard.getIncomingMessage()

-- повертає ідентифікатор відкритої Панелі (string)
-- кожна відкрита Панель має власний унікальний ідентифікатор
-- якщо Панель недоступна — повертає nil
local id = Dashboard.getOpenDashboardId()

-- надсилання повідомлення до Панелі
-- якщо Панель вже закрита або недоступна, повідомлення буде проігноровано
local outgoingMessage = {
    key = "Pi",
    value = 3.14
}

Dashboard.sendMessage(outgoingMessage)
```

---




<h3 class="pt-5" id="std">Std</h3>

Сервіс `Std` використовується для переспрямування потоків **STDOUT** і **STDERR**.

##### Приклад

```lua
-- захоплює потік STDOUT і викликає функцію зворотного виклику,
-- якій передається один рядок тексту (UTF-8)
Std.captureRowOut(function(row)

end)

-- захоплює потік STDERR і викликає функцію зворотного виклику,
-- якій передається один рядок тексту (UTF-8)
Std.captureRowErr(function(row)

end)

-- захоплює потік STDOUT і викликає функцію зворотного виклику,
-- якій передається один байт (number -128...127)
Std.captureByteOut(function(value)

end)

-- захоплює потік STDERR і викликає функцію зворотного виклику,
-- якій передається один байт (number -128...127)
Std.captureByteErr(function(value)

end)

-- відновлює стандартний потік STDOUT (java.lang.System.out)
Std.restoreOut()

-- відновлює стандартний потік STDERR (java.lang.System.err)
Std.restoreErr()



-- приклад захоплення потоку STDOUT у буфер
local buffer = {}

Std.captureRowOut(function(row)
    table.insert(buffer, row)
end)

os.execute("ls -l")

Std.restoreOut()

Output.print(table.concat(buffer, "\n"))



-- приклад захоплення потоку STDOUT у ByteArray
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

Сервіс `Output` використовується для виведення повідомлень у вивід **Dev**.

Якщо Сценарій запущено не в режимі **Dev**, виклики `Output.print()` ігноруються.

##### Приклад

```lua

Output.print("message_1", "message_2", "message_n")

```

---



<h3 class="pt-5" id="log">Log</h3>

Сервіс `Log` використовується для запису повідомлень у журнал. 

##### Приклад

```lua

-- запис повідомлення з рівнем debug
Log.debug("debug")
Log.debug({Pi = 3.14})
Log.debug("string", {Pi = 3.14})

-- запис повідомлення з рівнем info
Log.info("info")
Log.info({Pi = 3.14})
Log.info("string", {Pi = 3.14})

-- запис повідомлення з рівнем warn
Log.warn("warning")
Log.warn({Pi = 3.14})
Log.warn("string", {Pi = 3.14})

-- запис повідомлення з рівнем error
Log.error("error")
Log.error({Pi = 3.14})
Log.error("string", {Pi = 3.14})

-- повертає true, якщо увімкнено рівень журналювання debug
local flag = Log.isDebugEnabled()

-- дорогу операцію підготовки повідомлення варто виконувати лише,
-- якщо рівень debug увімкнено
if Log.isDebugEnabled() then
    Log.debug(prepareReallyLongDebugMessage())
end

```


---



<h3 id="client" class="pt-5">Client</h3>

Сервіс `Client` використовується для доступу до MQTT Клієнта.

Дозволяє отримувати вхідні повідомлення та надсилати вихідні. Дані повідомлень представлені екземплярами класу `ByteArray`.

Отримувати вхідні повідомлення та надсилати вихідні можна лише в тих Сценаріях, які були запущені Підпискою.

Якщо Сценарій був запущений іншим способом, необхідно викликати функцію `setup(clientId)` і вказати ідентифікатор Клієнта, від імені якого виконуватиметься надсилання повідомлень.

##### Приклад

```lua
-- повертає true, якщо доступне вхідне повідомлення
local flag = Client.isIncomingMessageAvailable()

-- повертає вхідне повідомлення (table)
-- якщо повідомлення відсутнє — повертає nil
local incomingMessage = Client.getIncomingMessage()

-- MQTT-топік (string)
local topic = incomingMessage.topic

-- MQTT Topic Filter, за яким отримано повідомлення (string)
local topicFilter = incomingMessage.topicFilter

-- рівень QoS (number)
local qos = incomingMessage.qos

-- отримання payload
local payload = incomingMessage.payload
local jsonStr = payload:getString(0)

-- вибір Клієнта, який використовуватиметься для надсилання повідомлень
-- необхідно вказати Id Клієнта
-- якщо Сценарій запущено Підпискою, викликати setup() не потрібно
Client.setup("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")

-- повертає Id поточного Клієнта
-- якщо Клієнт не вибраний — повертає nil
Output.print(Client.clientId)

-- надсилання повідомлення
local outgoingMessage = {
    topic = "project/super_topic",
    qos = 0,
    payload = ByteArray.fromString("{\"key\":\"value\"}")
}

-- повертає true, якщо повідомлення успішно надіслано
local ok = Client.sendMessage(outgoingMessage)
```

---



### Додаткові класи




<h3 id="byte_array" class="pt-5">ByteArray</h3>

Клас `ByteArray` використовується для роботи з даними (Payload) у сервісі `Client`. Це контейнер для Java `byte[]`, який надає зручні функції для читання, запису та перетворення бінарних даних.

> **Примітка:** параметр `position` — це індекс масиву, який починається з `0`, а не з `1`.

### Функціонал  

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



##### Статичні функції



<span id="ba-allocate"></span>

- `allocate(length)` — створює новий екземпляр `ByteArray`.

```lua
local data = ByteArray.allocate(64)
```

Виділяє пам'ять розміром `64` байти та повертає новий екземпляр `ByteArray`.

---



<span id="ba-fromString"></span>

- `fromString(string)` — створює `ByteArray` з рядка UTF-8.

```lua
local data = ByteArray.fromString("{\"key\":\"value\"}")
```

Перетворює рядок у масив байтів UTF-8 та повертає екземпляр `ByteArray`.

---



<span id="ba-fromHexString"></span>

- `fromHexString(hexString)` — створює `ByteArray` із HEX-рядка.

```lua
local data = ByteArray.fromHexString("48656C6C6F20576F726C64")
```

Перетворює HEX-рядок у масив байтів та повертає екземпляр `ByteArray`.

---



<span id="ba-fromBase64String"></span>

- `fromBase64String(base64String)` — створює `ByteArray` із Base64-рядка.

```lua
local data = ByteArray.fromBase64String("aGVsbG8gd29ybGQ=")
```

Перетворює Base64-рядок у масив байтів та повертає екземпляр `ByteArray`.

---



<span id="ba-fromJBytes"></span>

- `fromJBytes(userdata)` — створює `ByteArray` з Java `byte[]`.

```lua
local data = ByteArray.fromJBytes(anotherByteArray:getJBytes())
```

Створює екземпляр `ByteArray` з Java-масиву `byte[]`.

---



##### Методи екземпляра

_У прикладах нижче `data` — це екземпляр класу `ByteArray`._



<span id="ba-split"></span>

- `split(position, length, chunkSize)` — створює зріз даних, розбиває його на частини однакового розміру та повертає масив (`table`) екземплярів `ByteArray`.

```lua
local array = data:split(2, 16, 4)
```

Створює зріз від позиції `2` довжиною `16` байтів і розбиває його на блоки по `4` байти.

```
[0x00, 0x01,
0x02, 0x03, 0x04, 0x05,
0x06, 0x07, 0x08, 0x09,
0x0A, 0x0B, 0x0C, 0x0D,
0x0E, 0x0F, 0x10, 0x11,
0x12, 0x13, 0x14, 0x15, 0x16]
```

У результаті буде повернено масив із `4` екземплярів `ByteArray`.
```
[0x02, 0x03, 0x04, 0x05]
[0x06, 0x07, 0x08, 0x09]
[0x0A, 0x0B, 0x0C, 0x0D]
[0x0E, 0x0F, 0x10, 0x11]
```

---



<span id="ba-length"></span>

- `length()` — повертає розмір внутрішнього масиву байтів.

```lua
local length = data:length()
```

Повертає значення типу `number`.

---



<span id="ba-payloadLength"></span>

- `payloadLength()` — Повертає кількість байтів до останнього ненульового байта. Кінцеві байти зі значенням `0x00` не враховуються.

##### Приклад

Масив містить `8` байтів (`0xFF, 0xFF, 0xFF, 0x00, 0xAA, 0x00, 0x00, 0x00`),
але зайнятою вважається лише його перша частина — `5` байтів (`0xFF, 0xFF, 0xFF, 0x00, 0xAA`).

```lua
local payloadLength = data:payloadLength()
```

Повертає значення типу `number`.

---



<span id="ba-getHexString"></span>

- `getHexString()` — повертає дані у вигляді HEX-рядка.

```lua
local str = data:getHexString()
```

Повертає HEX-рядок.

---



<span id="ba-toBase64String"></span>

- `toBase64String()` — повертає дані у вигляді Base64-рядка.

```lua
local str = data:toBase64String()
```

Повертає Base64-рядок.

---



<span id="ba-getJBytes"></span>

- `getJBytes()` — повертає внутрішній Java-масив `byte[]`.

```lua
local bytes = data:getJBytes()
```

Повертає `userdata`, що містить Java `byte[]`.

---



<span id="ba-put"></span>

- `put(anotherByteArray, position)` — копіює дані іншого `ByteArray` у поточний.

```lua
data:put(data2, 8)
```

Запис починається з позиції `8`.

---



<span id="ba-merge"></span>

- `merge(anotherByteArray, position)` — об'єднує поточний `ByteArray` з іншим.

```lua
local newData = data:merge(data2, 8)
```

Об'єднання починається з позиції `8`. Повертає новий екземпляр `ByteArray`.

---



<span id="ba-copy"></span>

- `copy(position, length)` — створює копію частини даних.

```lua
local newData = data:copy(2, 4)
```

Створює копію довжиною `4` байти, починаючи з позиції `2`, та повертає новий екземпляр `ByteArray`.

---



<span id="ba-getInt8"></span>

- `getInt8(position)` — повертає значення типу `int8`.

```lua
local value = data:getInt8(0)
```

Зчитує `1` байт із вказаної позиції, перетворює його на `int8` та повертає значення типу `number`.

---



<span id="ba-getUint8"></span>

- `getUint8(position)` — повертає значення типу `uint8`.

```lua
local value = data:getUint8(0)
```

Зчитує `1` байт із вказаної позиції, перетворює його на `uint8` та повертає значення типу `number`.

---



<span id="ba-getInt16Be"></span>

- `getInt16Be(position)` — повертає значення типу `int16` у форматі **Big-endian**.

```lua
local value = data:getInt16Be(0)
```

Зчитує `2` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getInt16Le"></span>

- `getInt16Le(position)` — повертає значення типу `int16` у форматі **Little-endian**.

```lua
local value = data:getInt16Le(0)
```

Зчитує `2` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getUint16Be"></span>

- `getUint16Be(position)` — повертає значення типу `uint16` у форматі **Big-endian**.

```lua
local value = data:getUint16Be(0)
```

Зчитує `2` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getUint16Le"></span>

- `getUint16Le(position)` — повертає значення типу `uint16` у форматі **Little-endian**.

```lua
local value = data:getUint16Le(0)
```

Зчитує `2` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getInt32Be"></span>

- `getInt32Be(position)` — повертає значення типу `int32` у форматі **Big-endian**.

```lua
local value = data:getInt32Be(0)
```

Зчитує `4` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getInt32Le"></span>

- `getInt32Le(position)` — повертає значення типу `int32` у форматі **Little-endian**.

```lua
local value = data:getInt32Le(0)
```

Зчитує `4` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getUint32Be"></span>

- `getUint32Be(position)` — повертає значення типу `uint32` у форматі **Big-endian**.

```lua
local value = data:getUint32Be(0)
```

Зчитує `4` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getUint32Le"></span>

- `getUint32Le(position)` — повертає значення типу `uint32` у форматі **Little-endian**.

```lua
local value = data:getUint32Le(0)
```

Зчитує `4` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getInt64Be"></span>

- `getInt64Be(position)` — повертає значення типу `int64` у форматі **Big Endian**.

```lua
local value = data:getInt64Be(0)
```

Зчитує `8` байтів із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getInt64Le"></span>

- `getInt64Le(position)` — повертає значення типу `int64` у форматі **Little Endian**.

```lua
local value = data:getInt64Le(0)
```

Зчитує `8` байтів із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getFloatBe"></span>

- `getFloatBe(position)` — повертає значення типу `float` у форматі **Big Endian**.

```lua
local value = data:getFloatBe(0)
```

Зчитує `4` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getFloatLe"></span>

- `getFloatLe(position)` — повертає значення типу `float` у форматі **Little Endian**.

```lua
local value = data:getFloatLe(0)
```

Зчитує `4` байти із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getDoubleBe"></span>

- `getDoubleBe(position)` — повертає значення типу `double` у форматі **Big Endian**.

```lua
local value = data:getDoubleBe(0)
```

Зчитує `8` байтів із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getDoubleLe"></span>

- `getDoubleLe(position)` — повертає значення типу `double` у форматі **Little Endian**.

```lua
local value = data:getDoubleLe(0)
```

Зчитує `8` байтів із вказаної позиції та повертає значення типу `number`.

---



<span id="ba-getUint8Sequence"></span>

- `getUint8Sequence(position)` — повертає послідовність байтів у вигляді масиву чисел від `-0` до `255`.

```lua
local table = data:getUint8Sequence(0)
```

Зчитує байти із вказаної позиції та повертає масив (`table`).

---



<span id="ba-getInt8Sequence"></span>

- `getInt8Sequence(position)` — повертає послідовність байтів у вигляді масиву чисел від `-128` до `127`.

```lua
local table = data:getInt8Sequence(0)
```

Зчитує байти із вказаної позиції та повертає масив (`table`).

---



<span id="ba-getString"></span>

- `getString(position, [length])` — повертає рядок.

```lua
local str = data:getString(0, 10)
-- або
local str = data:getString(0)
```

Зчитує дані, починаючи із вказаної позиції, та перетворює `length` байтів або всі доступні байти (якщо `length` не вказано) у рядок у кодуванні UTF-8.

---



<span id="ba-getSafeString"></span>

- `getSafeString(position, [length])` — повертає рядок.

```lua
local str = data:getSafeString(0, 10)
-- або
local str = data:getSafeString(0)
```

Зчитує дані, починаючи із вказаної позиції, та перетворює `length` байтів або всі доступні байти (якщо `length` не вказано), **або до першого символу `\0`**, у рядок у кодуванні UTF-8.

---



<span id="ba-setInt8"></span>

- `setInt8(number, position)` — записує значення типу `int8`.

```lua
data:setInt8(-127, 0)
```

Перетворює `number` у значення типу `int8` і записує його на вказану позицію.

---



<span id="ba-setUint8"></span>

- `setUint8(number, position)` — записує значення типу `uint8`.

```lua
data:setUint8(255, 0)
```

Перетворює `number` у значення типу `uint8` і записує його на вказану позицію.

---



<span id="ba-setInt16Be"></span>

- `setInt16Be(number, position)` — записує значення типу `int16` у форматі **Big Endian**.

```lua
data:setInt16Be(-32768, 0)
```

Перетворює `number` у значення типу `int16` і записує його на вказану позицію.

---



<span id="ba-setInt16Le"></span>

- `setInt16Le(number, position)` — записує значення типу `int16` у форматі **Little Endian**.

```lua
data:setInt16Le(-32768, 0)
```

Перетворює `number` у значення типу `int16` і записує його на вказану позицію.

---



<span id="ba-setUint16Be"></span>

- `setUint16Be(number, position)` — записує значення типу `uint16` у форматі **Big Endian**.

```lua
data:setUint16Be(65535, 0)
```

Перетворює `number` у значення типу `uint16` і записує його на вказану позицію.

---



<span id="ba-setUint16Le"></span>

- `setUint16Le(number, position)` — записує значення типу `uint16` у форматі **Little Endian**.

```lua
data:setUint16Le(65535, 0)
```

Перетворює `number` у значення типу `uint16` і записує його на вказану позицію.

---



<span id="ba-setInt32Be"></span>

- `setInt32Be(number, position)` — записує значення типу `int32` у форматі **Big Endian**.

```lua
data:setInt32Be(-2147483648, 0)
```

Перетворює `number` у значення типу `int32` і записує його на вказану позицію.

---



<span id="ba-setInt32Le"></span>

- `setInt32Le(number, position)` — записує значення типу `int32` у форматі **Little Endian**.

```lua
data:setInt32Le(-2147483648, 0)
```

Перетворює `number` у значення типу `int32` і записує його на вказану позицію.

---



<span id="ba-setUint32Be"></span>

- `setUint32Be(number, position)` — записує значення типу `uint32` у форматі **Big Endian**.

```lua
data:setUint32Be(4294967295, 0)
```

Перетворює `number` у значення типу `uint32` і записує його на вказану позицію.

---



<span id="ba-setUint32Le"></span>

- `setUint32Le(number, position)` — записує значення типу `uint32` у форматі **Little Endian**.

```lua
data:setUint32Le(4294967295, 0)
```

Перетворює `number` у значення типу `uint32` і записує його на вказану позицію.

---



<span id="ba-setInt64Be"></span>

- `setInt64Be(number, position)` — записує значення типу `int64` у форматі **Big Endian**.

```lua
data:setInt64Be(-1000000000000, 0)
```

Перетворює `number` у значення типу `int64` і записує його на вказану позицію.

---



<span id="ba-setInt64Le"></span>

- `setInt64Le(number, position)` — записує значення типу `int64` у форматі **Little Endian**.

```lua
data:setInt64Le(-1000000000000, 0)
```

Перетворює `number` у значення типу `int64` і записує його на вказану позицію.

---



<span id="ba-setFloatBe"></span>

- `setFloatBe(number, position)` — записує значення типу `float` у форматі **Big Endian**.

```lua
data:setFloatBe(3.1415, 0)
```

Перетворює `number` у значення типу `float` і записує його на вказану позицію.

---



<span id="ba-setFloatLe"></span>

- `setFloatLe(number, position)` — записує значення типу `float` у форматі **Little Endian**.

```lua
data:setFloatLe(3.1415, 0)
```

Перетворює `number` у значення типу `float` і записує його на вказану позицію.

---



<span id="ba-setDoubleBe"></span>

- `setDoubleBe(number, position)` — записує значення типу `double` у форматі **Big Endian**.

```lua
data:setDoubleBe(3.1415, 0)
```

Перетворює `number` у значення типу `double` і записує його на вказану позицію.

---



<span id="ba-setDoubleLe"></span>

- `setDoubleLe(number, position)` — записує значення типу `double` у форматі **Little Endian**.

```lua
data:setDoubleLe(3.1415, 0)
```

Перетворює `number` у значення типу `double` і записує його у форматі **Little Endian** на вказану позицію.

---



<span id="ba-setUint8Sequence"></span>

- `setUint8Sequence(table, position)` — записує послідовність байтів.

```lua
data:setUint8Sequence({0x01, 0x02, 0x03, 0x04}, 0)
```

Перетворює `table`, що містить числа в діапазоні від `0` до `255`, у `byte[]` та записує його, починаючи із вказаної позиції.

---



<span id="ba-setInt8Sequence"></span>

- `setInt8Sequence(table, position)` — записує послідовність байтів.

```lua
data:setInt8Sequence({0x01, 0x02, 0x03, 0x04}, 0) 
```

Перетворює `table`, що містить числа в діапазоні від `-128` до `127`, у `byte[]` та записує його, починаючи із вказаної позиції.

---



<span id="ba-setString"></span>

- `setString(string, position)` — записує рядок.

```lua
data:setString("hello world", 0)
```

Перетворює `string` у `byte[]` у кодуванні UTF-8 та записує його, починаючи із вказаної позиції.

---



##### Приклад

Отримання даних із C-структури. Структура отримується з ESP32 у вигляді масиву байтів.

```lua
--[[ Опис структури
struct __attribute__((packed)) struct_1_t {
    uint8_t a;     // 1 байт
    uint32_t b;    // 4 байти
    int16_t c[4];  // 8 байтів
};
]]

local struct1 = incomingMessage.payload

local a = struct1:getUint8(0)
local b = struct1:getUint32Le(1)

-- Починаючи з позиції 5:
-- довжина — 8 байтів;
-- розмір одного елемента — 2 байти (int16_t).
local arrayC = struct1:split(5, 8, 2)

local c1 = arrayC[1]:getInt16Le(0)
local c2 = arrayC[2]:getInt16Le(0)
local c3 = arrayC[3]:getInt16Le(0)
local c4 = arrayC[4]:getInt16Le(0)

Output.print(a, b, c1, c2, c3, c4)
```

Відправлення даних у вигляді C-структури. Структура передається до ESP32 як масив байтів.

```lua
--[[ Опис структури
struct __attribute__((packed)) struct_2_t {
    int8_t a;       // 1 байт
    int64_t b;      // 8 байтів
    uint16_t c[4];  // 8 байтів
    char d[10];     // 10 байтів
};  // Загальний розмір — 27 байтів
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

-- останній байт буде '\0'
struct2:setString("hello 123", 17)

-- send struct2
```

---




<h3 class="pt-5" id="open_dashboard">open_dashboard</h3>

Модуль `open_dashboard` використовується для отримання інформації про відкриті Панелі (тобто Панелі, які зараз відкриті в браузері) та надсилання повідомлення.

##### Приклад
```lua
local OpenDashboard = require("open_dashboard")

-- отримання всіх відкритих Панелей Проєкту (table, array);
-- необхідно передати id Проєкту
local openDashboards = OpenDashboard.byProjectId(Args.projectId)

-- отримання всіх відкритих екземплярів Панелі (table, array);
-- необхідно передати id Панелі
openDashboards = OpenDashboard.byDashboardId("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")

-- надсилання повідомлення всім відкритим Панелям
for i = 1, #openDashboards do
    local openDashboard = openDashboards[i]

    -- openDashboard.name         -- назва Панелі
    -- openDashboard.id           -- id відкритої Панелі (кожен відкритий екземпляр має власний унікальний id)
    -- openDashboard.projectId    -- id Проєкту
    -- openDashboard.dashboardId  -- id Панелі

    openDashboard:sendMessage({key = "value"})
end
```

---



<h3 class="pt-5" id="client_info">client_info</h3>

Модуль `client_info` використовується для отримання інформації про Клієнтів (id, id Проєкту, назва, URI та статус підключення).

Надсилати повідомлення можна тільки з інжектованого сервісу [Client](/docs/lua_modules#client).

##### Приклад
```lua
local ClientInfo = require("client_info")

-- отримання всіх Клієнтів Проєкту (table, array);
-- необхідно передати id Проєкту
local clients = ClientInfo.byProjectId(Args.projectId)

for i = 1, #clients do
    local client = clients[i]

    -- client.id           -- id Клієнта
    -- client.name         -- назва Клієнта
    -- client.projectId    -- id Проєкту
    -- client.uri          -- URI Клієнта
    -- client.connected    -- статус підключення
end

-- отримання Клієнта за його id (table)
local client = ClientInfo.byClientId("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")
```

---




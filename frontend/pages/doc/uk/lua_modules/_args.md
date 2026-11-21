<h1 id="args" class="pt-5">Args</h1>

Сервіс `Args` використовується для отримання аргументів, переданих у сценарій.
Кожен сценарій отримує базовий набір аргументів: ідентифікатор проєкту, ідентифікатор сценарію, джерело запуску та додаткові дані.

Передати персональні аргументи можна в таких випадках:
- запуск сценарію модулем [timer](/doc/lua_modules#timer);
- запуск сценарію з плагіну.

## Приклад

```lua
Args.projectId    -- ідентифікатор проєкту (string)
Args.scriptId     -- ідентифікатор сценарію (string)
Args.source       -- джерело запуску сценарію (string)
```

### Панель запустила сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.dashboardId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію (string)
DASHBOARD,                          -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Id панелі (string)
]]
```

### Розклад запустив сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.scheduleId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію (string)
SCHEDULE,                           -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Id розкладу (string)
]]
```

### Підписка запустила сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.subscriptionId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію (string)
SUBSCRIPTION,                       -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx  -- Id підписки (string)
]]
```

### Автостарт запустив сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію (string)
AUTOSTART                           -- Джерело запуску (string)
]]
```

### Подія «Клієнт під'єднався до брокера» запустила сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.clientId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію (string)
CLIENT_CONNECTED,                   -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id клієнта (string)
]]
```

### Подія «Клієнт від'єднався від брокера» запустила сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.clientId)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію (string)
CLIENT_DISCONNECTED,                -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id клієнта (string)
]]
```

### Подія «Помилка сценарію» запустила сценарій

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
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію (string)
SCRIPT_ERROR,                       -- Джерело запуску (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію, в якому виникла помилка (string)
1,                                  -- поточна кількість помилок (number)
10,                                 -- максимально допустима кількість помилок (number)
reason,                             -- причина помилки (string)
stack                               -- стек помилки (string)
]]
```

### Редактор Lua запустив сценарій

```lua
Output.print(Args.projectId, Args.scriptId, Args.source)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію (string)
DEV                                 -- Джерело запуску (string). Значення можна змінити в імітаційних даних (JSON).
]]
```

### Lua-бібліотека запустила сценарій (на прикладі Таймера)

```lua
Output.print(Args.projectId, Args.scriptId, Args.source, Args.luaLib)
--[[
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id проєкту (string)
xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx, -- Id сценарію (string)
LUA_LIB,                            -- Джерело запуску (string)
timer,                              -- Id Lua-бібліотеки (або Id Плагіна) (string)
]]
```

---
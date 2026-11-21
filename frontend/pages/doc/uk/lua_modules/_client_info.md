<h1 class="pt-5" id="client_info">client_info</h1>

Модуль `client_info` використовується для отримання інформації про клієнтів (id, id проєкту, назва, URI та статус підключення).

Надсилати повідомлення можна тільки з інжектованого сервісу [Client](/doc/lua_modules#client).

## Приклад
```lua
local ClientInfo = require("client_info")

-- отримання всіх клієнтів проєкту (table, array);
-- необхідно передати id проєкту
local clients = ClientInfo.byProjectId(Args.projectId)

for i = 1, #clients do
    local client = clients[i]

    -- client.id           -- id клієнта
    -- client.name         -- назва клієнта
    -- client.projectId    -- id проєкту
    -- client.uri          -- URI клієнта
    -- client.connected    -- статус підключення
end

-- отримання клієнта за його id (table)
local client = ClientInfo.byClientId("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")
```

---
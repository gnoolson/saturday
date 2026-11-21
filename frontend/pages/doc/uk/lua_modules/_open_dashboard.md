<h1 class="pt-5" id="open_dashboard">open_dashboard</h1>

Модуль `open_dashboard` використовується для отримання інформації про відкриті панелі (тобто панелі, які зараз відкриті в браузері) та надсилання повідомлення.

## Приклад
```lua
local OpenDashboard = require("open_dashboard")

-- отримання всіх відкритих панелей проєкту (table, array);
-- необхідно передати id проєкту
local openDashboards = OpenDashboard.byProjectId(Args.projectId)

-- отримання всіх відкритих екземплярів панелі (table, array);
-- необхідно передати id панелі
openDashboards = OpenDashboard.byDashboardId("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")

-- надсилання повідомлення всім відкритим панелям
for i = 1, #openDashboards do
    local openDashboard = openDashboards[i]

    -- openDashboard.name         -- назва панелі
    -- openDashboard.id           -- id відкритої панелі (кожен відкритий екземпляр має власний унікальний id)
    -- openDashboard.projectId    -- id Проєкту
    -- openDashboard.dashboardId  -- id Панелі

    openDashboard:sendMessage({key = "value"})
end
```

---
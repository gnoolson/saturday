<h1 id="client" class="pt-5">Client</h1>

Сервіс `Client` використовується для доступу до MQTT клієнта.

Дозволяє отримувати вхідні повідомлення та надсилати вихідні. Дані повідомлень представлені екземплярами класу `ByteArray`.

Отримувати вхідні повідомлення та надсилати вихідні можна лише в тих сценаріях, які були запущені підпискою.

Якщо сценарій був запущений іншим способом, необхідно викликати функцію `setup(clientId)` і вказати ідентифікатор клієнта, від імені якого виконуватиметься надсилання повідомлень.

## Приклад

```lua
-- повертає true, якщо доступне вхідне повідомлення
local flag = Client.isIncomingMessageAvailable()

-- повертає вхідне повідомлення (table)
-- якщо повідомлення відсутнє - повертає nil
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

-- вибір клієнта, який використовуватиметься для надсилання повідомлень
-- необхідно вказати Id клієнта
-- якщо сценарій запущено підпискою, викликати setup() не потрібно
Client.setup("xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx")

-- повертає Id поточного клієнта
-- якщо клієнт не вибраний - повертає nil
Output.print(Client.clientId)

-- надсилання повідомлення
local outgoingMessage = {
    topic = "project/super_topic",
    qos = 0,
    payload = ByteArray.fromString("{\"key\":\"value\"}"),
    retained = false -- Можна не вказувати. Значення false за замовчуванням.
}

-- повертає true, якщо ввімкнений клієнт отримав це повідомлення.
local ok = Client.sendMessage(outgoingMessage)

```

---
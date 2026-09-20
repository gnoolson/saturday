# Saturday

Платформа для IoT: виконуйте власну бізнес-логіку та керуйте пристроями за допомогою простих Lua-сценаріїв.

**Saturday** — це готове середовище для автоматизації, обробки телеметрії та координації MQTT-пристроїв.

Замість ускладнення прошивок апаратного забезпечення, Saturday дозволяє винести всю загальну бізнес-логіку в окремий керований шар. Ви описуєте потрібну поведінку системи у вигляді легких Lua-скриптів, які можна змінювати в реальному часі — без перекомпіляції та перезапуску додатка.

**Як це працює?**

Saturday підключається до MQTT-брокера як клієнт, підписується на необхідні топіки та миттєво обробляє вхідні повідомлення за заданими Lua-сценаріями.


**Основні можливості**

- Виконання бізнес-логіки в реальному часі: Миттєва обробка та маршрутизація MQTT-повідомлень.
- Власні HTML/CSS/JS Панелі: Створення власних веб-інтерфейсів для моніторингу та керування з можливістю передачі параметрів у сценарії.
- Гнучкі тригери та розклад: Запуск скриптів за Cron-розкладом, подіями MQTT, з панелі чи при виникненні помилок.
- Розширювана архітектура: Можливість додавати новий функціонал через систему плагінів.

Зі сценаріїв Lua ви маєте вбудований доступ до:

- Баз даних та кешування (для збереження стану й телеметрії);
- Telegram API (для сповіщень та інтерактивних команд);
- Роботи з Serial-портом та операційною системою;
- Взаємодії з плагінами та іншими сценаріями.

**Тригери запуску сценаріїв**

- MQTT: Отримання повідомлення за підпискою.
- Панель: Дія користувача у веб-інтерфейсі.
- Розклад: Автоматичний запуск (Cron).
- Плагіни: Внутрішні та зовнішні події.
- Мережа: Підключення чи відключення MQTT-клієнтів.
- Dev Mode: Ручний запуск для налагодження.
- Обробка помилок: Реакція на збої в інших сценаріях.

**Для чого підходить?**

Побудова систем автоматизації, IoT-інфраструктури, телеметрії, диспетчеризації обладнання та гнучких MQTT-інтеграцій.


---


[Проєкт](/docs/project)
- [Опис](/docs/project#main)
- [Поля](/docs/project#fields)

[Сценарій](/docs/script)
- [Опис](/docs/script#main)
- [Запуск](/docs/script#start)
- [Життевий цикл](/docs/script#lifecycle)
- [Інтерпретатор](/docs/script#interpreter)
- [Паралельна робота](/docs/script#parallel_work)
- [Помилки](/docs/script#error)
- [Функція require()](/docs/script#require)
- [Функція print()](/docs/script#print)
- [Журнал](/docs/script#log)
- [Усунення несправностей](/docs/script#troubleshooting)
- [Поля](/docs/script#fields)

[Коротко про Lua](/docs/lua)

[Інжектовані сервіси, класи, модулі (Lua)](/docs/lua_modules)
- [Args](/docs/lua_modules#args)
- [Dashboard](/docs/lua_modules#dashboard)
- [Std](/docs/lua_modules#std)
- [Output](/docs/lua_modules#output)
- [Log](/docs/lua_modules#log)
- [Client](/docs/lua_modules#client)
- [ByteArray](/docs/lua_modules#byte_array)
- [open_dashboard](/docs/lua_modules#open_dashboard)
- [client_info](/docs/lua_modules#client_info)
- [db*](/plugin/db/doc)
- [http_client*](/plugin/http_client/doc)
- [cache*](/plugin/cache/doc)
- [json*](/plugin/json/doc)
- [now*](/plugin/now/doc)
- [tg*](/plugin/tg/doc)
- [timer*](/plugin/timer/doc)
- [locker*](/plugin/locker/doc)
- [serial*](/plugin/serial/doc)

[Панель](/docs/dashboard)
- [Опис](/docs/dashboard#main)
- [dashboard.js](/docs/dashboard#dashboard_js)
- [Поля](/docs/dashboard#fields)

[Клієнт](/docs/client)
- [Опис](/docs/client#main)
- [Поля](/docs/client#fields)

[Підписка](/docs/subscription)
- [Опис](/docs/subscription#main)
- [Поля](/docs/subscription#fields)

[Розклад](/docs/schedule)
- [Опис](/docs/schedule#main)
- [Поля](/docs/schedule#fields)

[Плагін](/docs/plugin)


---

\* - Плагін
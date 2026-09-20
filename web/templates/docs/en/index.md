# Saturday

An IoT platform for running your custom business logic and managing devices via lightweight Lua scripts.

**Saturday** is a turn-key environment for automation, telemetry processing, and MQTT device coordination.

Instead of overcomplicating hardware firmware, Saturday lets you offload overall business logic into a separate, manageable execution layer. You define system behavior using lightweight Lua scripts that can be updated on the fly — without recompiling or restarting the application.

**How it works**

Saturday connects to an MQTT broker as a client, subscribes to required topics, and instantly processes incoming messages based on user-defined Lua scripts.


**Core Features**

- Real-time business logic execution: Instant processing and routing of MQTT messages.
- Custom HTML/CSS/JS Dashboards: Build tailored web interfaces for monitoring and control, complete with parameterized script triggers.
- Flexible triggers & scheduling: Run scripts via Cron schedules, MQTT events, dashboard interactions, or error handlers.
- Extensible architecture: Easily expand functionality through a plugin system.

Directly from Lua scripts, you get built-in access to:

-    Databases and caching (for state management and telemetry storage);
-    Telegram API (for alerts and interactive commands);
-    Hardware Serial ports and OS-level operations;
-    Interactions with plugins and other scripts.

**Event Triggers**

- MQTT: Incoming messages on subscribed topics.
- Dashboard: User actions within the web interface.
- Schedule: Automated Cron-based execution.
- Plugins: Internal and external plugin events.
- Network: Client connection and disconnection events.
- Dev Mode: Manual execution for debugging.
- Error Handling: Reactive execution when errors occur in other scripts.

**Use Cases**

Building automation systems, IoT infrastructure, telemetry pipelines, hardware dispatch systems, and flexible MQTT integrations.

---


[Project](/docs/project)
- [Overview](/docs/project#main)
- [Fields](/docs/project#fields)

[Script](/docs/script)
- [Overview](/docs/script#main)
- [Execution](/docs/script#start)
- [Lifecycle](/docs/script#lifecycle)
- [Interpreter](/docs/script#interpreter)
- [Parallel Execution](/docs/script#parallel_work)
- [Errors](/docs/script#error)
- [The require() Function](/docs/script#require)
- [The print() Function](/docs/script#print)
- [Log](/docs/script#log)
- [Troubleshooting](/docs/script#troubleshooting)
- [Fields](/docs/script#fields)

[Lua Quick Guide](/docs/lua)

[Injected Services, Classes, and Modules (Lua)](/docs/lua_modules)
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

[Dashboard](/docs/dashboard)
- [Overview](/docs/dashboard#main)
- [dashboard.js](/docs/dashboard#dashboard_js)
- [Fields](/docs/dashboard#fields)

[Client](/docs/client)
- [Overview](/docs/client#main)
- [Fields](/docs/client#fields)

[Subscription](/docs/subscription)
- [Overview](/docs/subscription#main)
- [Fields](/docs/subscription#fields)

[Schedule](/docs/schedule)
- [Overview](/docs/schedule#main)
- [Fields](/docs/schedule#fields)

[Plugin](/docs/plugin)


---
\* - Plugin
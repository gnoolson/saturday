[Documentation](/doc)

<h1 id="main">Script</h1>

Define your business logic in script code.

---


<h3 id="start">Execution</h3>

A script is triggered in response to the following events:
- receiving a message via a subscription;
- receiving a message from a dashboard;
- scheduled execution;
- execution triggered by a plugin;
- a client connecting or disconnecting;
- manual execution (**Dev** mode);
- an error occurring in another script.

---
<h3 id="lifecycle">Lifecycle</h3>

A separate Sandbox with its own isolated execution environment is created for each script.
Once the script finishes running, the Sandbox can either be discarded or cached for reuse.
Caching allows reusing an already initialized execution environment (improving performance) and preserving global variable values across script runs.

Scripts should execute quickly. 
If execution time exceeds the limit defined by the `gnoolson.saturday.script.lua.max_execution_time` parameter in the **Saturday configuration**, the script will be terminated automatically.

---

<h3 id="interpreter">Interpreter</h3>

**Saturday** uses the **Luaj** interpreter.  
**Luaj** has certain syntax quirks—for example, reserved keywords cannot be used as table field names.

By default, the standard `os` and `io` libraries are disabled. To enable them, set `gnoolson.saturday.script.lua.full_access` to `true` in the **Saturday configuration**.

---
<h3 id="parallel_work">Concurrency</h3>

Scripts with different IDs run in parallel.
Scripts with the same ID run sequentially.

Race conditions can occur when multiple scripts attempt to modify shared data simultaneously.
To prevent such issues, we recommend using the [locker](/doc/lua_modules#locker) module.


---
<h3 id="error">Errors</h3>

If unhandled errors occur during script execution, they increment the error counter.
If the error count exceeds the allowed limit, the script is automatically disabled and locked.
To unlock it, you must resolve all errors.

The maximum number of allowed errors is defined by the `gnoolson.saturday.script.max_errors` parameter in the **Saturday configuration**.

---
<h3 id="require">The require() function</h3>

The `require()` function searches for modules in the `./lualib` directory.
This path can be changed using the `gnoolson.saturday.script.lua.lib` parameter in the **Saturday configuration**.

---
<h3 id="print">The print() function</h3>

The standard `print()` function (as well as other standard Lua functions that write to **STDOUT**, such as `os.execute()`) outputs to `java.lang.System.out`.  
As a result, output from these calls goes directly to the **Saturday** application console and will not be visible in the UI.  
Using these functions without redirecting standard output is not recommended. See [Std](/doc/lua_modules#std) for details.  
To print text output in **Dev** mode, use [Log](/doc/lua_modules#log) or [Output](/doc/lua_modules#output) instead.  

---


<h3 id="log">Log</h3>

Each script has its own log where you can record messages at different logging levels.  
The directory for storing logs and their archives is defined by the `gnoolson.saturday.script.log_storage.path` parameter in the **Saturday configuration**.  
The **debug** level can be enabled on the script's log page. 
See [Log](/doc/lua_modules#log) for usage examples.

<h3 id="troubleshooting">Troubleshooting</h3>

If script execution fails with an error such as:
```
ClassCastException: org.luaj.vm2.Lua*** cannot be cast to gnoolson.saturday.***
```
you most likely called a method using the `.` operator instead of `:`.

Check the **Saturday** log (`application.log`). If needed, enable `debug` logging in `config/log4j2.xml`:

```xml
<Logger additivity="false" level="debug" name="gnoolson.saturday">
    <AppenderRef ref="stdout"/>
    <AppenderRef ref="file"/>
</Logger>
```

---
<h1 id="fields">Fields</h1>

<h3 id="project"># Project</h3>

The project to which this script belongs. Required field.


---
<h3 id="name"># Name</h3>

A unique script name within the project. Maximum 64 characters. Required field.


---


<h3 id="description"># Description</h3>

A text description of the script. Maximum 5000 characters. Optional field.


---


<h3 id="error-handler"># Error handler</h3>

The selected script will be executed if the main script terminates with an error.  
Error details can be accessed via `Args`.
The error handler is not triggered in **Dev** mode.


---

<h3 id="cache"># Cache duration</h3>

How long the Sandbox is retained in the cache.
Not used in **Dev** mode.

---

<h3 id="autostart"># Autostart</h3>

Automatic script execution after **Saturday** starts or when the script is enabled.

---

<h3 id="included-scripts"># Included scripts</h3>

Included scripts are executed (in the specified order) before the main script, and only once when caching is enabled.
This allows extracting shared logic into separate scripts for reuse (the main script can directly access data and functions defined in included scripts).

Only the code of included scripts is used; their other settings are ignored.

---

<h3 id="code"># Code</h3>

The Lua script code executed by the **Luaj** interpreter. Maximum 50,000 characters.

---

<h3 id="execute"># Run script (**Dev** mode)</h3>

In **Dev** mode, you can test your script by running it manually.  
Mock input data for `Args`, `Client`, and `Dashboard` is configured in JSON format.  
Calls to `Dashboard.sendMessage()` are displayed in **Output**.
Errors are also displayed in **Output**.

---
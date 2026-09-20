[Documentation](/docs)

<h1 id="main">Script</h1>

A Script is business logic written in Lua.

---



<h3 id="start">Execution</h3>

A Script can be executed in response to the following events:

- receiving a message from a Subscription;
- receiving a message from a Dashboard;
- execution by a Schedule;
- execution from a Plugin;
- a Client connecting or disconnecting;
- manual execution (**Dev**);
- an error occurring in another Script.

---



<h3 id="lifecycle">Lifecycle</h3>

A separate Sandbox with its own isolated execution environment is created for each Script.

After the Script finishes execution, the Sandbox may be discarded or cached for future reuse. Caching allows the prepared execution environment to be reused (improving performance) and preserves the values of global variables between Script executions.

Scripts are expected to execute quickly. Long-running loops are not allowed.

If the execution time exceeds the value of the `gnoolson.saturday.script.lua.max_execution_time` parameter in the **Saturday Configuration**, the Script is terminated automatically.

---



<h3 id="interpreter">Interpreter</h3>

Scripts are executed using **Luaj**.

Luaj has several syntax-specific behaviors. For example, reserved keywords cannot be used as table field names.

To enable access to the **JseIoLib** and **JseOsLib** libraries, set the `gnoolson.saturday.script.lua.full_access` parameter to `true` in the **Saturday Configuration**.

---



<h3 id="parallel_work">Parallel Execution</h3>

Scripts with different IDs are executed in parallel.

Scripts with the same ID are executed sequentially.

Race conditions may occur when multiple Scripts attempt to modify shared data simultaneously.

To prevent such situations, it is recommended to use the [locker](/docs/lua_modules#locker) module.

---



<h3 id="error">Errors</h3>

Unhandled errors that occur during Script execution increase the Script's error counter.

If the number of errors exceeds the configured limit, the Script is automatically disabled and blocked.

To unblock the Script, all errors must first be resolved.

The maximum allowed number of errors is defined by the `gnoolson.saturday.script.max_errors` parameter in the **Saturday Configuration**.

---



<h3 id="require">The require() Function</h3>

The `require()` function searches for Lua modules in the `./lualib` directory.

The search path can be changed using the `gnoolson.saturday.script.lua.lib` parameter in the **Saturday Configuration**.

---



<h3 id="print">print() Function</h3>

The standard `print()` function (as well as other standard Lua functions that may write to **STDOUT**, such as `os.execute()`) uses the standard output stream `java.lang.System.out`.  
As a result, calls to these functions print their output to the **Saturday** application console.  
It is not recommended to use these functions without redirecting the standard output stream. For details, see [Std](/docs/lua_modules#std).  
To output text in **Dev** mode, it is recommended to use [Log](/docs/lua_modules#log) or [Output](/docs/lua_modules#output).

---



<h3 id="log">Log</h3>

Each Script has its own log where messages can be written.

The directory used to store logs and archived log files is configured by the `gnoolson.saturday.script.log_storage.path` parameter in the **Saturday Configuration**.

See the [Log](/docs/lua_modules#log) example.

---



<h3 id="troubleshooting">Troubleshooting</h3>

If a Script throws an error similar to:

```text
ClassCastException: org.luaj.vm2.Lua*** cannot be cast to gnoolson.saturday.***
```

the most likely cause is that a method was called using the `.` operator instead of `:`.

Check the `application.log` file. If necessary, enable the `Debug` logging level in `config/log4j2.xml`.

```xml
<Logger name="gnoolson.saturday" level="debug" additivity="false">
    <AppenderRef ref="stdout"/>
    <AppenderRef ref="file"/>
</Logger>
```

---



<h1 id="fields">Fields</h1>




<h3 id="project">Project</h3>

The Project to which this Script belongs. This field is required.

---



<h3 id="name">Name</h3>

A unique Script name within the Project. Maximum length: **64** characters. This field is required.

---



<h3 id="description">Description</h3>

A text description of the Script. Maximum length: **5000** characters. This field is optional.

---



<h3 id="error-handler">Error Handler</h3>

The selected Script is executed if the main Script terminates with an error.

Information about the error is available through `Args`.

The error handler is not executed in **Dev** mode.

---



<h3 id="cache">Cache Duration</h3>

The amount of time the Sandbox is kept in the cache.

This setting is ignored in **Dev** mode.

---



<h3 id="autostart">Autostart</h3>

Automatically executes the Script after **Saturday** starts.

---




<h3 id="included-scripts">Included Scripts</h3>

The selected Scripts are executed in the specified order before the main Script and only once (when Sandbox caching is enabled).

This allows common logic to be moved into separate Scripts and reused across multiple Scripts.

The main Script can directly access the variables and functions defined in the included Scripts.

Only the code of the included Scripts is used; all other Script settings are ignored.

---



<h3 id="execute">Script Execution (Dev)</h3>

In **Dev** mode, you can test the Script by executing it manually.

Input data for `Args`, `Client`, and `Dashboard` is provided in JSON format.

The output of `Dashboard.sendMessage()` is displayed in the **Output** panel.

Any errors are also displayed in the **Output** panel.

---



<h3 id="code">Code</h3>

The Lua source code of the Script executed by the **Luaj** interpreter. Maximum length: 50,000 characters.

---




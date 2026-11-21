[Documentation](/doc)

<h1 id="main">Dashboard</h1> 

A dashboard is a custom HTML page designed to send data to and receive data from a script.

To make requests to a script, you must use the `dashboard.js` library.

You can include any additional libraries required for styling or functionality.
The directory for storing static files is defined by the `gnoolson.saturday.dashboard.static` parameter in the **Saturday configuration**.
Static files are accessible in HTML via the `/dashboard/static/` path.


---



<h1 id="dashboard_js">dashboard.js</h1>

The `dashboard.js` JavaScript library is required for sending data to and receiving data from a script.

The library uses Long Polling to receive incoming messages,
so there is no need to continuously poll the script for new messages.

To import it in HTML, use: `import dashboard from "/dashboard/static/js/dashboard.js"`.
Before using it, you must call the `setup(dashboardId, openDashboardId, onMessageCallback [, errorCallback])` function.

```js
const onMessageCallback = (message) => {};
dashboard.setup(dashboardId, openDashboardId, onMessageCallback);
```

Messages from the script are passed to `onMessageCallback`.  
To send a message to the script, use the `execute(message [, successCallback, errorCallback])` function. The message must be an object containing arbitrary data.

```js
dashboard.execute({ key : "value" });
```

To receive and send messages within a script, use the injected [Dashboard](/doc/lua_modules#dashboard) service.

---



<h1 id="fields">Fields</h1>




<h3 id="project">Project</h3>

The project to which this dashboard belongs. Required field.

---



<h3 id="name">Name</h3>

A unique dashboard name within the project. Maximum 64 characters. Required field.

---



<h4 id="description">Description</h4>

A text description of the dashboard. Maximum 5000 characters. Optional field.

---



<h3 id="script-handler">Script</h3>

The selected script will process requests from the dashboard and return responses. Required field.

---



<h3 id="access">Access</h3>

Access level for the dashboard. Required field.
- **SUPER** — only users in the **SUPER** group have access to the dashboard;
- **VIEWER** — users in both the **VIEWER** and **SUPER** groups have access to the dashboard;
- **ANONYMOUS** — accessing the dashboard does not require authentication.

---



<h3 id="html">HTML</h3>

A custom HTML page. Maximum 50,000 characters.

---
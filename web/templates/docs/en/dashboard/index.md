[Documentation](/docs)

<h1 id="main">Dashboard</h1>

A Dashboard is a custom HTML page designed for sending data to and receiving data from a Script.

To communicate with a Script, use the `dashboard.js` library.

You can include any additional libraries required for the appearance or functionality of your Dashboard.

The directory for storing static files is configured using the `gnoolson.saturday.dashboard.static` parameter in the **Saturday Configuration**.

Static files are available in HTML under the `/dashboard/static/` path.

---



<h1 id="dashboard_js">dashboard.js</h1>

The `dashboard.js` JavaScript library is used to send data to and receive data from a Script.

The library uses the **Long Polling** technique to receive incoming messages, so there is no need to continuously poll the Script for new messages.

To import the library into your HTML page, use:

`import dashboard from "/dashboard/static/js/dashboard.js"`.

Before using the library, call the `setup(dashboardId, openDashboardId, onMessageCallback [, errorCallback])` function.

```js
const dashboardId = window.dashboardId;
const openDashboardId = window.openDashboardId;
const onMessageCallback = (message) => {};

dashboard.setup(dashboardId, openDashboardId, onMessageCallback);
```

Messages sent from the Script are delivered to `onMessageCallback`.

To send a message to the Script, use the `execute(message [, successCallback, errorCallback])` function. The message must be a JavaScript object containing arbitrary data.

```js
dashboard.execute({ key: "value" });
```

To send and receive messages in a Script, use the injected [Dashboard](/docs/lua_modules#dashboard) service.

---



<h1 id="fields">Fields</h1>

<h3 id="project">Project</h3>

The Project to which this Dashboard belongs. Required field.

---



<h3 id="name">Name</h3>

A unique Dashboard name within the Project. Maximum length: 64 characters. Required field.

---



<h3 id="description">Description</h3>

A text description of the Dashboard. Maximum length: 5000 characters. Optional field.

---




<h3 id="script-handler">Script</h3>

The selected Script processes requests from the Dashboard and returns responses. Required field.

---



<h3 id="access">Access</h3>

The Dashboard access level. Required field.

- **Editor** — only users in the **Editor** group can access the Dashboard.
- **Viewer** — users in the **Viewer** and **Editor** groups can access the Dashboard.
- **Anonymous** — the Dashboard can be accessed without authentication.

---




<h3 id="html">HTML</h3>

The custom HTML page. Maximum length: 50,000 characters.

---




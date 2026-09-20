[Documentation](/docs)

<h1 id="main">Schedule</h1>

Runs a Script according to a schedule.

Please note: a scheduled execution will be skipped if the previous execution of the Script has not finished by the time the next execution is scheduled to start.

---

<h1 id="fields">Fields</h1>




<h3 id="project">Project</h3>

The Project to which this Schedule belongs. This field is required.

---



<h3 id="name">Name</h3>

A unique Schedule name within the Project. Maximum length: **64** characters. This field is required.

---




<h3 id="description">Description</h3>

A text description of the Schedule. Maximum length: **1000** characters. This field is optional.

---



<h3 id="script">Script</h3>

The selected Script will be executed according to the Schedule. This field is required.

---



<h3 id="cron">Cron Expression</h3>

A cron expression that defines when the Script is executed. This field is required.

---




[Documentation](/doc)

<h1 id="main">Schedule</h1>

Scheduled script execution.

Note: a scheduled run will be skipped if the previous execution of the script has not finished yet (that is, if the script takes longer to run than its scheduled interval).

---

<h1 id="fields">Fields</h1>



<h3 id="project">Project</h3>

The project to which this schedule belongs. Required field.

---



<h3 id="name">Name</h3>

A unique schedule name within the project. Maximum 64 characters. Required field.

---



<h3 id="description">Description</h3>

A text description of the schedule. Maximum 5000 characters. Optional field.

---



<h3 id="script">Script</h3>

The selected script will be executed according to this schedule. Required field.

---



<h3 id="cron">Cron expression</h3>

A cron expression defining the schedule for executing automated tasks. Required field.

---
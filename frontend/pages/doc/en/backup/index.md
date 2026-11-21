[Documentation](/doc)

<h1 id="main">Database backup</h1>

**Saturday** uses the **H2** database management system to store internal data (projects, scripts, etc.). To improve reliability, it is recommended to perform regular database backups.

You can configure the database backup parameters in the **Saturday configuration** file.

```properties
# enable database backups
gnoolson.saturday.h2.backup.use=true           

# schedule (CRON expression)
gnoolson.saturday.h2.backup.cron=0 0 0 * * *        

# directory for storing backup files
gnoolson.saturday.h2.backup.path=file:data/backup   

# maximum number of backup files to retain
gnoolson.saturday.h2.backup.max_files=3            
```
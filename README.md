# Saturday

[English] | [Українська](README.uk.md)

---

> [!WARNING]
> **Development Status: Public Beta**
> The project is currently in beta testing. The system is fully functional, but it may contain undiscovered bugs, unstable features, or inaccuracies in the documentation.

---

**An IoT platform for running your custom business logic and managing devices via simple Lua scripts.**

**Saturday** is a ready-to-use environment for automation, telemetry processing, and MQTT device coordination.

Instead of overcomplicating hardware firmware, Saturday allows you to offload overall business logic into a separate, manageable execution layer. You define system behavior using lightweight Lua scripts that can be updated on the fly — without recompiling or restarting the application.

#### How it works
Saturday connects to an MQTT broker as a client, subscribes to required topics, and instantly processes incoming messages based on user-defined Lua scripts.

---

### Core Features

- Real-time business logic execution: Instant processing and routing of MQTT messages.
- Custom HTML/CSS/JS Dashboards: Build web interfaces for monitoring and control with parameterized script triggers.
- Flexible triggers & scheduling: Run scripts via Cron schedules, MQTT events, dashboard interactions, or error handlers.
- Extensible architecture: Easily expand functionality through a plugin system.

#### Out-of-the-Box Lua Capabilities
Directly from Lua scripts, you get built-in access to:
- Databases and caching (for state management and telemetry storage);
- Telegram API (for alerts and interactive commands);
- Hardware Serial ports and OS-level operations;
- Interactions with plugins and other scripts.

#### Event Triggers
- MQTT: Incoming messages on subscribed topics.
- Dashboard: User actions within the web interface.
- Schedule: Automated Cron-based execution.
- Plugins: Internal and external plugin events.
- Network: Client connection and disconnection events.
- Dev Mode: Manual execution for debugging.
- Error Handling: Reactive execution when errors occur in other scripts.

#### Use Cases
Building automation systems, IoT infrastructure, telemetry pipelines, hardware dispatch systems, and flexible MQTT integrations.

---

### System Requirements
Saturday is a Java application (JAR) that requires **JRE 1.8** or higher.  
*RAM usage (example on `ibm-semeru-open-jre-21`): ~200–300 MB.*

---

### Getting Started

#### Option 1: Via Docker (Pre-configured Environment)
1. Go to the [Releases](https://github.com/gnoolson/saturday/releases) section and download the `saturday-docker-***.zip` archive.
2. Extract the archive to any directory.
3. Run the startup script for your OS:
    - **Linux:** `./start.sh`
    - **Windows:** `start.bat`
4. Open your browser at `http://localhost:5050`.

*To stop the application, run `./stop.sh` or `stop.bat`.*

#### Option 2: As a Standalone JAR
1. Go to the [Releases](https://github.com/gnoolson/saturday/releases) section and download the `saturday-jar-***.zip` archive.
2. Extract the archive to any directory.
3. Run the startup script for your OS:
    - **Linux:** `./start.sh`
    - **Windows:** `start.bat`
4. Open your browser at `http://localhost:5050`.

---

### Building from Source Code
- Build **Docker** image: `sh/docker_build.sh` (requires specifying `JRE_FOLDER` in the script)
- Build **JAR**: `sh/jar_build.sh`

*Build artifacts will be saved in the `_dist` directory.*

To build successfully, the `saturday` project directory must be placed in the same parent folder as [saturday-plugins](https://github.com/gnoolson/saturday-plugins).

---

### Detailed Documentation
Full documentation is available directly inside the running application at: `http://localhost:5050/docs`

---

### Plugins
The plugin repository is available here: [saturday-plugins](https://github.com/gnoolson/saturday-plugins).  
See the built-in documentation for instructions on how to build custom plugins.

---

### Demo Projects
Immediately after the first launch, several demo projects will be available to showcase how **Saturday** works in practice.

By default, all demo resources are disabled. To enable them after the first start:
- More -> MQTT Broker -> Start
- Scripts -> Enable (all scripts)
- Clients -> Connect (all clients)
- Schedules -> Enable (all schedules)

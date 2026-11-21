[Documentation](/doc)

# Plugin

Plugins are used to extend the functionality of Lua scripts. With plugins, you can implement complex business logic in Java and expose it to your scripts—enabling you to open sockets, interact with databases and the file system, trigger other scripts, and more.

Let's walk through creating a plugin using a temporary data store (cache) as an example.

---

### Step 1

Create a Maven project and add the following dependencies.

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>[https://jitpack.io](https://jitpack.io)</url>
    </repository>
</repositories>

<dependencies>

    <dependency>
        <groupId>com.github.gnoolson</groupId>
        <artifactId>saturday-plugin-api</artifactId>
        <version>2.1.1</version>
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>org.pf4j</groupId>
        <artifactId>pf4j</artifactId>
        <version>3.10.0</version>
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>org.luaj</groupId>
        <artifactId>luaj-jse</artifactId>
        <version>3.0.1</version>
        <scope>provided</scope>
    </dependency>
    
    <dependency>
        <groupId>com.github.gnoolson</groupId>
        <artifactId>luaj-utils</artifactId>
        <version>3.2.0</version>
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.36</version>
        <scope>provided</scope>
    </dependency>

</dependencies>


<build>
    <finalName>my-cache-plugin</finalName>
</build>
```

---
### Step 2

Define the functionality. Functionality represents the core module logic implemented in Java. The functionality class must implement the `gnoolson.saturday_plugin_api.Functionality.Functionality` interface and provide an implementation for `void writeResultToOutput(Output output)`.

The `writeResultToOutput(Output output)` method is called when a script runs in **Dev** mode.

The `Output` interface represents a text buffer where you can write execution output (optional).

For simplicity, only the cache functionality interface is shown below; its implementation is omitted.

```java
public interface MyCache extends Functionality {

    @Override
    void writeResultToOutput(Output output);

    Optional<Object> get(String key);

    Optional<Object> getAndProlong(String key);

    void put(String key, Object data, long storageTime);

    boolean remove(String key);

    void clear();

}
```

---
### Step 3

Create your main plugin class extending `gnoolson.saturday_plugin_api.SaturdayPlugin`.

`PluginManager` manages the plugin lifecycle.

Lifecycle methods:

- `void start()` - called when the plugin starts;
- `void stop()` - called when the plugin stops;
- `void onApplicationReady()` - called once **Saturday** has fully started and is ready to operate (invoked after `start()`).

```java
public class MyCachePlugin extends SaturdayPlugin {

    @Getter
    public MyCache cache;

    @Override
    public void start() {
        this.cache = new MyCacheImpl(); // any implementation of MyCache
    }

    @Override
    public void stop() {
        this.cache.clear();
    }

    @Override
    public void onApplicationReady() {
    }

}
```

---
### Step 4

Create a class extending `LuaModule` and implement the following methods:

- `void updateFunctionality(Functionality functionality);` - called to inject the functionality instance. Invoked before every script execution (even if the script is cached);
- `void release();` - called after the script finishes executing. Use this to close resources and perform cleanup;
- `String name();` - must return the name of the module.

The primary purpose of this class is to act as an adapter between Lua and Java, defining the functions and data exposed to Lua code.

Before each script execution, the `Functionality` reference on the `LuaModule` object is updated. The `LuaModule` instance itself is cached within the Sandbox, whereas the `Functionality` instance is not.

We will also use the `LuaArgUtils` utility class to validate function arguments.

```java
public class MyCacheLuaModule extends LuaModule {

    private MyCache cache;

    public MyCacheLuaModule() {
        set("get", new GetFunction(this));
        set("getAndProlong", new GetAndProlongFunction(this));
        set("put", new PutFunction(this));
        set("remove", new RemoveFunction(this));
    }

    @Override
    public void updateFunctionality(Functionality functionality) {
        this.cache = (MyCache) functionality;
    }

    @Override
    public void release() {
    }

    @Override
    public String name() {
        return "my_cache";
    }

    @RequiredArgsConstructor
    public static class GetFunction extends OneArgFunction {
        private final MyCacheLuaModule cacheLuaModule;

        @Override
        public LuaValue call(LuaValue keyLuaValue) {
            String key = LuaArgUtils.getStringFromFunctionArgs(keyLuaValue, 1, "key");
            Optional<Object> dataOpt = cacheLuaModule.cache.get(key);
            return dataOpt.map(o -> (LuaValue) o).orElse(LuaValue.NIL);
        }
    }

    @RequiredArgsConstructor
    public static class GetAndProlongFunction extends OneArgFunction {
        private final MyCacheLuaModule cacheLuaModule;

        @Override
        public LuaValue call(LuaValue keyLuaValue) {
            String key = LuaArgUtils.getStringFromFunctionArgs(keyLuaValue, 1, "key");
            Optional<Object> dataOpt = cacheLuaModule.cache.getAndProlong(key);
            return dataOpt.map(o -> (LuaValue) o).orElse(LuaValue.NIL);
        }
    }

    @RequiredArgsConstructor
    public static class PutFunction extends VarArgFunction {
        private final MyCacheLuaModule cacheLuaModule;

        @Override
        public Varargs invoke(Varargs args) {
            String key = LuaArgUtils.getStringFromFunctionArgs(
                LuaArgUtils.getLuaValueFromFunctionVarargs(args, 1, "key"),
                1,
                "key"
            );

            LuaValue data = LuaArgUtils.getLuaValueFromFunctionVarargs(args, 2, "data");

            long storageTime = LuaArgUtils.getLongFromFunctionArgs(
                LuaArgUtils.getLuaValueFromFunctionVarargs(args, 3, "storageTime"),
                3,
                "storageTime"
            );

            cacheLuaModule.cache.put(key, data, storageTime);
            return LuaValue.NIL;
        }
    }

    @RequiredArgsConstructor
    public static class RemoveFunction extends OneArgFunction {
        private final MyCacheLuaModule cacheLuaModule;

        @Override
        public LuaValue call(LuaValue keyLuaValue) {
            String key = LuaArgUtils.getStringFromFunctionArgs(keyLuaValue, 1, "key");
            return LuaBoolean.valueOf(cacheLuaModule.cache.remove(key));
        }
    }

}
```

---
### Step 5

Create two classes implementing `gnoolson.saturday_plugin_api.FunctionalityFactory` and `gnoolson.saturday_plugin_api.LuaModuleFactory`.

These factories return instances of `Functionality` and `LuaModule` and are invoked when a script is executed.

If caching is enabled for a script, `LuaModuleFactory.getInstance()` is called only once—during the first execution of the script.

```java
public class FunctionalityFactoryImpl implements FunctionalityFactory {

    private final MyCache cache;

    public FunctionalityFactoryImpl(MyCache cache) {
        this.cache = cache;
    }

    @Override
    public Functionality getInstance() {
        return cache;
    }

}

public class LuaModuleFactoryImpl implements LuaModuleFactory {

    @Override
    public LuaModule getInstance() {
        return new MyCacheLuaModule();
    }

}
```

---
### Step 6

Create two classes implementing `gnoolson.saturday_plugin_api.LuaModuleFactoryProvider` and `gnoolson.saturday_plugin_api.FunctionalityFactoryProvider`, and annotate them with `org.pf4j.Extension`.

The `void setup(SaturdayPlugin plugin)` method is called after instantiation and receives the `SaturdayPlugin` instance (defined in Step 3).

These providers return instances of `LuaModuleFactory` and `FunctionalityFactory`. The `getFactory()` method is called only once when the plugin starts.

```java
@Extension
public class FunctionalityFactoryProviderImpl implements FunctionalityFactoryProvider {

    private FunctionalityFactory functionalityFactory;

    @Override
    public FunctionalityFactory getFactory() {
        return functionalityFactory;
    }

    @Override
    public void setup(SaturdayPlugin saturdayPlugin) {
        MyCachePlugin cachePlugin = (MyCachePlugin) saturdayPlugin;
        functionalityFactory = new FunctionalityFactoryImpl(cachePlugin.getCache());
    }
}

@Extension
public class LuaModuleFactoryProviderImpl implements LuaModuleFactoryProvider {

    private final LuaModuleFactory luaModuleFactory = new LuaModuleFactoryImpl();

    @Override
    public LuaModuleFactory getFactory() {
        return luaModuleFactory;
    }

    @Override
    public void setup(SaturdayPlugin saturdayPlugin) {
    }
}
```

---
### Step 7

Create a `pf4j.properties` file inside the `resources` directory with the following contents:

```properties
plugin.id=my_cache
plugin.class=com.test.MyCachePlugin
plugin.version=1.0.0
plugin.provider=Your Name
```

---
### Step 8

Build the Maven project, copy the resulting `.jar` file to the `./plugins/{plugin_id}/` directory, and restart **Saturday**.

Each plugin must reside in its own dedicated directory.

The plugin file must be in `.jar` format, and its filename must end with `plugin.jar` (for example, `my-cache-plugin.jar`). This naming convention allows `PluginManager` to recognize the file as a plugin.

---
### Usage in Lua

```lua
local MyCache = require("my_cache")

MyCache.put("cool_key", {Pi = 3.14}, 10000)

local data = MyCache.getAndProlong("cool_key")

MyCache.remove("cool_key")
```

---
### Step 9. Plugin page (optional)

A plugin can provide its own web page for management, status display, documentation, and so on.

Interaction with a plugin page works in two ways:
- via a GET request with query parameters, which expects content in return (`html`, `markdown`, or `thymeleaf_template`);
- via a POST request with a JSON request body, which expects a JSON response body.

The plugin page is accessible at `/plugin/{pluginId}`. POST requests sent to this URL are routed to `executeAction`.

To handle page interactions, create a class implementing `gnoolson.saturday_plugin_api.WebEndpoint`, annotate it with `org.pf4j.Extension`, and implement two methods:  

* `Content getContent(String language, String path, Map<String, String> requestParams)` - returns content for a GET request;
* `Map<String, Object> executeAction(Map<String, Object> requestBody)` - returns data for a POST request.

The returned `Content` object can be one of three types: 

* `ThymeleafContent` objects — processed using `SpringTemplateEngine`;   
* `MarkdownContent` objects — processed using `MarkdownParser` to render HTML;
* `Content` objects — unprocessed; the content is returned as-is, which is useful for serving plain text information on the page.

Let's look at an example of a plugin with two pages: one displaying currently cached keys, and another containing brief documentation.

To support this, add a `Collection<String> getKeys()` method to the `Cache` interface to return the collection of keys to display (the `Cache` implementation is omitted).

The keys page will be implemented using `ThymeleafContent`, and the documentation page using `MarkdownContent`.

```java
@Extension
public class WebEndpointImpl implements WebEndpoint {

    private Cache cache;
    
    @Override
    public Content getContent(String language, String path, Map<String, String> requestParams) {
        if(path.endsWith("/doc")){
            return getDocContent(language);
        }

        return getInfoContent(language);
    }

    @Override
    public Map<String, Object> executeAction(Map<String, Object> requestBody) {
        return Collections.emptyMap();
    }

    @Override
    public void setup(SaturdayPlugin saturdayPlugin) {
        CachePlugin cachePlugin = (CachePlugin)saturdayPlugin;
        this.cache = cachePlugin.getCache();
    }
    
    private ThymeleafContent getInfoContent(String language){
        String content;
        if (language.equals("uk")) {
            content = readContent("/content/info_uk.html");
        } else {
            content = readContent("/content/info_en.html");
        }

        Collection<String> keys = cache.getKeys();

        Map<String, Object> models = new HashMap<>(1);
        models.put("keys", keys);

        return new ThymeleafContent(content, models);
    }

    private MarkdownContent getDocContent(String language){
        String content;
        if (language.equals("uk")) {
            content = readContent("/content/doc_uk.md");
        } else {
            content = readContent("/content/doc_en.md");
        }

        return new MarkdownContent(content);
    }

    private String readContent(String path) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
```

In `resources`, create a `content` directory containing the page templates: `info_en.html`, `info_uk.html`, `doc_en.md`, and `doc_uk.md`.

If a page uses `ThymeleafContent`, it must define a fragment named `content`. Only this fragment will be processed by `SpringTemplateEngine` and used to render the model.
Any content outside this fragment will be ignored. 

Contents of `info_en.html` (`info_uk.html` follows the same structure with Ukrainian text):

```html

<div th:fragment="content">

    <div class="pure-g pb-5">
        <div class="pure-u-1-2">
            <a href="/plugin/cache/doc">Documentation</a>
        </div>
        <div class="pure-u-1-2 s-align-right">
            <button onclick="location.reload();" class="pure-button">Refresh</button>
        </div>
    </div>

    <div>
        <h3>
            Keys
        </h3>

        <table class="pt-2 pure-table s-w100">
            <thead>
            <tr>
                <th>Key</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="key : ${keys}">
                <td th:text="${key}">Key</td>
            </tr>
            <tr th:if="${keys.isEmpty()}">
                <td colspan="4" class="s-align-center">Nothing to display</td>
            </tr>
            </tbody>
        </table>
    </div>

</div>

```

Contents of `doc_en.md` (`doc_uk.md` follows the same structure with Ukrainian text):

```markdown

<div class="pb-5">
    <a href="/plugin/cache">Keys</a>
</div>

TEXT
TEXT
TEXT
TEXT
TEXT

##### Example
`` `lua
local Cache = require("cache")

-- Lua
`` `

```

### P.S.

If `LuaModule` does not rely on `Functionality`, you can return the same `LuaModule` instance and omit implementing `Functionality`, `FunctionalityFactory`, and `FunctionalityFactoryProvider`.
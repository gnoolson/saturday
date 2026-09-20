[Documentation](/docs)

# Plugin

Plugins are used to extend the functionality of Lua Scripts. They allow you to implement complex business logic in Java and make it available from Scripts. This makes it possible to open sockets, work with databases and the file system, execute other Scripts, and much more.

In this guide, we will create a Plugin that provides an in-memory data store as an example.

---



### Step 1

Create a Maven project and add the following dependencies.

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
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
        <version>3.1.0</version>
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

Create the functionality. The functionality contains the core logic of the module implemented in Java. The functionality class must implement the `gnoolson.saturday_plugin_api.Functionality.Functionality` interface and provide an implementation of the `void writeResultToOutput(Output output)` method.

The `writeResultToOutput(Output output)` method is called when a Script is executed in **Dev** mode.

The `Output` interface represents a text buffer that can be used to write execution results (optional).

For simplicity, only the cache functionality interface is shown below. Its implementation is outside the scope of this guide.

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

Create your own Plugin class by extending `gnoolson.saturday_plugin_api.SaturdayPlugin`.

The `PluginManager` manages the Plugin lifecycle.

Lifecycle methods:

- `void start()` — called when the Plugin starts;
- `void stop()` — called when the Plugin stops;
- `void onApplicationReady()` — called after **Saturday** has fully started and the system is ready for use (invoked after `start()`).

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

Create a class that extends `LuaModule` and implement the following methods:

- `void updateFunctionality(Functionality functionality);` — called to assign the functionality instance. This method is invoked before every Script execution (even if the Script is cached);
- `void release();` — called after the Script finishes execution. Use this method to close resources or perform other cleanup tasks;
- `String name();` — must return the module name.

The primary purpose of this class is to act as an adapter between Lua and Java. It defines the functions and data that will be available from Lua code.

Before each Script execution, the `Functionality` reference of the `LuaModule` instance is updated. The `LuaModule` instance itself is cached inside the Sandbox, while the `Functionality` instance is not.

For function argument validation, we will also use the `LuaArgUtils` utility class.

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

Create two classes that implement the `gnoolson.saturday_plugin_api.FunctionalityFactory` and `gnoolson.saturday_plugin_api.LuaModuleFactory` interfaces.

These factories are responsible for creating `Functionality` and `LuaModule` instances. They are invoked whenever a Script is executed.

If Script caching is enabled, the `getInstance()` method of `LuaModuleFactory` is called only once, during the first Script execution.

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

Create two classes that implement the `gnoolson.saturday_plugin_api.LuaModuleFactoryProvider` and `gnoolson.saturday_plugin_api.FunctionalityFactoryProvider` interfaces, and annotate them with `org.pf4j.Extension`.

The `void setup(SaturdayPlugin plugin)` method is called immediately after the provider instance is created. It receives the `SaturdayPlugin` instance described earlier.

These classes must return `LuaModuleFactory` and `FunctionalityFactory` instances. The `getFactory()` method is called only once after the Plugin starts.

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

Create a `pf4j.properties` file in the `resources` directory with the following contents:

```properties
plugin.id=my_cache
plugin.class=com.test.MyCachePlugin
plugin.version=1.0.0
plugin.provider=Your Name
plugin.description=Plugin description
```

Replace the plugin class name with the fully qualified name of your Plugin class.

---



### Step 8

Build the Maven project, then copy the generated `.jar` file to the `./plugins/{plugin_id}/` directory and restart **Saturday**.

Each Plugin must be placed in its own directory.

The Plugin must be packaged as a `.jar` file, and its filename must end with `plugin.jar` (for example, `my-cache-plugin.jar`). This allows the `PluginManager` to recognize the file as a Plugin.

---



### Using the Plugin from Lua

```lua
local MyCache = require("my_cache")

MyCache.put("cool_key", {Pi = 3.14}, 10000)

local data = MyCache.getAndProlong("cool_key")

MyCache.remove("cool_key")
```

---



### Step 9. Plugin Page (Optional)

A Plugin can have its own page, where you can implement management functionality, display information, provide documentation, and more.

There are two ways to interact with the page:

* via a GET request with parameters, which expects content to be returned (`html`, `markdown`, `thymeleaf_template`);
* via a POST request with a JSON request body, which expects a JSON response body.

The Plugin page is available at `/plugin/{pluginId}`. POST requests to this URL are handled as `executeAction`.

To allow a Plugin to handle page interactions, create a class that implements the `gnoolson.saturday_plugin_api.WebEndpoint` interface, annotate it with `org.pf4j.Extension`, and implement the following two methods:

* `Content getContent(String language, String path, Map<String, String> requestParams)` — returns content for a GET request;
* `Map<String, Object> executeAction(Map<String, Object> requestBody)` — returns data for a POST request.

The returned `Content` object can be one of three types:

* objects of the `ThymeleafContent` class. They are processed using `SpringTemplateEngine`;  
* objects of the `MarkdownContent` class. They are processed using `MarkdownParser` to produce HTML markup;
* objects of the `Content` class. They are not processed. The content is returned unchanged. This can be used to provide text information that will be displayed on the page.

Let's consider an example of a Plugin with two pages: one displays information about the keys currently used in the Cache, and the other contains short documentation.

For this example, add the `Collection<String> getKeys()` method to the `Cache` interface. The method returns a collection of keys to be displayed (the `Cache` implementation is not covered here).

The keys page will be implemented as `ThymeleafContent`, while the documentation page will be implemented as `MarkdownContent`.

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

Create a `content` directory in `resources`. It will contain the following pages: `info_en.html`, `info_uk.html`, `doc_en.md`, and `doc_uk.md`.

If the page is a `ThymeleafContent`, it must contain a fragment named `content`. This is the part that will be processed by `SpringTemplateEngine` and used to render the model.

All content outside this fragment will be ignored.

The contents of `info_en.html` (the `info_uk.html` file has the same structure but contains Ukrainian text):

```html
<div th:fragment="content">

    <div class="grid pb-5">
        <div>
            <a href="/plugin/cache/doc" role="button" class="secondary">Documentation</a>
        </div>
        <div style="text-align: right;">
            <button onclick="location.reload();">Refresh</button>
        </div>
    </div>

    <div>
        <h3>
            Keys
        </h3>

        <table class="pt-2">
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
                <td colspan="4" style="text-align: center">Nothing to display</td>
            </tr>
            </tbody>
        </table>
    </div>

</div>
```

The contents of `doc_en.md` (the `doc_uk.md` file has the same structure but contains Ukrainian text):

```markdown
<div class="pb-5">
    <a href="/plugin/cache" role="button" class="secondary">Keys</a>
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




### Notes

If your `LuaModule` does not use a `Functionality` instance, you can always return the same `LuaModule` instance and omit the `Functionality`, `FunctionalityFactory`, and `FunctionalityFactoryProvider` implementations.





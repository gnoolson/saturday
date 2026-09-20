[Документація](/docs)

# Плагін

Плагіни використовуються для розширення функціональності Lua Сценаріїв. За допомогою Плагінів можна реалізувати складну бізнес-логіку на Java та використовувати її у Сценаріях. Це дає змогу відкривати сокети, працювати з базами даних, файловою системою, запускати інші Сценарії тощо.

Розглянемо створення Плагіну на прикладі тимчасового сховища даних.

---



### Крок 1

Створіть Maven-проєкт і додайте такі залежності.

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



### Крок 2

Створіть функціональність. Функціональність — це основна логіка модуля, реалізована на Java. Клас функціональності повинен імплементувати інтерфейс `gnoolson.saturday_plugin_api.Functionality.Functionality` та реалізувати метод `void writeResultToOutput(Output output)`.

Метод `writeResultToOutput(Output output)` викликається під час запуску Сценарію в режимі **Dev**.

Інтерфейс `Output` описує текстовий буфер, у який можна записувати результат виконання (необов'язково).

Для прикладу наведено лише інтерфейс функціональності кешу. Реалізацію цього інтерфейсу розглядати не будемо.

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



### Крок 3

Створіть власний клас Плагіну, який розширює клас `gnoolson.saturday_plugin_api.SaturdayPlugin`.

`PluginManager` керує життєвим циклом Плагіну.

Методи життєвого циклу:

- `void start()` — викликається під час запуску Плагіну;
- `void stop()` — викликається під час зупинки Плагіну;
- `void onApplicationReady()` — викликається після повного запуску **Saturday**, коли система готова до роботи (викликається після `start()`).

```java
public class MyCachePlugin extends SaturdayPlugin {

    @Getter
    public MyCache cache;

    @Override
    public void start() {
        this.cache = new MyCacheImpl(); // будь-яка реалізація MyCache
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



### Крок 4

Створіть клас, який розширює `LuaModule`, та реалізуйте такі методи:

- `void updateFunctionality(Functionality functionality);` — викликається для встановлення функціональності. Викликається перед кожним запуском Сценарію (навіть якщо Сценарій кешується);
- `void release();` — викликається після завершення роботи Сценарію. Тут можна закрити ресурси та виконати інші дії з очищення;
- `String name();` — повинен повертати назву модуля.

Основне призначення цього класу — бути адаптером між Lua та Java. У ньому потрібно описати функції та дані, які будуть доступні в Lua-коді.

Перед кожним запуском Сценарію для об'єкта `LuaModule` оновлюється посилання на об'єкт `Functionality`. Сам екземпляр `LuaModule` кешується в Sandbox, тоді як екземпляр `Functionality` — ні.

Для перевірки аргументів функцій додатково будемо використовувати клас `LuaArgUtils`.

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



### Крок 5

Створіть два класи, які реалізують інтерфейси `gnoolson.saturday_plugin_api.FunctionalityFactory` та `gnoolson.saturday_plugin_api.LuaModuleFactory`.

Ці фабрики повинні повертати екземпляри `Functionality` та `LuaModule`. Вони викликаються під час запуску Сценарію.

Якщо для Сценарію ввімкнене кешування, метод `getInstance()` класу `LuaModuleFactory` буде викликаний лише один раз — під час першого запуску Сценарію.

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



### Крок 6

Створіть два класи, які реалізують інтерфейси `gnoolson.saturday_plugin_api.LuaModuleFactoryProvider` та `gnoolson.saturday_plugin_api.FunctionalityFactoryProvider`, і позначте їх анотацією `org.pf4j.Extension`.

Метод `void setup(SaturdayPlugin plugin)` викликається після створення екземпляра класу та отримує екземпляр `SaturdayPlugin` (той, що був описаний вище).

Ці класи повинні повертати екземпляри `LuaModuleFactory` та `FunctionalityFactory`. Метод `getFactory()` викликається лише один раз після запуску Плагіну.

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



### Крок 7

Створіть файл `pf4j.properties` у каталозі `resources` з таким вмістом:

```properties
plugin.id=my_cache
plugin.class=com.test.MyCachePlugin
plugin.version=1.0.0
plugin.provider=Your Name
```

---



### Крок 8

Зберіть Maven-проєкт, після чого скопіюйте `.jar` файл до каталогу `./plugins/{plugin_id}/` і перезапустіть **Saturday**.

Кожен Плагін повинен знаходитися у власному каталозі.

Файл Плагіну повинен бути у форматі `.jar`, а його назва повинна закінчуватися на `plugin.jar` (наприклад, `my-cache-plugin.jar`). Це необхідно для того, щоб `PluginManager` міг визначити, що файл є Плагіном.

---



### Використання в Lua

```lua
local MyCache = require("my_cache")

MyCache.put("cool_key", {Pi = 3.14}, 10000)

local data = MyCache.getAndProlong("cool_key")

MyCache.remove("cool_key")
```

---



### Крок 9. Сторінка Плагіну (необов'язково)

Плагін може мати власну сторінку, на якій можна реалізувати керування, відображення інформації, документацію тощо.

Взаємодія зі сторінкою може здійснюватися двома способами:
- через GET-запит із параметрами, який очікує повернення контенту (`html`, `markdown`, `thymeleaf_template`);
- через POST-запит із тілом запиту у форматі JSON, який очікує повернення тіла відповіді у форматі JSON.

Сторінка Плагіну буде доступна за адресою `/plugin/{pluginId}`. POST-запити на цей URL будуть оброблятися як `executeAction`.

Щоб Плагін міг обробляти взаємодію зі сторінкою, необхідно створити клас, який імплементує інтерфейс `gnoolson.saturday_plugin_api.WebEndpoint`, 
позначити його анотацією `org.pf4j.Extension` та реалізувати два методи:  

* `Content getContent(String language, String path, Map<String, String> requestParams)` — повертає контент для GET-запиту;
* `Map<String, Object> executeAction(Map<String, Object> requestBody)` — повертає дані для POST-запиту.

Об'єкт `Content`, що повертається, може бути одного з трьох типів: 

* об'єкти класу `ThymeleafContent`. Обробляються за допомогою `SpringTemplateEngine`;   
* об'єкти класу `MarkdownContent`. Обробляються за допомогою `MarkdownParser` для отримання HTML-розмітки;
* об'єкти класу `Content`. Не обробляються. Контент передається без змін. Таким способом можна передавати текстову інформацію, яка буде відображена на сторінці.

Розглянемо приклад Плагіну з двома сторінками: одна відображатиме інформацію про ключі, які зараз використовуються в Кеші, а друга міститиме коротку документацію.

Для цього додамо до інтерфейсу `Cache` метод `Collection<String> getKeys()`, який повертатиме колекцію ключів для відображення (імплементацію `Cache` не розглядаємо).

Сторінка з ключами буде реалізована як `ThymeleafContent`, а сторінка документації — як `MarkdownContent`.

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

У `resources` створимо каталог `content`, у якому зберігатимуться сторінки: `info_en.html`, `info_uk.html`, `doc_en.md` та `doc_uk.md`.

Якщо сторінка є `ThymeleafContent`, вона повинна містити фрагмент з іменем `content`. Саме ця частина буде оброблена `SpringTemplateEngine` та використана для відображення моделі.
Увесь вміст, що знаходиться поза цим фрагментом, буде проігноровано. 

Вміст файлу `info_en.html` (файл `info_uk.html` має таку саму структуру, але містить текст українською мовою):

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

Вміст файлу `doc_en.md` (файл `doc_uk.md` має таку саму структуру, але містить текст українською мовою):

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



### P.S.

Якщо `LuaModule` не використовує `Functionality`, можна повертати один і той самий екземпляр `LuaModule` та не реалізовувати `Functionality`, `FunctionalityFactory` і `FunctionalityFactoryProvider`.




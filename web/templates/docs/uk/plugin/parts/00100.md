### Використання в Lua

```lua
local MyCache = require("my_cache")

MyCache.put("cool_key", {Pi = 3.14}, 10000)

local data = MyCache.getAndProlong("cool_key")

MyCache.remove("cool_key")
```

---
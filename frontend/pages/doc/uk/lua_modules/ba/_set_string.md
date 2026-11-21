<span id="ba-setString"></span>

- `setString(string, position)` - записує рядок.

```lua
local bytes = data:setString("hello world", 0)
```

Перетворює `string` у `byte[]` у кодуванні UTF-8 та записує його, починаючи із вказаної позиції.
Повертає кількість записаних байтів `number`.

---
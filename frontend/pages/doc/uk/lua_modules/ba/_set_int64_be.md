<span id="ba-setInt64Be"></span>

- `setInt64Be(number, position)` - записує значення типу `int64` у форматі **Big Endian**.

```lua
local bytes = data:setInt64Be(-1000000000000, 0)
```

Перетворює `number` у значення типу `int64` і записує його на вказану позицію.
Повертає кількість записаних байтів `number`.

---
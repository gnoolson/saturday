<span id="ba-setInt64Le"></span>

- `setInt64Le(number, position)` - записує значення типу `int64` у форматі **Little Endian**.

```lua
local bytes = data:setInt64Le(-1000000000000, 0)
```

Перетворює `number` у значення типу `int64` і записує його на вказану позицію.
Повертає кількість записаних байтів `number`.

---
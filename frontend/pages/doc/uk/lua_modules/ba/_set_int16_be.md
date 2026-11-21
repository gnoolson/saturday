<span id="ba-setInt16Be"></span>

- `setInt16Be(number, position)` - записує значення типу `int16` у форматі **Big Endian**.

```lua
local bytes = data:setInt16Be(-32768, 0)
```

Перетворює `number` у значення типу `int16` і записує його на вказану позицію.
Повертає кількість записаних байтів `number`.

---
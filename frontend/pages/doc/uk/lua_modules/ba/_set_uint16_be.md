<span id="ba-setUint16Be"></span>

- `setUint16Be(number, position)` - записує значення типу `uint16` у форматі **Big Endian**.

```lua
local bytes = data:setUint16Be(65535, 0)
```

Перетворює `number` у значення типу `uint16` і записує його на вказану позицію.
Повертає кількість записаних байтів `number`.

---
<span id="ba-setUint32Be"></span>

- `setUint32Be(number, position)` - записує значення типу `uint32` у форматі **Big Endian**.

```lua
local bytes = data:setUint32Be(4294967295, 0)
```

Перетворює `number` у значення типу `uint32` і записує його на вказану позицію.
Повертає кількість записаних байтів `number`.

---
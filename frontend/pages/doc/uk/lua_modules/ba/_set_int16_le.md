<span id="ba-setInt16Le"></span>

- `setInt16Le(number, position)` - записує значення типу `int16` у форматі **Little Endian**.

```lua
local bytes = data:setInt16Le(-32768, 0)
```

Перетворює `number` у значення типу `int16` і записує його на вказану позицію.
Повертає кількість записаних байтів `number`.

---
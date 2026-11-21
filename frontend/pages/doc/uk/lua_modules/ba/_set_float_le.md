<span id="ba-setFloatLe"></span>

- `setFloatLe(number, position)` - записує значення типу `float` у форматі **Little Endian**.

```lua
local bytes = data:setFloatLe(3.1415, 0)
```

Перетворює `number` у значення типу `float` і записує його на вказану позицію.
Повертає кількість записаних байтів `number`.

---
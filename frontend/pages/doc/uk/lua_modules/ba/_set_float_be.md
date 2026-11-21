<span id="ba-setFloatBe"></span>

- `setFloatBe(number, position)` - записує значення типу `float` у форматі **Big Endian**.

```lua
local bytes = data:setFloatBe(3.1415, 0)
```

Перетворює `number` у значення типу `float` і записує його на вказану позицію.
Повертає кількість записаних байтів `number`.

---
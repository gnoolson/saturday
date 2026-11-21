<span id="ba-setDoubleBe"></span>

- `setDoubleBe(number, position)` - записує значення типу `double` у форматі **Big Endian**.

```lua
local bytes = data:setDoubleBe(3.1415, 0)
```

Перетворює `number` у значення типу `double` і записує його на вказану позицію.
Повертає кількість записаних байтів `number`.

---
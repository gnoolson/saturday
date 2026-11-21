<span id="ba-put"></span>

- `put(anotherByteArray, position)` - копіює дані іншого `ByteArray` у поточний.

```lua
local bytes = data:put(data2, 8)
```

Запис починається з позиції `8`. Повертає кількість записаних байтів `number` (довжину `anotherByteArray`).

---
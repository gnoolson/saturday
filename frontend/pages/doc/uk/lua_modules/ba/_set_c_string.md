<span id="ba-setCString"></span>

- `setCString(string, position)` - записує рядок.

```lua
local bytes = data:setCString("hello world", 0)
```

Перетворює `string` у `byte[]` у кодуванні UTF-8 та записує його, починаючи із вказаної позиції. Додає `\0` в на позицію після останнього записаного байта.
Повертає кількість записаних байтів `number`.

---
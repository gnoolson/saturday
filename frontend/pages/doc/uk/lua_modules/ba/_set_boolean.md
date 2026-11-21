<span id="ba-setBoolean"></span>

- `setBoolean(flag, position)` - записує значення типу `boolean`.

```lua
local bytes = data:setBoolean(true, 0)
```

Перетворює `boolean` у значення типу `int8` і записує його на вказану позицію. 
Повертає кількість записаних байтів `number`.

---
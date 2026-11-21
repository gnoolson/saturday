### Обробка помилок (pcall)

```lua
local function divide(a, b)
    -- error("message")

    return a / b
end

local success, result = pcall(divide, 10, "apple")

if success then
    Output.print("Success! Result: " .. result)
else
    Output.print("Error: " .. result)
end
```

---
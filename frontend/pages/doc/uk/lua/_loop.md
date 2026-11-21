### Цикли

```lua
local i = 1

while i <= 10 do
    i = i + 1

    if i == 8 then
        break
    end
end

for i = 1, 10, 1 do
    -- початкове значення, кінцеве значення, крок
end

local months = {
    "January",
    "February",
    "March",
    "April"
}

for index, value in ipairs(months) do
    -- для масивів
end

for key, value in pairs(months) do
    -- для таблиць
end

local length = #months -- довжина масиву
Output.print(length)
```

---
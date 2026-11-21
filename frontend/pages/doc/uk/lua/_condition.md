### Умови

```lua
local flag = true

if flag then
    -- дія
end

flag = false

if not flag then
    -- дія
end

local x = 60

if x == 100 then
    -- дія
elseif x ~= 60 then
    -- "~=" означає "не дорівнює"
elseif x < 18 then
    -- дія
elseif (x >= 16) and (x < 18) then
    -- логічне "і"
elseif (x > 10000) or (x < -10000) then
    -- логічне "або"
else
    -- виконується, якщо жодна умова не виконалася
end
```

---
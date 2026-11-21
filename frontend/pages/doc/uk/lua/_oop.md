### OOP

```lua
Box = {}
Box.__index = Box

function Box:new(height, width, length)
    local newObj = {
        height = height,
        width = width,
        length = length
    }

    setmetatable(newObj, Box)

    return newObj
end

function Box:volume()
    return self.height * self.width * self.length
end

local box = Box:new(2, 2, 4)
local volume = box:volume()
```

---
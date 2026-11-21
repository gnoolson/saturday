### Таблиці (об'єкти, масиви)

```lua
local value1 = {} -- порожня таблиця

local value2 = {
    pi = 3.14,
    e = 2.71828
}

local pi = value2.pi

local value3 = {
    ["simple string"] = 123456789
}

local simpleString = value3["simple string"] -- рядок як ключ

local value4 = {
    [3.14] = 987654321
}

local v = value4[3.14] -- число як ключ

local value5 = {1, 2, 3, "str", {}, nil, 4}
local secondValue = value5[2] -- індексація починається з 1

local value6 = {
    action = function()
        -- дія
    end
}

value6.action()

local value7 = {
    message = "Hello",

    sayHello = function(self)
        Output.print(self.message)
    end
}

value7.sayHello(value7) -- виклик через "."
value7:sayHello()       -- виклик через ":" (self передається автоматично)
```

---
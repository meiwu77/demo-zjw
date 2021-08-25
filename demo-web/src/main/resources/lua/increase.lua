-- key
local key = KEYS[1]
-- 初始值，默认 0
local initValue = KEYS[2]
-- 过期时间
local expire = KEYS[3]
local value = redis.call('get',key)

-- 初始值
if(initValue == nil)
then
    initValue = 0
end

if(value == false)
then
    -- 设置初始值
    redis.call('setex',key,expire,initValue)
end
-- 自增
value = redis.call('incr',key)


return value

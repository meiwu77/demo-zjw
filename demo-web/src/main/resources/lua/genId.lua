local key = KEYS[1]
local expire = KEYS[2]
local value
local id = redis.call('get',key)
if(id == false)
then
    redis.call('set',key,1)
    value = 1
else
    redis.call('set',key,id+1)
    value =  id + 1
end
-- 设置过期时间
if(expire ~= nil)
then
    redis.call('expire',key,expire)
end
return value
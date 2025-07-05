explain
select e.id, e.name, e.password, e.username
from employee e
where e.username = 'admin';


select d.id          as id,
       d.name        as name,
       price,
       image,
       description,
       d.status      as status,
       c.name        as categoryName,
       category_id   as categoryId,
       d.create_time as createTime,
       d.update_time as updateTime,
       d.create_user as createUser,
       d.update_user as updateUser,
       make_time     as makeTime
from dish as d
         left join category c on d.category_id = c.id
WHERE category_id = ? d.status = ?
;



select count(*)
from setmeal
where status = 1
  and id IN (11);


update setmeal
SET category_id = ?,
    name=?,
    price=?,
    status      = ?,
    description=?,
    image=?,
    make_time=? create_time=?, update_time=?, create_user=?, update_user=?
where id = ?;


/* 起售时套餐菜品不能包含停售菜品 */
# 子查询方式
select count(*)
from dish
where status = 0
  and id in (select dish_id
             from setmeal_dish
             where setmeal_id = 3);

/* 连接查询方式 */
select *
from setmeal s
         left join setmeal_dish sd on s.id = sd.setmeal_id
         left join dish d on sd.dish_id = d.id
where s.id = 3 and  d.status = 0;



create type unit_type as enum('PCS','KG','L');
create table DishIngredient(
                               id serial primary key ,
                               id_dish int references dish(id),
                               id_ingredient int references ingredient(id),
                               quantity_required numeric,
                               unit unit_type
);

insert into DishIngredient( id_dish, id_ingredient, quantity_required, unit)
values(1,1,0.20,'KG'),
      (1,2,0.15,'KG'),
      (2,3,1.00,'KG'),
      (4,4,0.30,'KG'),
      (4,5,0.20,'KG');
insert into dish( name, dish_type,selling_price)
values ('salade fraiche','STARTER',3500.00),
       ('Poulet grilleé','MAIN',12000.00),
       ('riz aux légumes','MAIN',NULL),
       ('Gâteau au chocolat','DESSERT',8000.00),
       ('salade de fruits','DESSERT',NULL);
-- ALTER TABLE dish rename column price to selling_price;
UPDATE dish
SET selling_price = 3500.00
    WHERE id = 1;

UPDATE dish
SET selling_price = 12000.00
WHERE id = 2;

UPDATE dish
SET selling_price = NULL
WHERE id = 3;

UPDATE dish
SET selling_price = 8000.00
WHERE id = 4;

UPDATE dish
SET selling_price = NULL
WHERE id = 5;
select * from dish;

create table dish
(
    id        serial primary key,
    name      varchar(255),
    dish_type dish_type
);

create type ingredient_category as enum ('VEGETABLE', 'ANIMAL', 'MARINE', 'DAIRY', 'OTHER');

create table ingredient
(
    id       serial primary key,
    name     varchar(255),
    price    numeric(10, 2),
    category ingredient_category,

);
select DishIngredient.id_ingredient from    DishIngredient where id_dish = ?;

select id_dish from DishIngredient where id_ingredient = ?;
select dish.id, dish.name, dish.dish_type, dish.selling_price , ingredient.id, ingredient.name, ingredient.price,ingredient.category from DishIngredient join dish on dish.id = id_dish join ingredient on ingredient.id= id_ingredient  where dish.id = 1;

select id_ingredient,id_dish,dish.name,
       ingredient.id AS idIngredient, ingredient.name AS ingredient_name, ingredient.price,ingredient.category , dish.name AS dish_name
from DishIngredient
         join dish on dish.id = id_dish
         join ingredient on ingredient.id= id_ingredient
where ingredient.name ilike '%laitue%' or ingredient.category ='VEGETABLE' or dish.name ilike '%salade%' LIMIT 2 OFFSET 1

    ;



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
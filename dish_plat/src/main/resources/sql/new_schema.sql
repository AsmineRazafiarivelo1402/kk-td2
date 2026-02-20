create type unit_type as enum('PCS','KG','L');
create table DishIngredient(
                               id serial primary key ,
                               id_dish int references dish(id),
                               id_ingredient int references ingredient(id),
                               quantity_required numeric,
                               unit unit_type
);

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
    category ingredient_category

);
select DishIngredient.id_ingredient from    DishIngredient where id_dish = ?;

select id_dish from DishIngredient where id_ingredient = ?;
select dish.id, dish.name, dish.dish_type, dish.selling_price , ingredient.id, ingredient.name, ingredient.price,ingredient.category from DishIngredient join dish on dish.id = id_dish join ingredient on ingredient.id= id_ingredient  where dish.id = 1;

select id_ingredient,id_dish,dish.name,
       ingredient.id AS idIngredient, ingredient.name AS ingredient_name, ingredient.price,ingredient.category , dish.name AS dish_name
from DishIngredient
         join dish on dish.id = id_dish
         join ingredient on ingredient.id= id_ingredient
where ingredient.name ilike '%laitue%' or ingredient.category ='VEGETABLE' or dish.name ilike '%salade%' LIMIT 2 OFFSET 1;


create type mouvement_type as enum('IN','OUT');
Create table StockMovement (
    id serial primary key,
    id_ingredient int,
    quantity numeric,
    type mouvement_type,
    unit unit_type,
    creation_datetime timestamp
);
--- update numeric (10,2)
--- update timestamp zone

 select  * from stockmovement;
delete from stockmovement where type = 'OUT';
select * from  ingredient;


select StockMovement.quantity, StockMovement.type from StockMovement where creation_datetime = '2024-01-06 12:00';

CREATE TABLE if not exists "order"(
    id serial primary key ,
    reference_order varchar(8),
    creation_datetime timestamp
);
CREATE TABLE if NOT EXISTS DishOrder(
    id serial primary key ,
    id_order int references "order"(id),
    id_dish int references dish(id),
    quantity int
);
select id_dish,dish.id, dish.name, dish.dish_type, dish.selling_price  from DishIngredient join dish on dish.id = id_dish join ingredient on ingredient.id= id_ingredient  where dish.id = 99;
select dish.id, dish.name, dish.dish_type, dish.selling_price
from DishIngredient join dish on dish.id = DishIngredient.id_dish
                    join ingredient on ingredient.id= id_ingredient
where ingredient.name  ilike '%eur%';

select * from "order";
create type ordertype as enum('EAT_IN','TAKE_AWAY');
create type orderStatus AS enum('CREATED','DELIVERED','READY');

ALTER TABLE "order"
    ADD COLUMN order_type ordertype NOT NULL ,
    ADD COLUMN order_status orderStatus NOT NULL ;

ALTER TABLE "order"
    ALTER COLUMN order_type DROP NOT NULL;
ALTER TABLE "order"
    ALTER COLUMN order_status DROP NOT NULL;

ALTER TABLE "order"
    ALTER COLUMN reference_order TYPE VARCHAR(8);
DELETE FROM "order"
WHERE id = 14;

INSERT INTO "order" (id,reference_order,order_type,order_status,creation_datetime)values
                                                                                      (1,'ORD100','TAKE_AWAY','DELIVERED','2024-01-06 12:00'),
                                                                                      (2,'0RD102','EAT_IN','CREATED','2024-01-06 12:00');

INSERT INTO "order" (id,reference_order,order_type,order_status,creation_datetime)values (4,'ORD103','EAT_IN','CREATED','2024-01-06 12:00');
select * from "order";
delete from "order" where reference_order = '0RD102';

select "order".id, "order".reference_order, "order".creation_datetime, "order".order_status, "order".order_type from "order" ;
ALTER TABLE stockmovement
    ADD COLUMN commentaire text;

SELECT setval(
               pg_get_serial_sequence('stockmovement', 'id'),
               (SELECT MAX(id) FROM stockmovement)
       );

SELECT unit,
    COALESCE(SUM(
                     CASE
                         WHEN type = 'IN'  THEN quantity
                         WHEN type = 'OUT' THEN -quantity
                         END
             ), 0) AS actual_quantity

FROM StockMovement
WHERE   id_ingredient =1 and  creation_datetime <= now()
GROUP BY id_ingredient,unit;

SELECT sum(i.price ) as total_price from dishingredient join dish on dish.id = dishingredient.id_dish  join ingredient i on dishingredient.id_ingredient = i.id where dish.id =1;
SELECT d.selling_price - sum(i.price * di.quantity_required) as marge from DishIngredient di join dish d on d.id = di.id_dish join ingredient i on di.id_ingredient = i.id where id_dish = 1 group by d.selling_price ;
 select dish.selling_price from dish where id =1;
select i.price , di.quantity_required from dishingredient di join dish on dish.id = di.id_dish join ingredient i on di.id_ingredient = i.id where id_dish =1;
SELECT *
FROM "order"
WHERE creation_datetime BETWEEN '2024-01-01' AND '2024-01-31';
SELECT StockMovement.id_ingredient, StockMovement.quantity, StockMovement.type, StockMovement.unit, StockMovement.creation_datetime, StockMovement.commentaire
FROM stockmovement
WHERE creation_datetime BETWEEN '2024-01-01' AND '2024-01-31' group by StockMovement.id_ingredient;

select StockMovement.creation_datetime from  StockMovement ;
select StockMovement.quantity from stockmovement where id_ingredient= 1;
SELECT
    id_ingredient,

    SUM(CASE WHEN DATE(creation_datetime) = '2024-01-01'
                 THEN quantity ELSE 0 END) AS "01_01",

    SUM(CASE WHEN DATE(creation_datetime) = '2024-01-02'
                 THEN quantity ELSE 0 END) AS "02_01",

    SUM(CASE WHEN DATE(creation_datetime) = '2024-01-03'
                 THEN quantity ELSE 0 END) AS "03_01",

    SUM(CASE WHEN DATE(creation_datetime) = '2024-01-04'
                 THEN quantity ELSE 0 END) AS "04_01",

    SUM(CASE WHEN DATE(creation_datetime) = '2024-01-05'
                 THEN quantity ELSE 0 END) AS "05_01"

FROM stockmovement
WHERE creation_datetime BETWEEN '2024-01-01' AND '2024-01-31'
GROUP BY id_ingredient;




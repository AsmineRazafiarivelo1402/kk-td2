create type dish_type as enum ('STARTER', 'MAIN', 'DESSERT');

SELECT current_database(), current_user;
CREATE TYPE dish_type AS ENUM ('STARTER', 'MAIN', 'DESSERT');


CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO mini_dish_db_manager;
ALTER SCHEMA public OWNER TO mini_dish_db_manager;
SELECT nspname, pg_catalog.pg_get_userbyid(nspowner)
FROM pg_namespace
WHERE nspname = 'public';

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
    id_dish  int references dish (id)
);

alter table dish
    add column if not exists price numeric(10, 2);


alter table ingredient
    add column if not exists required_quantity numeric(10, 2);

-- -- //    List<Dish>
-- //    permettant de récupérer la liste des plats qui possède un nom des ingrédients
-- //    contenant la valeur du paramètre IngredientName fourni à travers la
-- //    métho

select dish.id, dish.name,dish.dish_type,dish.selling_price dish from dish;
select ingredient.id, ingredient.name, ingredient.price , ingredient.category from ingredient ;
select ingredient.id, ingredient.name, ingredient.price , ingredient.category from ingredient limit 3 offset 5;

INSERT INTO "PUBLIC"."USERS"( "ID", "EMAIL", "NAME", "PASSWORD" )
VALUES ( 1, 'email001@gmail.com', 'user01', 'password');

INSERT INTO "PUBLIC"."USERS"( "ID", "EMAIL", "NAME", "PASSWORD" )
VALUES ( 2, 'email002@gmail.com', 'user02', 'password');

INSERT INTO "PUBLIC"."USERS"( "ID", "EMAIL", "NAME", "PASSWORD" )
VALUES ( 3, 'email003@gmail.com', 'user03', 'password');


INSERT INTO "PUBLIC"."LISTINGS"
( "ID", "ADULTS", "BASE_PRICE", "CHILDREN", "CLEANING_FEE", "DESCRIPTION", "IMAGE_URL", "IS_PETS_ALLOWED", "MONTHLY_DISCOUNT", "NAME", "SLUG", "WEEKLY_DISCOUNT", "OWNER_ID" )
VALUES ( 1, 4, 10.2, 2, 0, 'Some Description 1', 'imageurl.com.ar', true, 0.23, 'Listing 01', 'SLUG', 0.1, 1);

INSERT INTO "PUBLIC"."LISTINGS"
( "ID", "ADULTS", "BASE_PRICE", "CHILDREN", "CLEANING_FEE", "DESCRIPTION", "IMAGE_URL", "IS_PETS_ALLOWED", "MONTHLY_DISCOUNT", "NAME", "SLUG", "WEEKLY_DISCOUNT", "OWNER_ID" )
VALUES ( 2, 3, 30.2, 2, 10, 'Some Description 2', 'imageurl.com.ar', false, 0.4, 'Listing 02', 'SLUG', 0.2, 1);

INSERT INTO "PUBLIC"."LISTINGS"
( "ID", "ADULTS", "BASE_PRICE", "CHILDREN", "CLEANING_FEE", "DESCRIPTION", "IMAGE_URL", "IS_PETS_ALLOWED", "MONTHLY_DISCOUNT", "NAME", "SLUG", "WEEKLY_DISCOUNT", "OWNER_ID" )
VALUES ( 3, 2, 5, 2, 0, 'Some Description 3', 'imageurl.com.ar', true, 0.11, 'Listing 03', 'SLUG', 0.15, 1);

INSERT INTO "PUBLIC"."LISTINGS"
( "ID", "ADULTS", "BASE_PRICE", "CHILDREN", "CLEANING_FEE", "DESCRIPTION", "IMAGE_URL", "IS_PETS_ALLOWED", "MONTHLY_DISCOUNT", "NAME", "SLUG", "WEEKLY_DISCOUNT", "OWNER_ID" )
VALUES ( 4, 1, 15, 0, 3, 'Some Description 4', 'imageurl.com.ar', false, 0.23, 'Listing 04', 'SLUG', 0.15, 2);

INSERT INTO "PUBLIC"."LISTINGS"
( "ID", "ADULTS", "BASE_PRICE", "CHILDREN", "CLEANING_FEE", "DESCRIPTION", "IMAGE_URL", "IS_PETS_ALLOWED", "MONTHLY_DISCOUNT", "NAME", "SLUG", "WEEKLY_DISCOUNT", "OWNER_ID" )
VALUES ( 5, 5, 20.2, 2, 0, 'Some Description 5', 'imageurl.com.ar', false, 0.3, 'Listing 05', 'SLUG', 0, 3);


INSERT INTO "PUBLIC"."SPECIAL_PRICES"( "ID", "DATE", "PRICE", "LISTING_ID" )
VALUES ( 1, null, 24, 1);

INSERT INTO "PUBLIC"."SPECIAL_PRICES"( "ID", "DATE", "PRICE", "LISTING_ID" )
VALUES ( 2, null, 33, 1);

INSERT INTO "PUBLIC"."SPECIAL_PRICES"( "ID", "DATE", "PRICE", "LISTING_ID" )
VALUES ( 3, null, 11, 2);
## listingTest API

API made in Java for manage listings.

#### IMPORTANT INFO

- Made in Java 8.
- In memory database (HyperSQL).
- Before/After each test clean up all database (DO NOT USE WITH MySQL or another NO in memory DB).
- Base path: IP:PORT/test/v1 (default: localhost:8080/test/v1).
- live try: https://weimer-listings.herokuapp.com/test/v1/ .
- Swagger path: localhost:8080/swagger-ui/index.html#/

#### ROUTES 
(More info about request/response body at swagger page).

HttpMethod | Path | Description |Response Object 
------------ | ------------- | ------------- | -----------
GET | test/v1/listings | Get All Listings | List`<ListingEntity>`
POST | test/v1/listings | Add a Listing | ListingEntity
GET | test/v1/listings/{:id} | Find Listing By Id | ListingEntity
PUT | test/v1/listings/{:id} | Update Listing By Id | ListingEntity
DELETE | test/v1/listings/{:id} | Delete Listing By Id | -
POST | test/v1/listings/{:id}/checkout | Calculate listing costs | CheckOutResponse
GET | test/v1/listings/{:id}/special-prices | Get All Special Prices by Listing ID | List`<SpecialPriceEntity>`
POST | test/v1/listings/{:id}/special-prices | Add a Special Price to listing | SpecialPriceEntity
DELETE | test/v1/listings/{id}/special-prices/{:spId} | Delete a SpecialPrice by spId | -

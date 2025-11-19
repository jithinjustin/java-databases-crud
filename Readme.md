
# Java Spring Boot project showing CRUD operations with relational Database
# some endpoints to try

## Get list of products
```
curl -X GET "http://localhost:8080/product"
```

## add a product
```
curl -X POST -H "Content-Type: application/json" -d '{"name":"iphone17","category":"Mobile",
 "price":"899.99","sku":"SKU101"}'  "http://localhost:8080/product"
```
## get product by id
```
curl  "http://localhost:8080/product/51"
```

## update product
```
curl -X PUT -H "Content-Type: application/json" -d '{"id":"51","name":"iphone17","category":"Mobile",
"price":"799.99","sku":"SKU101"}'  "http://localhost:8080/product"
```

## filter by product name and category
```
curl -X GET "http://localhost:8080/product/category/iphone/mobile"
```

## filter product by category and store id
```
curl -X GET "http://localhost:8080/product/filter/mobile/1"
```

## delete product
```
curl -X DELETE "http://localhost:8080/product/51"
```
## search by product name
```
curl -X GET "http://localhost:8080/product/searchProduct/iphone"
```
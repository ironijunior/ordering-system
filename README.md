# ordering-system

## How to run
```sh
mvn spring-boot:run
```

## API

The application consists of two APIs. One for Product management and the other for Ordering.

### Product API

####Creating a product

A product consists of Name, Description, Quantity and a Type.

The availables TYPES are: `TYPE_ONE` and `TYPE_TWO`.

The attribute `quantity` indicates the available stock quantity for the product. 

[POST] -> `localhost:8080/product`
```json
{
    "name": "Product Name",
    "description": "Product Description",
    "quantity": 20,
    "type": "TYPE_ONE"
}
```

####Removing a product

To remove a product just the id is required.

[DELETE] -> `localhost:8080/product/{id}`

####Getting a product

To get information of a product just the id is required.

[GET] -> `localhost:8080/product/{id}`

####Updating stock of a product

To update the stock quantity it's required to pass the product `id` in the path, and send the new quantity as request body.

[PUT] -> `localhost:8080/product/{id}`
```json
20
```
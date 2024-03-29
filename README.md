## Features

### Auth

- AppUser :

 ```java
class AppUser {
    private String id;
    private String username;
    private String password;
    private List<Role> roles;
}
  ```

- Role :

```java
public class Role {
    private String id;
    private String role; // Enum
}

enum ERole {
    ROLE_CUSTOMER,
    ROLE_ADMIN
}
```

### Catering

- Customer :

```java
class Customer {
    private String id;
    private String address;
    private String phone;
    private String name;
}
```

- Menu

```java
class Menu {
    private String id;
    private String name;
    private String description;
    private List<MenuPrice> menuPrices;
}
```

- Menu Price

```java
class MenuPrice {
    private String id;
    private Long price;
    private Integer stock;
    private Menu menu;
    private Boolean isActive;
}

- Order

```java
class Order {
    private String id;
    private LocalDateTime transDate;
    private InstalmentType instalmentType;
    private Customer customer;
    private List<OrderDetail> orderDetails;
}

- Order Detail

```java
class OrderDetail {
    private String id;
    private Order order;
    private MenuPrice menuPrice;
    private Integer quantity;
}

- Role

```java
class Role {
    private String id;
    private ERole name;
    private MenuPrice menuPrice;
}

- User Credential

```java
class UserCredential {
    private String id;
    private String password;
    private String username;
    private Role role;
}

## API Documentation

### Auth

#### Register

Request :

- Endpoint : ```/api/auth/register```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "username": "string",
  "password": "string"
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "username": "string",
    "role": [
      "admin",
      "staff"
    ]
  }
}
```

#### Login

Request :

- Endpoint : ```/api/auth/login```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "username": "string",
  "password": "string"
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "username": "string",
    "role": [
      "admin",
      "staff"
    ],
    "token": "string"
  }
}
```

### Customer

#### Get Customer By Id

Request

- Endpoint : ```/api/customer/{id}```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": {
    "id": "32bc3d44-faf9-46b7-87a1-8d617744aed9"
    "address": "string",
    "phone": "string",
    "name": "string"
  }
}
```

### Customer

#### Get All Customer By Name or Address

Request

- Endpoint : ```/api/customers/search```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": {
   "content": [
            {
                "id": "32bc3d44-faf9-46b7-87a1-8d617744aed9",
                "address": "Malang",
                "phone": "0821",
                "name": "danie"
            },
            {
                "id": "8ca130d5-c1d8-489e-a974-1cc49cc3340e",
                "address": "Malang",
                "phone": "0821",
                "name": "dane"
            }
        ]
  }
}
```

#### Update Customer

Request :

- Endpoint : ```/api/customers/update```
- Method : PUT
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "id": "32bc3d44-faf9-46b7-87a1-8d617744aed9"
  "address": "string",
  "phone": "string",
  "name": "string"
}
```

Response :

```json
{
  "message": "string",
  "data": {
  "id": "32bc3d44-faf9-46b7-87a1-8d617744aed9"
  "address": "null",
  "phone": "null",
  "name": "null"
  }
}
```

#### Delete Customer ```*Soft Delete```

Request :

- Endpoint : ```/api/customers/{id}```
- Method : DELETE
- Header :
    - Accept: application/json

Response :

```json
{
  "message": "string",
  "data": null
}
```

### Menu

#### Create Menu

Request :

- Authorize : `ADMIN ONLY`
- Endpoint : ```/api/menu```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "name": "string",
    "description": "string",
    "price": long,
    "stock": integer
}
```

Response :

```json
{
  "message": "string",
  "data": {
     "menuId": "5ded6eb8-7bec-4dd1-bf8d-7af23e35c68c",
        "name": "string",
        "description": "string",
        "price": long,
        "stock": integer
  }
}
```

#### Get All Menu By Name and Price

Request :

- Endpoint : ```/api/menu/search```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": {
   "content": [
            {
                "menuId": "1600cd22-2e64-4ff4-9bd7-9bfcb5d45495",
                "name": "Sapi Iga",
                "description": "Tulang summ-sum sapi",
                "price": 15000,
                "stock": 1
            }
        ]
  }
}
```

#### Get All Menu

Request :

- Endpoint : ```/api/menu```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": [
    {
        "id": "1f0b1f44-ae3b-4866-934c-80753601099e",
        "name": "Bebek Geprek",
        "description": "Bebek dengan sambel rica yang pedes",
        "menuPrices": [
            {
                "id": "8fb3842b-572a-4cb0-9dc9-364904e7ff60",
                "price": 15000,
                "stock": 3,
                "isActive": false
            }
        ]
    },
    {
        "id": "ed854aa4-cdad-4aba-8ce6-646e856a43f6",
        "name": "Ayam Lalapan",
        "description": "Ayam dengan Lalapan",
        "menuPrices": [
            {
                "id": "539615b0-bdeb-4cc7-8ebd-1f1d635e19b3",
                "price": 15000,
                "stock": 2,
                "isActive": false
            }
        ]
    },
    {
        "id": "1600cd22-2e64-4ff4-9bd7-9bfcb5d45495",
        "name": "Sapi Iga",
        "description": "Tulang summ-sum sapi",
        "menuPrices": [
            {
                "id": "2de32eec-13c8-4fdb-a7fb-c67eda352edb",
                "price": 15000,
                "stock": 1,
                "isActive": true
            }
        ]
    },
    {
        "id": "66ea85d0-e869-4f3a-bf6f-cae395209faf",
        "name": "Belut Geprek",
        "description": "Belut dengan sambel rica yang pedes",
        "menuPrices": [
            {
                "id": "57c3a1b3-5fe1-41bb-9c27-5cf0ff2b3474",
                "price": 15000,
                "stock": 9,
                "isActive": true
            }
        ]
    },
    {
        "id": "5ded6eb8-7bec-4dd1-bf8d-7af23e35c68c",
        "name": "Cireng",
        "description": "Cemilan",
        "menuPrices": [
            {
                "id": "e42d4cb8-fff6-4535-862b-f1aca789e891",
                "price": 10000,
                "stock": 5,
                "isActive": true
            }
        ]
    }
  ]
}
```

#### Update Menu

Request :

- Authorize : `ADMIN ONLY`
- Endpoint : ```/api/menu/update```
- Method : PUT
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "menuId": "XXXXX",
        "name": "String",
        "description": "String",
        "price": Long,
        "stock": Integer
}
```

Response :

```json
{
  "message": "string",
  "data": {
    "menuId": "66ea85d0-e869-4f3a-bf6f-cae395209faf",
        "name": "String",
        "description": "String",
        "price": Long,
        "stock": Integer
  }
}
```

#### Delete Menu

Request :

- Endpoint : ```/api/menu/delete/{id}```
- Method : DELETE
- Header :
    - Accept: application/json

Response :

```json
{
  "message": "string",
  "data": null
}
```
#### Get Menu By Id

Request

- Endpoint : ```/api/menu/{id}```
- Method : GET
    - Accept: application/json
- Response :

```json
{
  "message": "string",
  "data": {
     "menuId": "66ea85d0-e869-4f3a-bf6f-cae395209faf",
        "name": "String",
        "description": "String",
        "price": Long,
        "stock": Integer
  }
}

### Order

#### Create Order

Request :
- Endpoint : ```/api/order```
- Method : POST
- Header :
    - Content-Type: application/json
    - Accept: application/json
- Body :

```json
{
  "customerId": "32bc3d44-faf9-46b7-87a1-8d617744aed9",
    "orderDetail": [
        {
            "menuPriceId":"57c3a1b3-5fe1-41bb-9c27-5cf0ff2b3474",
            "quantity": 1
        }
    ]
}
```

Response :

```json
{
  "message": "string",
  "data": {
        "orderId": "63c90678-d042-4a1a-9c28-13f470a4f893",
        "transDate": "2024-03-29T18:44:15.4648475",
        "customerResponse": {
            "id": "32bc3d44-faf9-46b7-87a1-8d617744aed9",
            "address": "Malang",
            "phone": "0821",
            "name": "danie"
        },
        "orderDetails": [
            {
                "orderDetailId": null,
                "menuResponse": {
                    "menuId": "66ea85d0-e869-4f3a-bf6f-cae395209faf",
                    "name": "Belut belutan",
                    "description": "Belut dengan sambel rica yang aman",
                    "price": 12000,
                    "stock": 10
                },
                "quantity": 1
            }
        ]
    }
}
```

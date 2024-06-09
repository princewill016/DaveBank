---DaveBank(performs traditional banking operations)
This project is a Bank application that was built to simulate banking operations in the real world. in this application,
a bank account can be Created by a potential customer and have a unique bank account number assigned to the bank user.
Each bank user can perform three main bank operations which are Crediting of account, Withdrawal from account and
Transfer of funds to another account.

---Key Features
Account creation and Registration.
User can create a bank account by passing in relevant data through the CustomerDTO.

--Main Operations for Account
This Bank customer can Transfer funds from their account to that of a beneficiary.
This can customer can also withdraw from the account and the database is updated in real time.
The customer can also deposit money into the bank account and the database get updated in real time.

---Search Functionality
Search Bank details: Endpoint to search for Bank account name by Account number.
Bank user can also check his/her account balance

---Security
JWT Authentication: All contact management endpoints require a valid JWT for access.

---Technical Requirements
Backend Framework
Spring Boot: Backend framework for building robust and scalable RESTful APIs.
ORM & Database
Hibernate: Object-Relational Mapping (ORM) framework for interacting with the MySQL database.
Security
Redis:For caching to reduce the time it takes to fetch already loaded data.
Spring Security: Security framework for securing API endpoints and implementing JWT authentication.

---API Documentation
Swagger: API documentation tool for generating interactive API documentation.

---Getting Started
Clone the repository.
Set up MySQL database and configure database connection in application.properties.
Build and run the application.
Access API documentation using Swagger UI.

---API Endpoints

-----User Authentication

POST /BankOperations/createAccount: Creates bank for a Customer.

POST /BankOperations/creditAcc: Credits a bank account.

POST /BankOperations/debitAcc: Debits a bank account.

POST /BankOperations/transfer: Transfer from one account to another.

GET /BankOperations/{accountName}: Get the account name of the account number passed as a parameter.

GET /BankOperations/bal/{accountBalance}: To get account balance.

DELETE /BankOperations//deleteAccount/{id}: To close an account.

PUT /BankOperations/editAccount/{id}: To create an account.

---Technologies Used
Spring Boot
Hibernate
MySQL
Redis
Spring Security
JWT
Swagger

---Contributor
Peter David.
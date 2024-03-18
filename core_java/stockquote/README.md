# Introduction
This Stock Quote application is an app that allows users to buy and sell stocks through a CLI to 
simulate a stock wallet. Utilizing APIs to fetch real-time stock data, users can buy or sell 
accordingly based on the data retrieved. New data about stocks are updated in a PSQL database as
well as the users current stock wallet. This application leverages a combination of Java 8, JDBC,
PostgreSQL, Docker, Maven as well as JUnit and Mockito for testing.

# Implementaiton
## ER Diagram
![stock_quote_diagram](./assets/stock_quote_erd.png)

## Design Patterns
This application utilizes the Data Access Object (DAO) and Repository design patterns. The DAO
pattern separates the data access layer from the business layer, encapsulating the interaction
with the database within dedicated DAO classes. This helps to provide a clear separation of concerns
in the codebase. A DAO class provides methods to perform CRUD (Create, Read, Update, Delete) 
operations on a specific entity or set of entities. The Repository pattern encapsulates the logic 
for querying, storing, and retrieving entities and provides methods for common data access 
operations, typically CRUD. It abstracts the details of data access, allowing the business logic to
interact with data through a well-defined interface. By decoupling the business logic from the data
access logic, repositories make it easier to unit test the application components in isolation. 
Repositories also provide a flexible and adaptable way to manage data access, allowing developers to
switch between different data storage implementations without affecting the rest of the application.
A key difference between these two patterns is that the DAO pattern provides a lower-level 
abstraction focused on individual data access operations, while the Repository pattern provides a 
higher-level abstraction presenting a collection-like interface for querying and manipulating data.
DAO classes are responsible for handling CRUD operations for specific entities, while repositories 
encapsulate the logic for querying and manipulating data collections. Overall though both are 
valuable for abstracting the data access layer in software applications.

# Test
This application was thoroughly tested through the TDD method. Aspects of the application were
developed after creating various test cases that would help to model the actual application.
After developing the various services integration tests were made to bring together all the work
and test the application as a whole. During the development process, Mockito was employed to 
simulate incomplete portions of the application while ensuring that the necessary components 
were adequately tested. This allowed for the unit testing of crucial parts of the application
even when certain functionalities were still under development. Set up for these tests involved 
creating some test stock quote data, wallet data and also mocking calls to the API to reduce calls
and testing time.
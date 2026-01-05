## About project

This project is a coding task solution for Teya. This is simple API which should provide:
- Ability to record money movements (ie: deposits and withdrawals)
- View current balance
- View transaction history

## Getting Started

To run the code you should install everything from prerequisites and follow the installation instructions.

### Prerequisites

- java 21 ([installation instructions](https://docs.oracle.com/en/java/javase/21/install/overview-jdk-installation.html))
- maven 3 ([installation instructions](https://maven.apache.org/install.html))

### Installation

1. Clone the repo if the project is not on your PC yet
   ```sh
   git clone https://github.com/dmitrymironiyk/teya-task
   ```
2. Build project
   ```sh
   mvn clean install
   ```

## Usage

To run the app you can use this command
```sh
java -jar target/teya-0.0.1-SNAPSHOT.jar 
```
Now the app is running on your local machine on the 8080 port. 
To play with application you can use curl to make an api calls to the available endpoints. 
Here you can find couple examples.

Deposit endpoint example:
```sh
curl -X POST http://localhost:8080/api/v1/ledger/deposit \
     -H "Content-Type: application/json" \
     -d '{"value": 5}'
```

Withdraw endpoint example:
```sh
curl -X POST http://localhost:8080/api/v1/ledger/withdraw \
     -H "Content-Type: application/json" \
     -d '{"value": 4}'
```

History endpoint example:
```sh
curl http://localhost:8080/api/v1/ledger/history
```

Balance endpoint example:
```sh
curl http://localhost:8080/api/v1/ledger/balance
```

## Project assumptions

I did some assumptions about the project to make it simple enough to implement in reasonable time 
and fill some gaps in the project requirements
- System supports only one ledger and all users interact with this ledger.
- Requests to the system always coming one after another
- All data disappears after application shutdown
- Initial balance of the system is 0
- Balance of the system can`t be negative
- Operations with negative numbers are invalid
- It is important to store very precise

## Project decisions

- Use **BigDecimal** to store money data to be precise with balance
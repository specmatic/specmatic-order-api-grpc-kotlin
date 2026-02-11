# Specmatic Order API gRPC Sample

This sample project demonstrates [Specmatic](https://specmatic.io/) **gRPC support** which allows you to use your **proto files for Contract Testing**.
[Specmatic](https://specmatic.io/) **gRPC support** can also help you use your proto files for service mocking, #nocode backward compatibility testing and more.

The **specmatic-order-api-grpc-kotlin** is a gRPC server application developed according to the following proto files, which can be found in the central contract repository:

* [order.proto](https://github.com/specmatic/specmatic-order-contracts/blob/main/io/specmatic/examples/store/grpc/order_api/order.proto)
* [product.proto](https://github.com/specmatic/specmatic-order-contracts/blob/main/io/specmatic/examples/store/grpc/order_api/product.proto)

The `ContractTest` class uses our JUnit extension to demonstrate how to use Specmatic to test **specmatic-order-api-grpc-kotlin** gRPC server app using the above proto files.

If you want to use Docker via TestContainers then you can refer to the `ContractTestUsingTestContainer` class.

## Requirements

* Java 17 or later

## Project Setup

1. Clone the repository

   ```shell
   git clone https://github.com/specmatic/specmatic-order-api-grpc-kotlin
   ```

2. Initialize and update the `specmatic-order-contracts` submodule

   ```shell
   git submodule update --init --recursive --remote
   ```

3. Enable automatic submodule updating when executing `git pull`

   ```shell
   git config submodule.recurse true
   ```

4. Run the generateProto task to generate the classes from the proto files

   ```shell
   ./gradlew generateProto
   ```

## Running contract tests
To run contract tests,
   1. Using gradle - `./gradlew clean test`
   2. Using docker -
      - Run the application using `./gradlew bootRun`
      - Run the tests
        - On Unix and Windows Powershell:
        ```shell
        docker run --rm --network host -v "$(pwd):/usr/src/app" specmatic/enterprise test
        ```

        - On Windows CMD Prompt:
        ```shell
        docker run --rm --network host -v "%cd%:/usr/src/app" specmatic/enterprise test
           ```

## Running the application
In case you want to run just the gRPC server using Gradle you can execute

```shell
./gradlew bootRun
```


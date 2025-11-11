package com.store.specmatic_order_api_grpc

import io.specmatic.grpc.EXAMPLES_DIR
import io.specmatic.grpc.HOST
import io.specmatic.grpc.IMPORT_PATHS
import io.specmatic.grpc.PORT
import io.specmatic.grpc.junit.SpecmaticGrpcContractTest
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.context.SpringBootTest
import io.specmatic.grpc.PROTOC_VERSION
import io.specmatic.grpc.SPECMATIC_GENERATIVE_TESTS

@SpringBootTest
class ContractTest : SpecmaticGrpcContractTest {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            System.setProperty(HOST, "localhost")
            System.setProperty(PORT, "9090")
            System.setProperty(EXAMPLES_DIR, "src/test/resources/specmatic")
            System.setProperty(SPECMATIC_GENERATIVE_TESTS, "true")
            // This path is relative to the specified proto file in specmatic config file
            System.setProperty(IMPORT_PATHS, "../")
            System.setProperty(PROTOC_VERSION, "3.23.4")
        }
    }
}

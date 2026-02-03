package com.store.specmatic_order_api_grpc

import io.specmatic.enterprise.SpecmaticContractTest
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
class ContractTest : SpecmaticContractTest 

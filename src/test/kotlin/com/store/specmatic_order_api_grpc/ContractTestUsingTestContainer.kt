package com.store.specmatic_order_api_grpc

import io.specmatic.grpc.SPECMATIC_GENERATIVE_TESTS
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIf
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@EnabledIf(value = "isNonCIOrLinux", disabledReason = "Run only on Linux in CI; all platforms allowed locally")
class ContractTestUsingTestContainer {
    companion object {
        @JvmStatic
        fun isNonCIOrLinux(): Boolean = System.getenv("CI") != "true" || System.getProperty("os.name").lowercase().contains("linux")
    }

    private val testContainer: GenericContainer<*> =
        GenericContainer("specmatic/specmatic-grpc")
            .withCommand(
                "test",
                "--host=localhost",
                "--port=9090",
                "--examples=./src/test/resources/specmatic",
                "--protoc-version=3.23.4",
            ).withEnv(SPECMATIC_GENERATIVE_TESTS, "true")
            .withFileSystemBind(
                "./src/test/resources/specmatic",
                "/usr/src/app/src/test/resources/specmatic",
                BindMode.READ_ONLY,
            ).withFileSystemBind(
                "./specmatic.yaml",
                "/usr/src/app/specmatic.yaml",
                BindMode.READ_ONLY,
            ).withFileSystemBind(
                "./build/reports/specmatic",
                "/usr/src/app/build/reports/specmatic",
                BindMode.READ_WRITE,
            ).waitingFor(Wait.forLogMessage(".*Failed Tests.*", 1))
            .withNetworkMode("host")
            .withLogConsumer { print(it.utf8String) }

    @Test
    fun specmaticContractTest() {
        testContainer.start()
        val hasSucceeded = testContainer.logs.contains("Result: FAILED").not()
        assertThat(hasSucceeded).isTrue()
    }
}

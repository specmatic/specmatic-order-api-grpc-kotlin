package com.store.specmatic_order_api_grpc

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
        fun isNonCIOrLinux(): Boolean =
            System.getenv("CI") != "true" || System.getProperty("os.name").lowercase().contains("linux")
    }

    private val testContainer: GenericContainer<*> =
        GenericContainer("specmatic/enterprise")
            .withCommand("test")
            .withFileSystemBind("./src", "/usr/src/app/src", BindMode.READ_ONLY)
            .withFileSystemBind("./specmatic.yaml", "/usr/src/app/specmatic.yaml", BindMode.READ_ONLY)
            .withNetworkMode("host")
            .waitingFor(Wait.forLogMessage(".*Tests run:.*", 1))
            .withLogConsumer { print(it.utf8String) }

    @Test
    fun specmaticContractTest() {
        testContainer.start()
        val hasSucceeded = testContainer.logs.contains("Result: FAILED").not()
        assertThat(hasSucceeded).isTrue()
    }
}

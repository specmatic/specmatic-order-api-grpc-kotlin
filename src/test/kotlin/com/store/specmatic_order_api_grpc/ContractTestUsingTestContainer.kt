package com.store.specmatic_order_api_grpc

import io.specmatic.grpc.SPECMATIC_GENERATIVE_TESTS
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIf
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitStrategy
import org.testcontainers.images.ImagePullPolicy
import org.testcontainers.images.PullPolicy
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
        GenericContainer("specmatic/enterprise")
            .withImagePullPolicy(PullPolicy.alwaysPull())
            .withCommand(
                "test",
                "--host=localhost",
                "--port=9090",
                "--examples=src/test/resources/specmatic",
                "--protoc-version=3.23.4",
                "--import-path=../"
            ).withEnv(SPECMATIC_GENERATIVE_TESTS, "true")
            .withFileSystemBind("./", "/usr/src/app", BindMode.READ_WRITE)
            .waitingFor(Wait.forLogMessage(".*Generating CTRF report.*", 1))
            .withNetworkMode("host")
            .withLogConsumer { print(it.utf8String) }

    @Test
    fun specmaticContractTest() {
        testContainer.start()
        assertThat(testContainer.logs).doesNotContain("Result: FAILED")
    }
}

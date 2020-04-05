package com.example.camelsample

import com.example.camelsample.ProjectConfig.Companion.testSupport
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.data.forAll
import io.kotest.data.row
import org.apache.camel.builder.AdviceWithRouteBuilder
import org.apache.camel.model.ModelCamelContext
import org.apache.camel.reifier.RouteReifier
import org.springframework.test.context.ContextConfiguration
import java.lang.StrictMath.random

@ContextConfiguration
class CloseToRealWorldRouteTest() : StringSpec() {

	init {
		"it should process product to the right frontend"() {
			forAll(
					row("AAAAAAA", "SHOP", 0, 1),
					row("BBBBBBB", "LOUNGE", 1, 0)
			) { sku, frontend, expDynamoLoungeCount, expSqsShopCount ->

				testSupport.context().start()
				val dynamoTable = "mock:aws-ddb:lounge-items".asMockEndpoint()
				dynamoTable.expectedMessageCount(expDynamoLoungeCount)

				val shopQueue = "mock:aws-sqs:shop-items".asMockEndpoint()
				shopQueue.expectedMessageCount(expSqsShopCount)

				testSupport.template().sendBody("direct:start",
						"""{ "id": "${random().toInt()}", "sku": "$sku", "frontend":"$frontend" }""")

				testSupport.assertSatisfied()
				testSupport.context().stop()
			}
		}
	}

	override fun beforeSpec(spec: Spec) {
		val mcc = testSupport.context().adapt(ModelCamelContext::class.java)
		val routeDef = mcc.routeDefinitions.first { it.id == "CloseToRealWorld" }
		RouteReifier.adviceWith(routeDef, testSupport.context(), object : AdviceWithRouteBuilder() {
			override fun configure() {
				mockEndpoints()
				replaceFromWith("direct:start")
			}
		})
	}
}

fun String.asMockEndpoint() = testSupport.useMock(this)

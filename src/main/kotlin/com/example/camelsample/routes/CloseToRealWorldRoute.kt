package com.example.camelsample.routes

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import org.apache.camel.Processor
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary
import org.springframework.stereotype.Component


@Component
class CloseToRealWorldRoute : RouteBuilder() {
    val dynamoAdapterProcessor = Processor {
        val body = it.message.body as Map<*, *>
        val newBody: MutableMap<String?, AttributeValue?> = mutableMapOf()
        for (key in body.keys) {
            newBody[key.toString()] = AttributeValue(body[key].toString())
        }
        it.message.setHeader("CamelAwsDdbItem", newBody)
    }

    override fun configure() {
        from("aws-sqs://products")
                .id("CloseToRealWorld")
                .choice()
                    .`when`(jsonpath("$..[?(@.frontend == 'LOUNGE')]"))
                        .unmarshal().json(JsonLibrary.Jackson, Map::class.java)
                        .process(dynamoAdapterProcessor)
                        .to("aws-ddb://lounge-items?operation=PutItem")
                    .otherwise()
                        .to("aws-sqs://shop-items")
    }
}
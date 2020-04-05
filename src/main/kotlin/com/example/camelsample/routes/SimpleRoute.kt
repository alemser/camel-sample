package com.example.camelsample.routes

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

@Component
class SimpleRoute : RouteBuilder() {

    override fun configure() {
        from("aws-sqs://helloq")
            .transform(body().prepend("Hello "))
            .log(body().toString())
    }
}
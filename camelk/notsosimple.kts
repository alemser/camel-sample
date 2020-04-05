import com.amazonaws.services.dynamodbv2.model.AttributeValue
import org.apache.camel.Processor
import org.apache.camel.model.dataformat.JsonLibrary

val dynamoAdapterProcessor = Processor { it ->
    val body = it.message.body as Map<*, *>
    val newBody: MutableMap<String?, AttributeValue?> = mutableMapOf()
    for (key in body.keys) {
        newBody[key.toString()] = AttributeValue(body[key].toString())
    }
    it.message.setHeader("CamelAwsDdbItem", newBody)
}

from("aws-sqs://camel-sample-1")
        .choice()
            .`when`(jsonpath("$..[?(@.frontend == 'LOUNGE')]"))
            .unmarshal().json(JsonLibrary.Jackson, Map::class.java)
            .process(dynamoAdapterProcessor)
            .to("aws-ddb://users?operation=PutItem")
        .otherwise()
            .to("aws-sqs://shop-items")
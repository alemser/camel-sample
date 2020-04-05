from("aws-sqs://dummy")
    .transform(body().prepend("Hello "))
    .log(text)
    .log("\${body}")

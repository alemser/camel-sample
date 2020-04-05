from("aws-sqs://helloq")
    .transform(body().prepend("Hello "))
    .log(text)
    .log("\${body}")

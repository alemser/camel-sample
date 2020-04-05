# Camel and Camel K Sample using AWS SQS and DynamoDB resources

### necessary camel k config maps 
In the camelk folder, create the following files:

simple-config.yaml
```
apiVersion: v1
kind: ConfigMap
metadata:
  name: simple-config
data:
  application.properties: |
    camel.component.aws-sqs.access-key=<SQS_ACCESS_KEY>
    camel.component.aws-sqs.secret-key=<SQS_SECRET>
    camel.component.aws-sqs.region=<SQS_REGION>
    logging.level.org.apache.camel=INFO
```

notsosimple-config.yaml
```
apiVersion: v1
kind: ConfigMap
metadata:
  name: notsosimple-config
data:
  application.properties: |
    camel.component.aws-sqs.access-key=<SQS_ACCESS_KEY>
    camel.component.aws-sqs.secret-key=<SQS_SECRET>
    camel.component.aws-sqs.region=<SQS_REGION>
    camel.component.aws-ddb.access-key=<DDB_ACCESS_KEY>
    camel.component.aws-ddb.secret-key=<DDB_SECRET>
    camel.component.aws-ddb.region=<DDB_REGION>
    logging.level.org.apache.camel=INFO
```

### necessary camel application.yaml
Create the following file in the src/main/resources folder

```
camel:
  component:
    aws-sqs:
      access-key: <SQS_ACCESS_KEY>
      secret-key: <SQS_SECRET>
      region: <SQS_REGION>
    aws-ddb:
      access-key: <DDB_ACCESS_KEY>
      secret-key: <DDB_SECRET>
      region: <DDB_REGION>
```



+-----------------+-------+<br>
| Kafka REST Port | 8082  |<br>
| Plaintext Ports | 53124 |<br>
+-----------------+-------+<br>


Start the broker:<br>
```confluent local kafka start```

Create a kafka topic if it doesn't exist:<br>
```confluent local kafka topic create purchases```


Compile the producer:<br>
```go build -o out/producer cmd/producer/producer.go```

Compile the consumer:<br>
```go build -o out/consumer cmd/consumer/consumer.go```


Execute the compiled producer:<br>
```./out/producer```

Execute the compiled consumer:<br>
```./out/consumer```

Shut down kafka once you are done:<br>
```confluent local kafka stop```

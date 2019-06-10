## Kafka Streams Aggregator
It is a simple project to practice in Kafka Streams.

The project follows next flow:
*   `App` service listens GET request
*   Controller produce message to topic
*   `Dictionary` service consume topic and send another to active services(user,order,delivery)
*   Every active service consumes message, processed and produces the answer
*   `Dictionary` consumes answers. If every active service answered with `completed` status `Dictionary` aggregates the messages and produces message to `App`.
*   `App` service receives(consumes) topic and answer for GET request

### Before you start

* Docker
* Confluence

### How to run
TBD
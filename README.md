## Kafka Streams Aggregator
It is a simple project to practice in Kafka Streams.

The project follows next flow:
*   `App` service listens GET request
*   Controller produce message to topic
*   `Dictionary` service consume topic and send another to active services:
    * `User` holds information about user (name,address, contacts, etc.).
    * `Order` stores every active and received order for user.
    * `Delivery` keeps delivery data for existing orders. Could be in process, delivered.
*   Every active service consumes message, processed and produces the answer
*   `Dictionary` consumes answers. If every active service answered with `completed` status `Dictionary` aggregates the messages and produces message to `App`.
*   `App` service receives(consumes) topic and answer for GET request

### Before you start

* Docker
* Confluent [quick start guide](https://docs.confluent.io/current/quickstart/cos-docker-quickstart.html#cos-docker-quickstart)

### How to run

* Run brokers and zookeeper:
    ```
    docker-compose up -d --build
    ```
* TBD

...
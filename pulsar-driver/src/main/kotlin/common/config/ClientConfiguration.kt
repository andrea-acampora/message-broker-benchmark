package common.config

import org.apache.pulsar.common.naming.TopicDomain

/**
 * The configuration file section for the Pulsar Client.
 * - [serviceUrl]: the URL of the Broker.
 * - [httpUrl]: the URL for the HTTP protocol.
 * - [ioThreads]: the number of io thread, default to 8.
 * - [connectionsPerBroker]: the number of connections per broker, default to 8.
 * - [maxConcurrentLookupRequests]: the maximum concurrent lookup requests.
 * - [clusterName]: the name of the topic.
 * - [topicType]: the type of the topic, default to persistent mode.
*/
data class ClientConfiguration(
    val serviceUrl: String,
    val httpUrl: String,
    val ioThreads: Int = 8,
    val connectionsPerBroker: Int = 8,
    val maxConcurrentLookupRequests: Int = 1000,
    val clusterName: String,
    val topicType: TopicDomain = TopicDomain.persistent,
)

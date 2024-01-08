package common.config

import org.apache.pulsar.common.naming.TopicDomain

data class ClientConfiguration(
    val serviceUrl: String,
    val httpUrl: String,
    val ioThreads: Int = 8,
    val connectionsPerBroker: Int = 8,
    val maxConcurrentLookupRequests: Int = 1000,
    val clusterName: String,
    val topicType: TopicDomain = TopicDomain.persistent,
)

package common.config

import org.apache.pulsar.client.api.SubscriptionType

/**
 * The configuration file section for the Pulsar Consumer.
 * - [receiverQueueSize]: the size of the queue.
 * - [subscriptionType]: the type of the subscription, default to FailOver.
 * - [subscriptionName]: the name of the subscription.
 */
data class ConsumerConfiguration(
    val receiverQueueSize: Int = 10000,
    val subscriptionType: SubscriptionType = SubscriptionType.Failover,
    val subscriptionName: String,
)

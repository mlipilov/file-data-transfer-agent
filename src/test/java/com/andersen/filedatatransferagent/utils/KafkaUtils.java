package com.andersen.filedatatransferagent.utils;

import java.util.Map;
import java.util.Properties;
import lombok.experimental.UtilityClass;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;

/**
 * This class provides utility methods for working with Apache Kafka.
 */
@UtilityClass
public class KafkaUtils {

  /**
   * Retrieves consumer properties for Kafka based on the provided KafkaProperties object.
   *
   * @param kafkaProperties The KafkaProperties object containing the configuration for Kafka.
   * @return The consumer properties for the Kafka consumer.
   */
  public static Properties getConsumerProperties(final KafkaProperties kafkaProperties) {
    final var sslBundles = new DefaultSslBundleRegistry();
    final var consumerProps = kafkaProperties.buildConsumerProperties(sslBundles);
    final var properties = new Properties();
    properties.putAll(consumerProps);
    return properties;
  }

  /**
   * Retrieves producer properties for Kafka based on the provided KafkaProperties object.
   *
   * @param kafkaProperties The KafkaProperties object containing the configuration for Kafka.
   * @return A map containing the producer properties for Kafka.
   */
  public static Map<String, Object> getProducerProperties(final KafkaProperties kafkaProperties) {
    final var sslBundles = new DefaultSslBundleRegistry();
    return kafkaProperties.buildProducerProperties(sslBundles);
  }
}

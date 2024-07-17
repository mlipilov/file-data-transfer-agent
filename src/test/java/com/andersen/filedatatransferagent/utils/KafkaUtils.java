package com.andersen.filedatatransferagent.utils;

import java.util.Properties;
import lombok.experimental.UtilityClass;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;

@UtilityClass
public class KafkaUtils {

  public static Properties getConsumerProperties(final KafkaProperties kafkaProperties) {
    final var sslBundles = new DefaultSslBundleRegistry();
    final var consumerProps = kafkaProperties.buildConsumerProperties(sslBundles);
    final var properties = new Properties();
    properties.putAll(consumerProps);
    return properties;
  }
}

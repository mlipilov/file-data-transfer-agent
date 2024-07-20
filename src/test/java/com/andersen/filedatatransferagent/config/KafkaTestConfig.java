package com.andersen.filedatatransferagent.config;

import static com.andersen.filedatatransferagent.utils.KafkaUtils.getProducerProperties;

import com.andersen.filedatatransferagent.model.user.User;
import java.util.UUID;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@TestConfiguration
public class KafkaTestConfig {

  @Bean
  public ProducerFactory<String, User> producerFactory(final KafkaProperties kafkaProperties) {
    final var producerProperties = getProducerProperties(kafkaProperties);
    final var producerFactory = new DefaultKafkaProducerFactory<String, User>(producerProperties);
    producerFactory.setTransactionIdPrefix("tx-" + UUID.randomUUID());
    return producerFactory;
  }

  @Bean
  @Primary
  public KafkaTemplate<String, User> kafkaTemplate(
      final ProducerFactory<String, User> producerFactory
  ) {
    return new KafkaTemplate<>(producerFactory);
  }
}

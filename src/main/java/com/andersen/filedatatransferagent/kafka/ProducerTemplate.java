package com.andersen.filedatatransferagent.kafka;

import static java.util.Objects.nonNull;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.support.SendResult;

/**
 * This class is used to construct producers
 *
 * @param <M> type of the DTO we send to the Kafka
 */
@Slf4j
public abstract class ProducerTemplate<M> {

  public void log(
      final M message,
      final SendResult<String, M> result,
      final Throwable ex) {
    if (nonNull(ex)) {
      log.error("Can't send message: {} to the topic: {} with key: {}, with headers: {}",
          message,
          result.getProducerRecord().topic(),
          result.getProducerRecord().key(),
          result.getProducerRecord().headers(),
          ex
      );
    } else {
      log.info(
          "Message produced successfully! Sent to the topic: {}, to the partition: {}, headers: {}",
          result.getRecordMetadata().topic(),
          result.getRecordMetadata().partition(),
          result.getProducerRecord().headers()
      );
    }
  }

  public ProducerRecord<String, M> getProducerRecord(
      final M message,
      final String topic
  ) {
    final Header header = new RecordHeader("", null);

    return new ProducerRecord<String, M>(
        topic,
        null,
        null,
        message,
        List.of(header)
    );
  }
}

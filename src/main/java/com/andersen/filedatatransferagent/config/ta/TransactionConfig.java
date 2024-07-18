package com.andersen.filedatatransferagent.config.ta;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionConfig {

  @Primary
  @Bean
  public PlatformTransactionManager transactionManager(
      final DataSource dataSource
  ) {
    final JdbcTransactionManager transactionManager = new JdbcTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }
}

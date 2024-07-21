package com.andersen.filedatatransferagent.config.ta;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * This class is a configuration class for managing transactions in the application.
 * It provides a bean definition for the transaction manager using the given data source.
 */
@Configuration
public class TransactionConfig {

  /**
   * This method creates and configures a transaction manager for managing transactions in the application.
   *
   * @param dataSource the data source used for the transaction manager
   * @return the transaction manager for*/
  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(
      final DataSource dataSource
  ) {
    final JdbcTransactionManager transactionManager = new JdbcTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }
}

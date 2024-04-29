package com.andersen.filedatatransferagent.service.impl;

import static com.andersen.filedatatransferagent.model.OperationType.READ;

import com.andersen.filedatatransferagent.service.UserCsvWriteErrorCollector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCsvWriteErrorCollectorImpl implements UserCsvWriteErrorCollector {

  private static final int TIMESTAMP_INDEX = 1;
  private static final int ERR_MSG_INDEX = 2;
  private static final int OPERATION_INDEX = 3;

  private static final String SAVE_ERR_SQL = """
      insert into
      user_csv_transfer_errors('occurred_at', 'error_description', 'operation-type')
      values (?, ?, ?)
      """;

  private final JdbcTemplate jdbcTemplate;

  @Override
  @Transactional
  public void collect(final Exception exception) {
    final PreparedStatementCreator creator = conn -> getPreparedStatement(exception, conn);
    final PreparedStatementCallback<Boolean> callback = PreparedStatement::execute;
    jdbcTemplate.execute(creator, callback);
  }

  private PreparedStatement getPreparedStatement(
      final Exception exception,
      final Connection psc
  ) throws SQLException {
    final PreparedStatement preparedStatement = psc.prepareStatement(SAVE_ERR_SQL);
    preparedStatement.setTimestamp(TIMESTAMP_INDEX, new Timestamp(System.currentTimeMillis()));
    preparedStatement.setString(ERR_MSG_INDEX, exception.getMessage());
    preparedStatement.setString(OPERATION_INDEX, READ.name());
    return preparedStatement;
  }
}

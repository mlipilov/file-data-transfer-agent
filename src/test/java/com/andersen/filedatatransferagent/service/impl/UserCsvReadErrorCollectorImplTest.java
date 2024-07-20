package com.andersen.filedatatransferagent.service.impl;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;

@ExtendWith(MockitoExtension.class)
class UserCsvReadErrorCollectorImplTest {

  @Mock
  private JdbcTemplate jdbcTemplate;
  @InjectMocks
  private UserCsvReadErrorCollectorImpl userCsvReadErrorCollectorImpl;

  @Test
  void givenNotFlatFileParseEx_whenCollectErrorData_ThenDoNothing() {
    //GIVEN
    final IllegalArgumentException ex = new IllegalArgumentException();

    //WHEN
    userCsvReadErrorCollectorImpl.collect(ex);

    //THEN
    verify(jdbcTemplate, never()).execute(
        any(PreparedStatementCreator.class),
        any(PreparedStatementCallback.class));
  }

  @Test
  void givenFlatFileParseEx_whenCollectErrorData_ThenCollect() {
    //GIVEN
    final FlatFileParseException parseEx = new FlatFileParseException(EMPTY, EMPTY, INTEGER_ONE);

    //WHEN
    userCsvReadErrorCollectorImpl.collect(parseEx);

    //THEN
    verify(jdbcTemplate, times(INTEGER_ONE)).execute(
        any(PreparedStatementCreator.class),
        any(PreparedStatementCallback.class));
  }
}
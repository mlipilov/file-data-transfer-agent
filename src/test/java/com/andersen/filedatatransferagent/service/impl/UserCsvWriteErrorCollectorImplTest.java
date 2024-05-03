package com.andersen.filedatatransferagent.service.impl;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;

@ExtendWith(MockitoExtension.class)
class UserCsvWriteErrorCollectorImplTest {

  @Mock
  private JdbcTemplate jdbcTemplate;
  @InjectMocks
  private UserCsvWriteErrorCollectorImpl userCsvWriteErrorCollectorImpl;

  @Test
  void givenAnyEx_WhenCollect_ThenCollectSuccessfully() {
    //GIVEN
    final IllegalArgumentException ex = new IllegalArgumentException();

    //WHEN
    userCsvWriteErrorCollectorImpl.collect(ex);

    //THEN
    verify(jdbcTemplate, times(INTEGER_ONE)).execute(
        any(PreparedStatementCreator.class),
        any(PreparedStatementCallback.class));
  }
}
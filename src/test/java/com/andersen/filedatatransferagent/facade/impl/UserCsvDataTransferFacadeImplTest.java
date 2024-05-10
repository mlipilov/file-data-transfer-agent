package com.andersen.filedatatransferagent.facade.impl;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class UserCsvDataTransferFacadeImplTest {

  @Mock
  private JobLauncher jobLauncher;
  @Mock
  private Job userJob;
  @InjectMocks
  private UserCsvDataTransferFacadeImpl userCsvDataTransferFacade;

  @Test
  @SneakyThrows
  void givenFile_whenLaunchJob_ThenSuccessfullyLaunchJob() {
    //GIVEN
    final MultipartFile multipartFile = new MockMultipartFile("file", "hello".getBytes());

    //WHEN
    userCsvDataTransferFacade.transferUserCsvData(multipartFile);

    //THEN
    verify(jobLauncher, times(INTEGER_ONE)).run(eq(userJob), any(JobParameters.class));
  }
}
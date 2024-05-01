package com.andersen.filedatatransferagent.facade.impl;

import static com.andersen.filedatatransferagent.utils.BatchUtils.runJobSafe;

import com.andersen.filedatatransferagent.facade.UserCsvDataTransferFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCsvDataTransferFacadeImpl implements UserCsvDataTransferFacade {

  private static final String USER_CSV_DATA_RESOURCE = "userCsvData";

  private final JobLauncher jobLauncher;
  private final Job userJob;

  @Async
  @Override
  public void transferUserCsvData(final MultipartFile csvData) {
    JobParameters jobParameters = toJobParameters(csvData);
    runJobSafe(jobLauncher, userJob, jobParameters);
  }

  private JobParameters toJobParameters(final MultipartFile csvFile) {
    final JobParametersBuilder builder = new JobParametersBuilder();
    builder.addJobParameter(USER_CSV_DATA_RESOURCE, csvFile.getResource(), Resource.class);
    return builder.toJobParameters();
  }
}

package com.andersen.filedatatransferagent.facade.impl;

import static com.andersen.filedatatransferagent.utils.BatchUtils.runJobSafe;

import com.andersen.filedatatransferagent.facade.UserCsvDataTransferFacade;
import java.util.List;
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

  private static final String FIRST_JOB_PARAM_KEY = "A";
  private static final String SECOND_JOB_PARAM_KEY = "B";

  private static final String USER_CSV_FILENAME_PART = "users";

  private final JobLauncher jobLauncher;
  private final Job userJob;

  @Async
  @Override
  public void transferUserCsvData(final List<MultipartFile> csvData) {
    csvData.stream()
        .map(this::toJobParameters)
        .forEach(jobParam -> runJobSafe(jobLauncher, userJob, jobParam));
  }

  private JobParameters toJobParameters(final MultipartFile csvFile) {
    final String jobParamKey = csvFile.getName().contains(USER_CSV_FILENAME_PART)
        ? FIRST_JOB_PARAM_KEY
        : SECOND_JOB_PARAM_KEY;
    final JobParametersBuilder builder = new JobParametersBuilder();
    builder.addJobParameter(jobParamKey, csvFile.getResource(), Resource.class);
    return builder.toJobParameters();
  }
}

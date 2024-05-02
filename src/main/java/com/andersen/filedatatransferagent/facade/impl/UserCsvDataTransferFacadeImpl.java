package com.andersen.filedatatransferagent.facade.impl;

import static com.andersen.filedatatransferagent.utils.BatchUtils.runJobSafe;
import static com.andersen.filedatatransferagent.utils.FileUtils.createTmpFile;
import static com.andersen.filedatatransferagent.utils.FileUtils.transferResourceData;

import com.andersen.filedatatransferagent.facade.UserCsvDataTransferFacade;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCsvDataTransferFacadeImpl implements UserCsvDataTransferFacade {

  private final JobLauncher jobLauncher;
  private final Job userJob;

  @Override
  public void transferUserCsvData(final MultipartFile csvData) {
    final Path tmpFile = createTmpFile();
    transferResourceData(csvData, tmpFile);
    final JobParameters jobParameters = toJobParameters(csvData, tmpFile);
    runJobSafe(jobLauncher, userJob, jobParameters);
  }

  private JobParameters toJobParameters(final MultipartFile csvFile, final Path tmpFile) {
    final JobParametersBuilder builder = new JobParametersBuilder();
    builder.addJobParameter(csvFile.getName(), tmpFile.toUri().getPath(), String.class);
    return builder.toJobParameters();
  }
}

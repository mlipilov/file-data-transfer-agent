package com.andersen.filedatatransferagent.facade.impl;

import static com.andersen.filedatatransferagent.utils.BatchUtils.runJobSafe;
import static com.andersen.filedatatransferagent.utils.FileUtils.copy;
import static com.andersen.filedatatransferagent.utils.FileUtils.createTmpFile;
import static java.util.concurrent.CompletableFuture.runAsync;

import com.andersen.filedatatransferagent.facade.UserCsvDataTransferFacade;
import com.andersen.filedatatransferagent.validator.UserCsvDataValidator;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Function;
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

  private final UserCsvDataValidator userCsvDataValidator;
  private final JobLauncher jobLauncher;
  private final Job userJob;

  @Override
  public void transferUserCsvData(final MultipartFile csvData) {
    log.info("Started transferring user csv data");
    final Path tmpFile = createTmpFile();
    copy(csvData, tmpFile);
    userCsvDataValidator.validate(tmpFile);
    final JobParameters jobParameters = toJobParameters(csvData, tmpFile);
    runAsync(() -> runJobSafe(jobLauncher, userJob, jobParameters))
        .exceptionally(logEx())
        .whenComplete(logJobIsFinished());
  }

  private JobParameters toJobParameters(final MultipartFile csvFile, final Path tmpFile) {
    final JobParametersBuilder builder = new JobParametersBuilder();
    builder.addJobParameter(csvFile.getName(), tmpFile.toUri().getPath(), String.class);
    return builder.toJobParameters();
  }

  private Function<Throwable, Void> logEx() {
    return err -> {
      log.error(err.getMessage(), err);
      return null;
    };
  }

  private BiConsumer<? super Void, ? super Throwable> logJobIsFinished() {
    return (res, ex) -> log.info("Finished transferring user csv data");
  }
}

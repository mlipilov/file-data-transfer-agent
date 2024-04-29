package com.andersen.filedatatransferagent.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

@Slf4j
@UtilityClass
public class BatchUtils {

  public static void runJobSafe(
      final JobLauncher jobLauncher,
      final Job job,
      final JobParameters jobParameters
  ) {
    try {
      jobLauncher.run(job, jobParameters);
    } catch (Throwable ex) {
      log.error(ex.getMessage(), ex);
      throw new RuntimeException(ex);
    }
  }
}

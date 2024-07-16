package com.andersen.filedatatransferagent.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

/**
 * Utility class for batch processing operations.
 */
@Slf4j
@UtilityClass
public class BatchUtils {

  /**
   * Executes a job using a JobLauncher, catching any Throwable exceptions that may occur and logging them before rethrowing as a RuntimeException.
   *
   * @param jobLauncher The JobLauncher to use for running the job
   * @param job The Job to be executed
   * @param jobParameters The JobParameters to pass to the job
   */
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

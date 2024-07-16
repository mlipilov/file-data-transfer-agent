package com.andersen.filedatatransferagent.config.batch;

import static com.andersen.filedatatransferagent.constants.UserCsvConstants.FILE_NAME;
import static com.andersen.filedatatransferagent.utils.FileUtils.delete;

import java.nio.file.Paths;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * UserCsvTransferJobExecutionListener is a class that implements the JobExecutionListener interface. It provides
 * functionality for logging job execution events and deleting a file after the job is finished.
 */
@Slf4j
@Component
public class UserCsvTransferJobExecutionListener implements JobExecutionListener {

  /**
   * Method that is called before the start of a job execution.
   * Logs a message indicating the start of the job.
   *
   * @param jobExecution The Job*/
  @Override
  public void beforeJob(@NonNull final JobExecution jobExecution) {
    log.info("Started {} job", jobExecution.getJobInstance().getJobName());
  }

  /**
   * Deletes a file after the job is finished.
   * <p>
   * This method is called automatically at the end of a job execution. It takes the {@link JobExecution} object as a parameter
   * and performs the following actions:
   * <ul>
   *   <li>Logs a message indicating the completion of the job execution.</li>
   *   <li>Extracts the file path that was stored as a parameter in the job execution parameters.</li>
   *   <li>Deletes the file located at the specified path.</li>
   * </ul>
   * </p>
   *
   * @param jobExecution The JobExecution object representing the current job execution.
   * @throws RuntimeException if an error occurs while deleting the file.
   */
  @Override
  public void afterJob(@NonNull final JobExecution jobExecution) {
    log.info("Finished {} job", jobExecution.getJobInstance().getJobName());
    final String path = (String) jobExecution.getJobParameters()
        .getParameters()
        .get(FILE_NAME)
        .getValue();
    delete(Paths.get(path));
  }
}

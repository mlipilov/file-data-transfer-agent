package com.andersen.filedatatransferagent.config;

import com.andersen.filedatatransferagent.model.user.User;
import lombok.NonNull;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class UseItemReadListener implements ItemReadListener<User> {

  @Override
  public void onReadError(@NonNull final Exception ex) {
    ItemReadListener.super.onReadError(ex);
  }
}

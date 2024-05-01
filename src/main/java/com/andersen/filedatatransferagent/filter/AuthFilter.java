package com.andersen.filedatatransferagent.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(0)
@Component
public class AuthFilter implements Filter {

  public static final String ALLOWED_IP = "127.0.0.1";

  @Override
  public void doFilter(
      final ServletRequest servletRequest,
      final ServletResponse servletResponse,
      final FilterChain filterChain
  ) throws ServletException, IOException {
    final String remoteHost = servletRequest.getRemoteAddr();
    //Need to be done using VPC or firewall on the cloud provider side
    if (remoteHost.equals(ALLOWED_IP)) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      throw new ServletException();
    }
  }
}

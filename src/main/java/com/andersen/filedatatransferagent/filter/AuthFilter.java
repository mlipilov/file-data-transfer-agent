package com.andersen.filedatatransferagent.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(0)
@Component
public class AuthFilter implements Filter {

  @Override
  public void doFilter(
      final ServletRequest servletRequest,
      final ServletResponse servletResponse,
      final FilterChain filterChain
  ) throws ServletException, IOException {
    final String remoteHost = servletRequest.getRemoteHost();
    log.info("Method: {}", ((HttpServletRequest) servletRequest).getMethod());
    log.info("remoteHost: {}", remoteHost);
    filterChain.doFilter(servletRequest, servletResponse);
  }
}

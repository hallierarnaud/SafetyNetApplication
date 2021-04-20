package com.openclassrooms.safetynet.model.repository;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class HttpTraceRepository implements org.springframework.boot.actuate.trace.http.HttpTraceRepository {

  AtomicReference<HttpTrace> lastTrace = new AtomicReference<>();

  @Override
  public List<HttpTrace> findAll() {
    return Collections.singletonList(lastTrace.get());
  }

  @Override
  public void add(HttpTrace trace) {
    if ("GET".equals(trace.getRequest().getMethod())) {
      lastTrace.set(trace);
    }
  }

}

package com.example.articles.core.context;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ProblemException {

  public AccessDeniedException() {
    super(
        Problem.builder()
            .title(HttpStatus.FORBIDDEN.getReasonPhrase())
            .status(HttpStatus.FORBIDDEN.value())
            .build());
  }
}

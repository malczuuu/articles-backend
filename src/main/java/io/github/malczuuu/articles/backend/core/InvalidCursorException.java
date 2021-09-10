package io.github.malczuuu.articles.backend.core;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public class InvalidCursorException extends ProblemException {

  public InvalidCursorException() {
    super(
        Problem.builder()
            .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .status(HttpStatus.BAD_REQUEST.value())
            .detail("Invalid cursor")
            .build());
  }
}

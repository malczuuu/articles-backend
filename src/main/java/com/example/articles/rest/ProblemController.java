package com.example.articles.rest;

import io.github.malczuuu.problem4j.core.Problem;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller which replaces default Spring-themed error page with custom application/problem+json
 * response.
 */
@RestController
public class ProblemController implements ErrorController {

  @RequestMapping(path = "/error")
  public ResponseEntity<Problem> handleError(HttpServletRequest request) {
    Object statusAttribute = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);

    HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    if (statusAttribute != null) {
      int statusCode = Integer.parseInt(statusAttribute.toString());
      responseStatus = HttpStatus.valueOf(statusCode);
    }

    return new ResponseEntity<>(
        Problem.builder()
            .title(responseStatus.getReasonPhrase())
            .status(responseStatus.value())
            .build(),
        headers,
        responseStatus);
  }
}
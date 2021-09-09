package com.example.articles.core.article;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public class ArticleNotFoundException extends ProblemException {

  public ArticleNotFoundException() {
    super(
        Problem.builder()
            .title(HttpStatus.NOT_FOUND.getReasonPhrase())
            .status(HttpStatus.NO_CONTENT.value())
            .detail("Article not found")
            .build());
  }
}

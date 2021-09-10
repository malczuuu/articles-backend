package io.github.malczuuu.articles.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.malczuuu.articles.backend.core.article.ArticleCreate;

public class ArticleCreateModel implements ArticleCreate {

  private final String content;

  @JsonCreator
  public ArticleCreateModel(@JsonProperty("content") String content) {
    this.content = content;
  }

  @Override
  @JsonProperty("content")
  public String getContent() {
    return content;
  }
}

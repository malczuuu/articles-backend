package com.example.articles.model;

import com.example.articles.core.article.ArticleCreate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

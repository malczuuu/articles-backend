package com.example.articles.model;

import com.example.articles.core.article.ArticleUpdate;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticleUpdateModel implements ArticleUpdate {

  private final String content;

  @JsonCreator
  public ArticleUpdateModel(@JsonProperty("content") String content) {
    this.content = content;
  }

  @Override
  @JsonProperty("content")
  public String getContent() {
    return content;
  }
}

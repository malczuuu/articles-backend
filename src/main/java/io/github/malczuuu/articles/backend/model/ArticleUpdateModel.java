package io.github.malczuuu.articles.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.malczuuu.articles.backend.core.article.ArticleUpdate;

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

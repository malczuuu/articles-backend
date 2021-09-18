package io.github.malczuuu.articles.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.malczuuu.articles.backend.core.article.ArticleCreate;

public class ArticleCreateModel implements ArticleCreate {

  private final String title;
  private final String content;

  @JsonCreator
  public ArticleCreateModel(
      @JsonProperty("title") String title, @JsonProperty("content") String content) {
    this.title = title;
    this.content = content;
  }

  @Override
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  @Override
  @JsonProperty("content")
  public String getContent() {
    return content;
  }
}

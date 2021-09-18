package io.github.malczuuu.articles.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.malczuuu.articles.backend.core.article.ArticleUpdate;

public class ArticleUpdateModel implements ArticleUpdate {

  private final String title;
  private final String content;

  @JsonCreator
  public ArticleUpdateModel(
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

package com.example.articles.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticleModel {

  private final String id;
  private final String content;
  private final String lastModifiedDate;

  @JsonCreator
  public ArticleModel(
      @JsonProperty("id") String id,
      @JsonProperty("content") String content,
      @JsonProperty("last_modified_date") String lastModifiedDate) {
    this.id = id;
    this.content = content;
    this.lastModifiedDate = lastModifiedDate;
  }

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  @JsonProperty("last_modified_date")
  public String getLastModifiedDate() {
    return lastModifiedDate;
  }
}

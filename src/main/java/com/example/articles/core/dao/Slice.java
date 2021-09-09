package com.example.articles.core.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Slice<T> {

  private final List<T> content;
  private final String pagingState;

  public Slice(List<T> content, String pagingState) {
    this.content = new ArrayList<>(content);
    this.pagingState = pagingState;
  }

  public List<T> getContent() {
    return Collections.unmodifiableList(content);
  }

  public String getPagingState() {
    return pagingState;
  }
}

package io.github.malczuuu.articles.backend.core.dao;

public class PagingStateException extends IllegalArgumentException {

  public PagingStateException() {
    super("Failed to parse pagingState");
  }
}

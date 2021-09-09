package com.example.articles.core.dao;

public class PagingStateException extends IllegalArgumentException {

  public PagingStateException() {
    super("Failed to parse pagingState");
  }
}

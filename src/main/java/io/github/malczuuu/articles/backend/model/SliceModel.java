package io.github.malczuuu.articles.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SliceModel<T> {

  private final List<T> content;
  private final Links links;

  @JsonCreator
  public SliceModel(@JsonProperty("content") List<T> content, @JsonProperty("_links") Links links) {
    this.content = new ArrayList<>(content);
    this.links = links;
  }

  @JsonProperty("content")
  public List<T> getContent() {
    return Collections.unmodifiableList(content);
  }

  @JsonProperty("_links")
  public Links getLinks() {
    return links;
  }

  public static class Links {

    private final String self;
    private final String next;

    @JsonCreator
    public Links(@JsonProperty("self") String self, @JsonProperty("next") String next) {
      this.self = self;
      this.next = next;
    }

    @JsonProperty("self")
    public String getSelf() {
      return self;
    }

    @JsonProperty("next")
    public String getNext() {
      return next;
    }
  }
}

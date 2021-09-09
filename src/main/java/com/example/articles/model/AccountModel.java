package com.example.articles.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountModel {

  private final String realm;
  private final String userid;
  private final String username;

  @JsonCreator
  public AccountModel(
      @JsonProperty("realm") String realm,
      @JsonProperty("userid") String userid,
      @JsonProperty("username") String username) {
    this.realm = realm;
    this.userid = userid;
    this.username = username;
  }

  @JsonProperty("realm")
  public String getRealm() {
    return realm;
  }

  @JsonProperty("userid")
  public String getUserid() {
    return userid;
  }

  @JsonProperty("username")
  public String getUsername() {
    return username;
  }
}

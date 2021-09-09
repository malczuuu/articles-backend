package com.example.articles.core.context;

public class HeaderContext {

  private final String realm;
  private final String userid;
  private final String username;

  public HeaderContext(String realm, String userid, String username) {
    this.realm = realm;
    this.userid = userid;
    this.username = username;
  }

  public String getRealm() {
    return realm;
  }

  public String getUserid() {
    return userid;
  }

  public String getUsername() {
    return username;
  }
}

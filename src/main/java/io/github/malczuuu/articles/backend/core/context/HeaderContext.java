package io.github.malczuuu.articles.backend.core.context;

public class HeaderContext implements SecurityContext {

  private final String realm;
  private final String userid;
  private final String username;

  public HeaderContext(String realm, String userid, String username) {
    this.realm = realm;
    this.userid = userid;
    this.username = username;
  }

  @Override
  public String getRealm() {
    return realm;
  }

  @Override
  public String getUserid() {
    return userid;
  }

  @Override
  public String getUsername() {
    return username;
  }
}

package io.github.malczuuu.articles.backend.core.context;

public interface SecurityContext {

  String getRealm();

  String getUserid();

  String getUsername();
}

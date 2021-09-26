package io.github.malczuuu.articles.backend.core.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpHeaders;

class HeaderContextServiceTests {

  private final String realmHeader = "X-Auth-Realm";
  private final String useridHeader = "X-Auth-Userid";
  private final String usernameHeader = "X-Auth-Username";

  @Test
  void shouldExtractContextFromHeaders() {
    SecurityContextService service =
        new HeaderContextService(realmHeader, useridHeader, usernameHeader);
    String realm = "shire";
    String userid = "00000000-0000-0000-0000-000000000000";
    String username = "frodo.baggins";

    HttpHeaders headers = new HttpHeaders();
    headers.set("X-Auth-Realm", realm);
    headers.set("X-Auth-Userid", userid);
    headers.set("X-Auth-Username", username);

    SecurityContext context = service.getContext(headers);

    assertEquals(realm, context.getRealm());
    assertEquals(userid, context.getUserid());
    assertEquals(username, context.getUsername());
  }

  @ParameterizedTest
  @CsvSource({"true", "false"})
  void shouldExtractContextFromHeadersRegardlessCaseSensitivity(boolean uppercase) {
    SecurityContextService service =
        new HeaderContextService(realmHeader, useridHeader, usernameHeader);
    String realm = "shire";
    String userid = "00000000-0000-0000-0000-000000000000";
    String username = "frodo.baggins";

    HttpHeaders headers = new HttpHeaders();
    headers.set(uppercase ? "X-AUTH-REALM" : "x-auth-realm", realm);
    headers.set(uppercase ? "X-AUTH-USERID" : "x-auth-userid", userid);
    headers.set(uppercase ? "X-AUTH-USERNAME" : "x-auth-username", username);

    SecurityContext context = service.getContext(headers);

    assertEquals(realm, context.getRealm());
    assertEquals(userid, context.getUserid());
    assertEquals(username, context.getUsername());
  }

  @Test
  void shouldDenyAnonymousAccess() {
    SecurityContextService service =
        new HeaderContextService(realmHeader, useridHeader, usernameHeader);

    HttpHeaders headers = new HttpHeaders();

    assertThrows(AccessDeniedException.class, () -> service.getContext(headers));
  }

  @Test
  void shouldDenyAccessOnUnknownHeaders() {
    SecurityContextService service =
        new HeaderContextService(realmHeader, useridHeader, usernameHeader);

    HttpHeaders headers = new HttpHeaders();
    headers.set("X-Realm", "shire");
    headers.set("X-Userid", "00000000-0000-0000-0000-000000000000");
    headers.set("X-Username", "frodo.baggins");

    assertThrows(AccessDeniedException.class, () -> service.getContext(headers));
  }
}

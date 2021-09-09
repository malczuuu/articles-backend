package com.example.articles.core.context;

import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class HeaderContextService {

  private final boolean anonymousAllowed;
  private final String defaultRealm;
  private final String defaultUserid;
  private final String realmHeader;
  private final String useridHeader;
  private final String usernameHeader;

  public HeaderContextService(
      @Value("${anonymous-allowed:false}") boolean anonymousAllowed,
      @Value("${default-realm:default}") String defaultRealm,
      @Value("${default-userid:anonymous}") String defaultUserid,
      @Value("${realm-header:X-Auth-Realm}") String realmHeader,
      @Value("${userid-header:X-Auth-Userid}") String useridHeader,
      @Value("${userid-header:X-Auth-Username}") String usernameHeader) {
    this.anonymousAllowed = anonymousAllowed;
    this.defaultRealm = defaultRealm;
    this.defaultUserid = defaultUserid;
    this.realmHeader = realmHeader;
    this.useridHeader = useridHeader;
    this.usernameHeader = usernameHeader;
  }

  public HeaderContext getContext(HttpHeaders headers) throws AccessDeniedException {
    String realm = headers.getFirst(realmHeader);
    String userid = headers.getFirst(useridHeader);
    String username = headers.getFirst(usernameHeader);

    if (anonymousAllowed) {
      realm = realm != null ? realm : defaultRealm;
      userid = userid != null ? userid : defaultUserid;
      username = username != null ? username : defaultUserid;
    }
    if (!allPresent(realm, userid, username)) {
      throw new AccessDeniedException();
    }
    return new HeaderContext(realm, userid, username);
  }

  private boolean allPresent(String... principals) {
    return Stream.of(principals).allMatch(r -> r != null && !r.isEmpty());
  }
}

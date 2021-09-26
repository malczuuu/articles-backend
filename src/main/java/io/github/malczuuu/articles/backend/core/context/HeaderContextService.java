package io.github.malczuuu.articles.backend.core.context;

import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class HeaderContextService implements SecurityContextService {

  private final String realmHeader;
  private final String useridHeader;
  private final String usernameHeader;

  public HeaderContextService(
      @Value("${realm-header:X-Auth-Realm}") String realmHeader,
      @Value("${userid-header:X-Auth-Userid}") String useridHeader,
      @Value("${username-header:X-Auth-Username}") String usernameHeader) {
    this.realmHeader = realmHeader;
    this.useridHeader = useridHeader;
    this.usernameHeader = usernameHeader;
  }

  @Override
  public SecurityContext getContext(HttpHeaders headers) throws AccessDeniedException {
    String realm = headers.getFirst(realmHeader);
    String userid = headers.getFirst(useridHeader);
    String username = headers.getFirst(usernameHeader);

    if (!allPresent(realm, userid, username)) {
      throw new AccessDeniedException();
    }
    return new HeaderContext(realm, userid, username);
  }

  private boolean allPresent(String... principals) {
    return Stream.of(principals).allMatch(r -> r != null && !r.isEmpty());
  }
}

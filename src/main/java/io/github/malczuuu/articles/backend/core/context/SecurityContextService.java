package io.github.malczuuu.articles.backend.core.context;

import org.springframework.http.HttpHeaders;

public interface SecurityContextService {

  SecurityContext getContext(HttpHeaders headers) throws AccessDeniedException;
}

package io.github.malczuuu.articles.backend.rest;

import io.github.malczuuu.articles.backend.core.context.HeaderContext;
import io.github.malczuuu.articles.backend.core.context.HeaderContextService;
import io.github.malczuuu.articles.backend.model.AccountModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

  private final HeaderContextService headerContextService;

  public AccountController(HeaderContextService headerContextService) {
    this.headerContextService = headerContextService;
  }

  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public AccountModel getAccount(@RequestHeader HttpHeaders headers) {
    HeaderContext context = headerContextService.getContext(headers);
    return new AccountModel(context.getRealm(), context.getUserid(), context.getUsername());
  }
}

package com.example.articles.rest;

import com.example.articles.core.context.HeaderContext;
import com.example.articles.core.context.HeaderContextService;
import com.example.articles.model.AccountModel;
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

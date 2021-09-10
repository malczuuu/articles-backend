package io.github.malczuuu.articles.backend.rest;

import io.github.malczuuu.articles.backend.core.article.ArticleNotFoundException;
import io.github.malczuuu.articles.backend.core.article.ArticleService;
import io.github.malczuuu.articles.backend.core.context.HeaderContext;
import io.github.malczuuu.articles.backend.core.context.HeaderContextService;
import io.github.malczuuu.articles.backend.model.ArticleCreateModel;
import io.github.malczuuu.articles.backend.model.ArticleModel;
import io.github.malczuuu.articles.backend.model.ArticleUpdateModel;
import io.github.malczuuu.articles.backend.model.SliceModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/articles")
public class ArticleController {

  private static final int DEFAULT_LIMIT = 20;

  private final ArticleService articleService;
  private final HeaderContextService headerContextService;

  public ArticleController(
      ArticleService articleService, HeaderContextService headerContextService) {
    this.articleService = articleService;
    this.headerContextService = headerContextService;
  }

  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public SliceModel<ArticleModel> findArticles(
      @RequestHeader HttpHeaders headers,
      @RequestParam(name = "limit", defaultValue = "20") String limit) {
    HeaderContext context = headerContextService.getContext(headers);
    int limitAsInt = parseLimit(limit);
    return articleService.findArticles(context.getRealm(), context.getUserid(), limitAsInt);
  }

  private int parseLimit(String limit) {
    try {
      int limitAsInt = Integer.parseInt(limit);
      if (limitAsInt < 1) {
        return DEFAULT_LIMIT;
      }
      return limitAsInt;
    } catch (NumberFormatException e) {
      return DEFAULT_LIMIT;
    }
  }

  @GetMapping(
      params = {"cursor"},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public SliceModel<ArticleModel> findNotesWithCursor(
      @RequestHeader HttpHeaders headers,
      @RequestParam(name = "cursor") String cursor,
      @RequestParam(name = "limit", defaultValue = "20") String limit) {
    HeaderContext context = headerContextService.getContext(headers);
    int limitAsInt = parseLimit(limit);
    return articleService.findArticles(context.getRealm(), context.getUserid(), limitAsInt, cursor);
  }

  @GetMapping(
      path = "/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ArticleModel findNote(HttpHeaders headers, @PathVariable("id") String id) {
    HeaderContext context = headerContextService.getContext(headers);
    return articleService
        .findArticle(context.getRealm(), context.getUserid(), id)
        .orElseThrow(ArticleNotFoundException::new);
  }

  @PostMapping(
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ArticleModel createNote(
      @RequestHeader HttpHeaders headers, @RequestBody ArticleCreateModel requestBody) {
    HeaderContext context = headerContextService.getContext(headers);
    return articleService.createArticle(context.getRealm(), context.getUserid(), requestBody);
  }

  @PutMapping(
      path = "/{id}",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ArticleModel updateNote(
      @RequestHeader HttpHeaders headers,
      @PathVariable("id") String id,
      @RequestBody ArticleUpdateModel requestBody) {
    HeaderContext context = headerContextService.getContext(headers);
    return articleService
        .updateArticle(context.getRealm(), context.getUserid(), id, requestBody)
        .orElseThrow(ArticleNotFoundException::new);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteNote(
      @RequestHeader HttpHeaders headers,
      @PathVariable("id") String id,
      @RequestBody ArticleUpdateModel requestBody) {
    HeaderContext context = headerContextService.getContext(headers);
    articleService.deleteArticle(context.getRealm(), context.getUserid(), id);
  }
}

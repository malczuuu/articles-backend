package io.github.malczuuu.articles.backend.core.article;

import io.github.malczuuu.articles.backend.core.InvalidCursorException;
import io.github.malczuuu.articles.backend.core.dao.ArticleRepository;
import io.github.malczuuu.articles.backend.core.dao.PagingStateException;
import io.github.malczuuu.articles.backend.core.dao.Slice;
import io.github.malczuuu.articles.backend.entity.ArticleEntity;
import io.github.malczuuu.articles.backend.model.ArticleModel;
import io.github.malczuuu.articles.backend.model.SliceModel;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;

@Service
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final TransactionOperations transactions;

  public ArticleService(ArticleRepository articleRepository, TransactionOperations transactions) {
    this.articleRepository = articleRepository;
    this.transactions = transactions;
  }

  public SliceModel<ArticleModel> findArticles(String realm, String owner, int limit) {
    Slice<ArticleEntity> entities = articleRepository.findArticles(realm, owner, limit);
    return new SliceModel<>(
        entities.getContent().stream().map(this::toModel).collect(Collectors.toList()),
        makeLinks(limit, entities));
  }

  private SliceModel.Links makeLinks(int limit, Slice<ArticleEntity> entities) {
    return makeLinks(limit, null, entities);
  }

  private SliceModel.Links makeLinks(int limit, String cursor, Slice<ArticleEntity> entities) {
    String self = makeSelfLink(limit, cursor);
    String next =
        entities.getPagingState() != null
            ? String.format("/api/articles?limit=%d&cursor=%s", limit, entities.getPagingState())
            : null;
    return new SliceModel.Links(self, next);
  }

  private String makeSelfLink(int limit, String cursor) {
    return cursor != null
        ? String.format("/api/articles?limit=%d&cursor=%s", limit, cursor)
        : String.format("/api/articles?limit=%d", limit);
  }

  private ArticleModel toModel(ArticleEntity article) {
    return new ArticleModel(
        article.getUid(),
        article.getTitle(),
        article.getContent(),
        article.getLastModifiedDate() != null
            ? article.getLastModifiedDate().atOffset(ZoneOffset.UTC).toString()
            : null);
  }

  public SliceModel<ArticleModel> findArticles(
      String realm, String owner, int limit, String cursor) {
    try {
      Slice<ArticleEntity> entities = articleRepository.findArticles(realm, owner, limit, cursor);
      return new SliceModel<>(
          entities.getContent().stream().map(this::toModel).collect(Collectors.toList()),
          makeLinks(limit, cursor, entities));
    } catch (PagingStateException e) {
      throw new InvalidCursorException();
    }
  }

  public Optional<ArticleModel> findArticle(String realm, String owner, String id) {
    return articleRepository.findArticle(realm, owner, id).map(this::toModel);
  }

  public ArticleModel createArticle(String realm, String owner, ArticleCreate create) {
    ArticleEntity article =
        new ArticleEntity(
            realm,
            UUID.randomUUID().toString().replace("-", ""),
            owner,
            create.getTitle(),
            create.getContent());
    article = articleRepository.save(article);
    return toModel(article);
  }

  public Optional<ArticleModel> updateArticle(
      String realm, String owner, String id, ArticleUpdate update) {
    return transactions.execute(
        transaction -> {
          Optional<ArticleEntity> optionalArticle = articleRepository.findArticle(realm, owner, id);
          if (optionalArticle.isEmpty()) {
            return Optional.empty();
          }
          ArticleEntity article = optionalArticle.get();

          article.setContent(update.getContent());
          article = articleRepository.save(article);

          return Optional.of(toModel(article));
        });
  }

  public void deleteArticle(String realm, String owner, String id) {
    articleRepository.deleteArticle(realm, owner, id);
  }
}

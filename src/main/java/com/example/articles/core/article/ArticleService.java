package com.example.articles.core.article;

import com.example.articles.core.InvalidCursorException;
import com.example.articles.core.dao.ArticleRepository;
import com.example.articles.core.dao.PagingStateException;
import com.example.articles.core.dao.Slice;
import com.example.articles.entity.ArticleEntity;
import com.example.articles.model.ArticleModel;
import com.example.articles.model.SliceModel;
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
        makeLinks(limit, entities).orElse(null));
  }

  private Optional<SliceModel.Links> makeLinks(int limit, Slice<ArticleEntity> entities) {
    return Optional.ofNullable(entities.getPagingState())
        .map(
            e ->
                String.format("/api/articles?limit=%s&cursor=%s", limit, entities.getPagingState()))
        .map(SliceModel.Links::new);
  }

  private ArticleModel toModel(ArticleEntity article) {
    return new ArticleModel(
        article.getUid(),
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
          makeLinks(limit, entities).orElse(null));
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
            realm, UUID.randomUUID().toString().replace("-", ""), owner, create.getContent());
    article = articleRepository.save(article);
    return toModel(article);
  }

  public Optional<ArticleModel> updateArticle(
      String realm, String owner, String id, ArticleUpdate update) {
    return transactions.execute(
        transaction -> {
          Optional<ArticleEntity> optionalNote = articleRepository.findArticle(realm, owner, id);
          if (optionalNote.isEmpty()) {
            return Optional.empty();
          }
          ArticleEntity article = optionalNote.get();

          article.setContent(update.getContent());
          article = articleRepository.save(article);

          return Optional.of(toModel(article));
        });
  }

  public void deleteArticle(String realm, String owner, String id) {
    articleRepository.deleteArticle(realm, owner, id);
  }
}

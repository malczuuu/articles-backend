package io.github.malczuuu.articles.backend.core.dao;

import io.github.malczuuu.articles.backend.entity.ArticleEntity;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ArticleRepository {

  private final MongoOperations mongoOperations;

  public ArticleRepository(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  public Slice<ArticleEntity> findArticles(String realm, String owner, int limit) {
    Criteria criteria =
        Criteria.where(ArticleEntity.REALM).is(realm).and(ArticleEntity.OWNER).is(owner);
    Query query = Query.query(criteria).limit(limit).with(Sort.by(Order.asc(ArticleEntity.ID)));

    List<ArticleEntity> articles = mongoOperations.find(query, ArticleEntity.class);

    return new Slice<>(articles, getPagingState(limit, articles).orElse(null));
  }

  private Optional<String> getPagingState(int limit, List<ArticleEntity> articles) {
    return articles.size() == limit
        ? Optional.of(articles.get(articles.size() - 1))
            .flatMap(article -> Optional.ofNullable(article.getId()))
            .map(ObjectId::toString)
        : Optional.empty();
  }

  public Slice<ArticleEntity> findArticles(
      String realm, String owner, int limit, String pagingState) throws PagingStateException {
    ObjectId afterId = parsePagingState(pagingState).orElseThrow(PagingStateException::new);

    Criteria criteria =
        Criteria.where(ArticleEntity.REALM)
            .is(realm)
            .and(ArticleEntity.OWNER)
            .is(owner)
            .and(ArticleEntity.ID)
            .gt(afterId);
    Query query = Query.query(criteria).limit(limit).with(Sort.by(Order.asc(ArticleEntity.ID)));

    List<ArticleEntity> articles = mongoOperations.find(query, ArticleEntity.class);

    return new Slice<>(articles, getPagingState(limit, articles).orElse(null));
  }

  private Optional<ObjectId> parsePagingState(String pagingState) {
    return ObjectId.isValid(pagingState)
        ? Optional.of(new ObjectId(pagingState))
        : Optional.empty();
  }

  public Optional<ArticleEntity> findArticle(String realm, String owner, String uid) {
    Criteria criteria =
        Criteria.where(ArticleEntity.REALM)
            .is(realm)
            .and(ArticleEntity.OWNER)
            .is(owner)
            .and(ArticleEntity.UID)
            .is(uid);
    Query query = Query.query(criteria);

    ArticleEntity article = mongoOperations.findOne(query, ArticleEntity.class);
    if (article == null) {
      return Optional.empty();
    }

    return Optional.of(article);
  }

  public ArticleEntity save(ArticleEntity article) {
    return mongoOperations.save(article);
  }

  public void deleteArticle(String realm, String owner, String uid) {
    Criteria criteria =
        Criteria.where(ArticleEntity.REALM)
            .is(realm)
            .and(ArticleEntity.OWNER)
            .is(owner)
            .and(ArticleEntity.UID)
            .is(uid);
    Query query = Query.query(criteria);

    mongoOperations.remove(query, ArticleEntity.class);
  }
}

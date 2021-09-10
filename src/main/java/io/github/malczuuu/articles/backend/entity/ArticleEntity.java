package io.github.malczuuu.articles.backend.entity;

import java.time.Instant;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "articles")
public class ArticleEntity implements Persistable<ObjectId> {

  public static final String ID = "_id";
  public static final String REALM = "realm";
  public static final String UID = "uid";
  public static final String OWNER = "owner";
  public static final String CONTENT = "content";
  public static final String LAST_MODIFIED_DATE = "lastModifiedDate";

  @MongoId(targetType = FieldType.OBJECT_ID)
  private ObjectId id;

  @Field(name = UID)
  private String uid;

  @Field(name = REALM)
  private String realm;

  @Field(name = OWNER)
  private String owner;

  @Field(name = CONTENT)
  private String content;

  @LastModifiedDate
  @Field(name = LAST_MODIFIED_DATE)
  private Instant lastModifiedDate;

  public ArticleEntity() {}

  public ArticleEntity(String realm, String uid, String owner, String content) {
    this(null, realm, uid, owner, content, null);
  }

  public ArticleEntity(
      ObjectId id,
      String realm,
      String uid,
      String owner,
      String content,
      Instant lastModifiedDate) {
    this.id = id;
    this.uid = uid;
    this.realm = realm;
    this.owner = owner;
    this.content = content;
    this.lastModifiedDate = lastModifiedDate;
  }

  @Override
  public boolean isNew() {
    return getId() != null;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  public String getRealm() {
    return realm;
  }

  public String getUid() {
    return uid;
  }

  public String getOwner() {
    return owner;
  }

  public String getContent() {
    return content;
  }

  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setContent(String content) {
    this.content = content;
  }
}

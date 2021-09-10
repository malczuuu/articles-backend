db = db.getSiblingDB('articles');

db.createCollection('articles');

db.runCommand({
  createIndexes: 'articles',
  indexes: [{
    'name': 'uid_unique_index',
    'key': { 'uid': 1 },
    'unique': true
  }]
});

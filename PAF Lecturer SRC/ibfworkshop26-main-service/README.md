## Workshop 26

1. Install local version https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-os-x/

2. Run the mongodb locally

```
mongod --config /usr/local/etc/mongod.conf
```

3. Import both the json data into your local Mongodb instance


```
mongosh
show dbs;
use bggdb;
mongoimport -hlocalhost --port=27017 -dbggdb -cgame --jsonArray json/game.json

mongoimport -hlocalhost --port=27017 -dbggdb -ccomment --jsonArray json/comment.json
```

4. Find all records
```
db.game.find()
```

5. Find record witg predication
```
db.game.find( { "name": "Samurai" } )
```

6. Drop mongo collection
```
db.game.drop()
```

7. Get all record sort by field
```
db.game.find().sort("title")
```

8. Get one record
```
db.game.find({"gid": 1})
```

9. Create a searchable index and allow text query

```
db.game.createIndex({ "name": "text", "url": "text" });
db.game.find({ $text: { $search: "tic" } });
```

10. Create Index for the Comment collection -  Text Query

```
 db.comment.createIndex({c_text: "text"})
```
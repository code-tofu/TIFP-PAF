## Workshop 28

Connect string format

```
mongosh "mongodb+srv://cluster0.oepmxgl.mongodb.net/workshop27" --apiVersion 1 --username workshop27
```

1. Mongoql for a

```
db.game.aggregate([
  {
    $match: {gid:3}
  },
  {
    $lookup:
      {
          from: "reviews",
          localField: "gameId",
          foreignField: "gid",
          as: "reviewsDocs",
      }
  },
  {
    $project : { _id: -1, gid: 1,
      name: 1, year: 1, ranking: 1, users_rated: 1, url: 1, image:1, reviews: "$reviewsDocs._id",timestamp: "$$NOW"}
  },

]);
```

2. Mongoql for b

```
db.comment.aggregate([
  {
    "$match" : { "$and" : [{ "user" : "Goodsound"}, { "rating" : { "$gt" : 6}}]}
  },
  {
    "$lookup" : { "from" : "game", "localField" : "gid", "foreignField" : "gid", "as" : "gameComment"}
  },
  { "$project" : { "_id" : 1, "c_id" : 1, "user" : 1, "rating" : 1, "c_text" : 1, "gid" : 1,    "game_name" : "$gameComment.name"}},
  { "$limit" : 500}
]);
```

```
db.comment.find({user:"Goodsound"})

db.comment.aggregate(
   {$group : { _id : '$user', count : {$sum : 1}}})

db.comment.aggregate({ "$match" : { "$and" : [{ "user" : "Goodsound"}, { "rating" : 6}]}}, { "$lookup" : { "from" : "game", "localField" : "gid", "foreignField" : "gid", "as" : "gameComment"}}, { "$project" : { "_id" : 1, "c_id" : 1, "user" : 1, "rating" : 1, "c_text" : 1, "gid" : 1, "game_name" : "$gameComment.name"}}, { "$limit" : 1000})


db.comment.aggregate([{ "$match" : { "$and" : [{ "user" : "Goodsound"}, { "rating" : { "$gt" : 6}}]}}, { "$lookup" : { "from" : "game", "localField" : "gid", "foreignField" : "gid", "as" : "gameComment"}}, { "$project" : { "_id" : 1, "c_id" : 1, "user" : 1, "rating" : 1, "c_text" : 1, "gid" : 1, "game_name" : "$gameComment.name"}}, { "$limit" : 500}])
```

```
{
	"name": "Kenneth",
	"email": "k@k.com",
	"phone": "3345677",
	"confirmation_date": "2018-12-10T13:49:51.141Z",
	"comments": "hi hi",
    "food_type": "Non-vegetarian"
}


{
	"name": "Kenneth",
	"email": "k@k.com",
	"phone": "3345677",
	"confirmation_date": "2018-12-10T13:49:51.141Z",
	"comments": "hi hi",
    "food_type": "Vegetarian"
}
```

2. Aggregate by counting food type

```
db.rsvp.aggregate([{ "$group" : { "_id" : "$foodType", "foodType" : { "$push" : "$foodType"}, "count" : { "$sum" : 1}}}, { "$sort" : { "count" : 1}}])
```

```
db.rsvp.aggregate([{ "$group" : { "_id" : "$foodType", "foodType" : { "$push" : "$foodType"}, "count" : { "$sum" : 1}}}, { "$sort" : { "count" : 1}}])
```

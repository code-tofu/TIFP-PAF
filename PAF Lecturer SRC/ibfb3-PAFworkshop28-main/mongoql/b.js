use bggdb;

db.reviews.aggregate([
    {
        $match: {"$and": [{"user": "desertfox2004"}, {"rating": {"$gt": 5}}]}
    },
    {
        $lookup: {"from": "games", "localField": "gid", 
                "foreignField": "gid", "as": "gameReviews"}
    },
    {
        $project: { "_id": 1, "c_id": 1, "user": 1, "rating": 1, "c_text": 1, "gid": 1,
            "game_name": "$gameReviews.name"}
    }
]);
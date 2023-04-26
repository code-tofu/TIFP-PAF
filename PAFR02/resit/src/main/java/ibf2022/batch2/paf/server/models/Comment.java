package ibf2022.batch2.paf.server.models;

// Do not change this file
public class Comment {

	private String restaurantId;
	private String name;
	private long date = 0l;
	private String comment;
	private int rating;

	public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }
	public String getRestaurantId() { return this.restaurantId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setComment(String comment) { this.comment = comment; }
	public String getComment() { return this.comment; }

	public void setDate(long date) { this.date = date; }
	public long getDate() { return this.date; }

	public void setRating(int rating) { this.rating = rating; }
	public int getRating() { return this.rating; }

	@Override
	public String toString() {
		return "Comment{restaurantId=%s, name=%s, date=%d, comment=%s, rating=%d"
				.formatted(restaurantId, name, date, comment, rating);
	}
}

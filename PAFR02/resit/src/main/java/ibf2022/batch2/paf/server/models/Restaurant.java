package ibf2022.batch2.paf.server.models;

import java.util.LinkedList;
import java.util.List;

// Do not change this file
public class Restaurant {

	private String restaurantId;
	private String name;
	private String address;
	private String cuisine;
	private List<Comment> comments = new LinkedList<>();

	public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }
	public String getRestaurantId() { return this.restaurantId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setAddress(String address) { this.address = address; }
	public String getAddress() { return this.address; }

	public void setCuisine(String cuisine) { this.cuisine = cuisine; }
	public String getCuisine() { return this.cuisine; }

	public void setComments(List<Comment> comments) { this.comments = comments; }
	public List<Comment> getComments() { return this.comments; }
	public void addComment(Comment comment) { this.comments.add(comment); }

	@Override
	public String toString() {
		return "Restaurant{restaurantId=%s, name=%s, address=%s, cuisine=%s, comments=%s"
				.formatted(restaurantId, name, address, cuisine, comments);
	}
}

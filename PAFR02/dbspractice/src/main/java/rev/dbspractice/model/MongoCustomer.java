package rev.dbspractice.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoCustomer {
    private ObjectId id;
    private String username;
    private String name;
    private String address;
    private LocalDate birthdate;
    private String email;
    private int[] accounts;
    private ArrayList<MongoTierDetail> tierDetails;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int[] getAccounts() {
        return accounts;
    }

    public void setAccounts(int[] accounts) {
        this.accounts = accounts;
    }

    public ArrayList<MongoTierDetail> getTierDetails() {
        return tierDetails;
    }

    public void setTierDetails(ArrayList<MongoTierDetail> tierDetails) {
        this.tierDetails = tierDetails;
    }

    @Override
    public String toString() {
        return "MongoCustomer [id=" + id + ", username=" + username + ", name=" + name + ", address=" + address
                + ", birthdate=" + birthdate + ", email=" + email + ", accounts="
                + Arrays.toString(accounts) + ", tierDetails=" + tierDetails + "]";
    }

    public static MongoCustomer mapCustomer(Document doc, Document tierDetails) {
        MongoCustomer newCustomer = new MongoCustomer();
        newCustomer.setAccounts(doc.getList("accounts", Integer.class).stream().mapToInt(Integer::intValue).toArray());
        newCustomer.setAddress(doc.getString(("address")));
        newCustomer.setBirthdate(doc.getDate("birthdate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        newCustomer.setId(doc.getObjectId("_id"));
        newCustomer.setName(doc.getString("name"));
        newCustomer.setUsername(doc.getString("username"));
        newCustomer.setEmail(doc.getString("email"));

        ArrayList<MongoTierDetail> newTierDetails = new ArrayList<>();
        System.out.println(tierDetails);
        List<Document> tDarray = tierDetails.getList("tierDetails", Document.class);
        for (Document tD : tDarray) {
            newTierDetails.add(MongoTierDetail.mapMongoTierDetail(tD));
        }
        newCustomer.setTierDetails(newTierDetails);
        return newCustomer;
    }

}

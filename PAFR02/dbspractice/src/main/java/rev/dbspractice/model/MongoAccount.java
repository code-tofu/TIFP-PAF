package rev.dbspractice.model;

import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoAccount {

    private ObjectId id;
    private int account_id;
    private int limit;
    private String[] products;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String[] getProducts() {
        return products;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "MongoAccount [id=" + id + ", account_id=" + account_id + ", limit=" + limit + ", products="
                + Arrays.toString(products) + "]";
    }

    public static MongoAccount mapAccount(Document doc) {
        MongoAccount newAccount = new MongoAccount();
        newAccount.setId(doc.getObjectId("_id"));
        newAccount.setAccount_id(doc.getInteger("account_id"));
        newAccount.setLimit(doc.getInteger("limit"));
        System.out.println(doc.getList("products", String.class).toArray(new String[0]));
        newAccount.setProducts(doc.getList("products", String.class).toArray(new String[0]));
        return newAccount;
    }

}

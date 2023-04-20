package rev.dbspractice.model;

import java.util.Arrays;

import org.bson.Document;

public class MongoTierDetail {

    private String tier;
    private String id;
    private boolean isActive;
    private String[] benefits;

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String[] getBenefits() {
        return benefits;
    }

    public void setBenefits(String[] benefits) {
        this.benefits = benefits;
    }

    public static MongoTierDetail mapMongoTierDetail(Document doc) {
        Document v = doc.get("v", Document.class);
        MongoTierDetail newTierDetail = new MongoTierDetail();
        newTierDetail.setActive(v.getBoolean("active"));
        newTierDetail.setBenefits(v.getList("benefits", String.class).toArray(new String[0]));
        newTierDetail.setId(v.getString("id"));
        newTierDetail.setTier(v.getString("tier"));
        return newTierDetail;
    }

    @Override
    public String toString() {
        return "MongoTierDetail [tier=" + tier + ", id=" + id + ", isActive=" + isActive + ", benefits="
                + Arrays.toString(benefits) + "]";
    }

}

// {
// "k": "0df078f33aa74a2e9696e0520c1a828a",
// "v": {
// "tier": "Bronze",
// "id": "0df078f33aa74a2e9696e0520c1a828a",
// "active": true,
// "benefits": [
// "sports tickets"
// ]
// }
// }
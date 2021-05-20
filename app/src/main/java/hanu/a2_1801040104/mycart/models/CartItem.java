package hanu.a2_1801040104.mycart.models;

public class CartItem {
    private int id;
    private String thumbnail;
    private String name;
    private int quantity;
    private int unitPrice;

    public CartItem() {
    }

    public CartItem(int id, String thumbnail, String name, int quantity, int unitPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int price) {
        this.unitPrice = price;
    }
}

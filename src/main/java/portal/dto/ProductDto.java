package portal.dto;

import java.sql.Timestamp;


public class ProductDto {

    private int itemId;
    private String itemName;
    
    private int categoryId;
    private String categoryName;
    
    private int price;
    private int sellingFlg;
    private Timestamp updateAt;
    
    public int getItemId() {
    	return itemId;
    }
    public void setItemId(int itemId) {
    	this.itemId = itemId;
    }
    
    public String getItemName() {
    	return itemName;
    }
    public void setItemName(String itemName) {
    	this.itemName = itemName;
    }
    
    public int getCategoryId() {
    	return categoryId;
    }
    public void setCategoryId(int categoryId) {
    	this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
    	return categoryName;
    }
    public void setCategoryName(String categoryName) {
    	this.categoryName = categoryName;
    }
    
    public int getPrice() {
    	return price;
    }
    public void setPrice(int price) {
    	this.price = price;
    }
    
    public int getSellingFlg() {
    	return sellingFlg;
    }
    public void setSellingFlg(int sellingFlg) {
    	this.sellingFlg = sellingFlg;
    }
    
    public Timestamp getUpdateAt() {
    	return updateAt;
    }
    public void setUpdateAt(Timestamp updateAt) {
    	this.updateAt = updateAt;
    }
}
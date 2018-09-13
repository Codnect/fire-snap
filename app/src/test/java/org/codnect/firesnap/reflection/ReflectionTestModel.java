package org.codnect.firesnap.reflection;

import org.codnect.firesnap.annotation.Model;

/**
 * Created by Burak Koken on 21.5.2018.
 *
 * @author Burak Koken
 */
@Model
public class ReflectionTestModel {

    private long id;
    private String productName;
    private boolean visible;
    private float price;
    private boolean discount;

    public ReflectionTestModel() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean hasDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

}

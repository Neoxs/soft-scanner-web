package org.example.softscannerweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @Field("_id") 
    private String id;
    private String name;
    private String price;
    private String expirationDate;

    @Override
    public String toString() {
        return "Product [ID=" + id + ", Name=" + name + ", Price=" + price +
                ", Expiration Date=" + expirationDate + "]";
    }
}

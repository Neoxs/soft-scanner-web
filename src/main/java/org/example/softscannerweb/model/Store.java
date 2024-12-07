package org.example.softscannerweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "stores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Store {
    @Id
    private String ID;
    @DBRef
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return "Store [ID=" + ID + ", Products=" + products + "]";
    }
}

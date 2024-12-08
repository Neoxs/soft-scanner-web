package org.example.softscannerweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @Field("_id") 
    private String id;
    private String name;
    private String age;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "User [ID=" + id + ", Name=" + name + ", Age=" + age +
                ", Email=" + email + ", Password=" + password + "]";
    }
}
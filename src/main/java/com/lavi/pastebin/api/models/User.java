package com.lavi.pastebin.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class User extends Model {
    private String username;
    private String email;
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Post> posts;
}

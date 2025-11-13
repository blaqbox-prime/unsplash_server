package com.blaqboxdev.unsplash.Models.DTO;

import com.blaqboxdev.unsplash.Models.Entities.User;
import com.blaqboxdev.unsplash.Models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements Serializable {
    private String _id;
    private String email;
    private Role role;

    public UserDTO(User user) {
        this._id = user.get_id();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}

package p.dtos;

import p.entities.UserEntity;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private Long id = 1L;
    private String name = "";

    public UserDTO(UserEntity user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

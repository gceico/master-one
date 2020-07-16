package p.entities;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
public class UserEntity extends BaseEntity implements Serializable {
    private String name = "";
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<NoteEntity> notes;

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }
}

package p.entities;

import java.io.*;
import javax.persistence.*;

@Entity
public class NoteEntity extends BaseEntity implements Serializable {
    private String note = "";

    public String getNote() {
        return this.note;
    }

    public void setNote(String newNote) {
        if (newNote == null || newNote.isEmpty()) {
            return;
        }
        this.note = newNote;
    }

    @Override
    public String toString() {
        return "{id: " + getId() + " , note: " + getNote() + " , userId: " + getUserEntity().getId() + "}";
    }

    @ManyToOne
    private UserEntity userEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}

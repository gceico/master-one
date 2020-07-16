package p.dtos;

import p.entities.NoteEntity;

import java.io.Serializable;

public class NoteDTO implements Serializable {
    private Long id = 1L;
    private String note = "";

    public NoteDTO(NoteEntity note) {
        this.id = note.getId();
        this.note = note.getNote();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}

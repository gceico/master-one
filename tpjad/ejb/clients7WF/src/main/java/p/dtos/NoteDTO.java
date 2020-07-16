package p.dtos;

import java.io.Serializable;

public class NoteDTO implements Serializable {
    private Long id = 1L;
    private String note = "";

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

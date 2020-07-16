package p.interfaces;

import p.dtos.NoteDTO;
import p.dtos.UserDTO;

import java.util.List;

public interface LogicR {
    void addNoteForUserR(String note, String userName);

    List<UserDTO> getAllUsersR();

    List<NoteDTO> getAllNotesR();

}

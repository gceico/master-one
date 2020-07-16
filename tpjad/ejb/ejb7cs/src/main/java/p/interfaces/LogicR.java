package p.interfaces;

import p.dtos.NoteDTO;
import p.dtos.UserDTO;
import p.entities.UserEntity;

import java.util.List;

public interface LogicR {
    void addNoteForUserR(String note, String userName);

    List<UserDTO> getAllUsersR();

    List<NoteDTO> getAllNotesR();

    List<NoteDTO> getAllNotesForUserR(UserEntity userEntity);
}

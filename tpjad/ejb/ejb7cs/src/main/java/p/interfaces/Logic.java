package p.interfaces;

import p.entities.NoteEntity;
import p.entities.UserEntity;

import java.util.List;

public interface Logic {
    UserEntity addNoteForUser(String note, String userName);

    List<UserEntity> getAllUsers();

    List<NoteEntity> getAllNotes();

    List<NoteEntity> getAllNotesForUser(UserEntity userEntity);
}

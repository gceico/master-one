package p.beans;

import javax.ejb.*;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.util.*;
import java.util.stream.Collectors;

import p.dtos.NoteDTO;
import p.dtos.UserDTO;
import p.entities.NoteEntity;
import p.entities.UserEntity;
import p.interfaces.Logic;
import p.interfaces.LogicR;

@Stateless
@Local(Logic.class)
@Remote(LogicR.class)
public class LogicBean implements Logic, LogicR {
    @PersistenceContext(unitName = "ejb7")
    private EntityManager manager;

    public UserEntity addNoteForUser(String note, String userName) {
        if (note == null || note.isEmpty()) {
            return null;
        }
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setNote(note);
        try {
            TypedQuery<UserEntity> query = manager.createQuery(
                    "select u from UserEntity u where u.name = :name", UserEntity.class);
            UserEntity dbUser = query.setParameter("name", userName).getSingleResult();
            noteEntity.setUserEntity(dbUser);
            manager.persist(noteEntity);
            return dbUser;
        } catch (Exception e) {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(userName);
            manager.persist(userEntity);

            noteEntity.setUserEntity(userEntity);
            manager.persist(noteEntity);
            return userEntity;
        }
    }

    public void addNoteForUserR(String note, String userName) {
        this.addNoteForUser(note, userName);
    }

    public List<UserEntity> getAllUsers() {
        TypedQuery<UserEntity> query = manager.createQuery(
                "select u from UserEntity u ", UserEntity.class);
        return query.getResultList();
    }

    public List<NoteEntity> getAllNotes() {
        TypedQuery<NoteEntity> query = manager.createQuery(
                "select n from NoteEntity n", NoteEntity.class);
        return query.getResultList();
    }

    public List<NoteEntity> getAllNotesForUser(UserEntity userEntity) {
        TypedQuery<NoteEntity> query = manager.createQuery(
                "select n from NoteEntity n where n.userEntity = :userEntity", NoteEntity.class);
        return query.setParameter("userEntity", userEntity).getResultList();
    }

    public List<UserDTO> getAllUsersR() {
        return this.getAllUsers()
                .stream()
                .map(this::userToDTO)
                .collect(Collectors.toList());
    }

    public List<NoteDTO> getAllNotesR() {
        return this.getAllNotes()
                .stream()
                .map(this::noteToDTO)
                .collect(Collectors.toList());
    }

    public List<NoteDTO> getAllNotesForUserR(UserEntity userEntity) {
        return this.getAllNotesForUser(userEntity)
                .stream()
                .map(this::noteToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO userToDTO(UserEntity user) {
        if (user == null) return null;
        return new UserDTO(user);
    }

    private NoteDTO noteToDTO(NoteEntity note) {
        if (note == null) return null;
        return new NoteDTO(note);
    }
}

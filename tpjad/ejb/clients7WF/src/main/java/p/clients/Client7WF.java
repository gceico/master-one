package p.clients;

import p.dtos.NoteDTO;
import p.interfaces.LogicR;

import java.util.*;
import javax.naming.*;

public class Client7WF {
    static final Properties JNDI = new Properties();

    static {
        JNDI.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
        JNDI.put("java.naming.provider.url", "http-remoting://localhost:8080");
    }

    static final String JNDIFacadeR = "ejb7cs/LogicBean!p.interfaces.LogicR";

    public static void main(String[] args) throws Exception {
        System.out.println("Client7JB");
        LogicR proxy = (LogicR) (new InitialContext(JNDI)).lookup(JNDIFacadeR);
        proxy.addNoteForUserR("Franzela alba!", "Gabriel");
        System.out.println("Notes: \n");
        for (NoteDTO noteDTO : proxy.getAllNotesR()) {
            System.out.println("{id:" + noteDTO.getId() + ", note:" + noteDTO.getNote() + "}");
        }
    }
}

package p.client;

import java.io.*;
import java.util.stream.Collectors;
import javax.ejb.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import p.entities.NoteEntity;
import p.entities.UserEntity;
import p.interfaces.Logic;

@WebServlet(name = "ServletClient", urlPatterns = "/client")
public class ServletClient extends HttpServlet {

    @EJB
    private Logic logic;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String note = request.getParameter("note");
            String userName = request.getParameter("user");
            UserEntity userEntity = null;

            if (note != null && !note.isEmpty() && userName != null && !userName.isEmpty()) {
                userEntity = this.logic.addNoteForUser(note, userName);
            }
            if (userEntity != null) {
                String notes = this.logic.getAllNotesForUser(userEntity)
                        .stream()
                        .map(NoteEntity::toString)
                        .collect(Collectors.joining("<br>"));

                request.setAttribute("notes", notes);
                RequestDispatcher dispatcher = request.getRequestDispatcher("note.jsp");
                dispatcher.forward(request, response);
            } else {
                throw new Error("User not found");
            }
        } catch (Exception e) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            request.setAttribute("error", e.toString());
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

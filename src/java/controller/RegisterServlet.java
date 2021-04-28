/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import account.AccountDAO;
import account.AccountDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author hotie
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
@MultipartConfig(
        fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        System.out.println(action);
        //if a==null -> redirect to register_form.html
        try {
            if (action == null) {
                response.sendRedirect("RegisterForm.jsp");
            } //if a.equals("register") 
            else if (action.equals("register")) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String email = request.getParameter("email");
                String name = request.getParameter("name");
                String avatarURL = uploadFile(request);

                //search for duplicated username in database
                AccountDAO dao = new AccountDAO();
                AccountDTO foundAccount = dao.getAccountByUsername(username);
                AccountDTO foundAccountByEmail = dao.getByEmail(email);

                //if foundAccount!= null -> dispatch to register_form, keep all inputted values except password
                if (foundAccount != null) {
                    request.setAttribute("username", username);
                    request.setAttribute("email", email);
                    request.setAttribute("name", name);
                    request.setAttribute("avatar", avatarURL);
                    request.setAttribute("duplicatedUser", foundAccount);
                    request.getRequestDispatcher("RegisterForm.jsp").forward(request, response);
                } else if (foundAccountByEmail != null) {
                    request.setAttribute("username", username);
                    request.setAttribute("email", email);
                    request.setAttribute("name", name);
                    request.setAttribute("avatar", avatarURL);
                    request.setAttribute("duplicatedEmail", foundAccountByEmail);
                    request.getRequestDispatcher("RegisterForm.jsp").forward(request, response);
                } //else -> add account to database, set session, then redirect to NovelServlet
                else {
                    if (avatarURL.equals("")) {
                        avatarURL = "default_ava.png";
                    }
                    AccountDTO newAccount = new AccountDTO(username, password, email, name, false, avatarURL);
                    AccountDAO aDAO = new AccountDAO();
                    aDAO.addAccount(newAccount);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", newAccount);
                    response.sendRedirect("NovelServlet");
                }
            }
        } catch (SQLException ex) {
            log(ex.getMessage());
            request.setAttribute("CHAPTERNOTFOUND", "Sorry, something went wrong");
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }

    private String uploadFile(HttpServletRequest request) {
        String fileName = "";
        try {
            Part filePart = request.getPart("avatar");
            fileName = (String) getFileName(filePart);

            String applicationPath = request.getServletContext().getRealPath("");
            String basePath = applicationPath + File.separator + "images" + File.separator + "avatars" + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                log(e.getMessage());
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            log(e.getMessage());
            fileName = "";
        }
        return fileName;
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

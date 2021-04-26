/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import account.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import novel.NovelDAO;
import novel.NovelDTO;

/**
 *
 * @author ASUS GAMING
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        
        //if a==null -> redirect to login_form.jsp
        if(action == null){
            response.sendRedirect("LoginForm.jsp");
        }
        
        //if a = login
        else if(action.equals("login")){
            String username = request.getParameter("username");
            String pass = request.getParameter("password");
            AccountDAO dao = new AccountDAO();
            NovelDAO ndao = new NovelDAO();
            
            //check login
            boolean isValid = dao.checkLogin(username, pass);
            
            //if isValid = false -> setAttribute("success", isValid) and dispatch to login_form.jsp
            if(isValid == false){
                request.setAttribute("success", isValid);
                request.setAttribute("username", username);
                request.getRequestDispatcher("LoginForm.jsp").forward(request, response);
                
            }
            
            //else redirect to NovelServlet
            else{
                AccountDAO aDAO = new AccountDAO();
                HttpSession session = request.getSession();
                session.setAttribute("user", aDAO.getAccountByUsername(username));
                ArrayList<NovelDTO> novelList = (ArrayList<NovelDTO>) ndao.getAll();
                request.setAttribute("novelList", novelList);
                request.getRequestDispatcher("Homepage.jsp").forward(request, response);
            }
        } else if(action.equals("logout")){
            HttpSession session = request.getSession(false);
            session.invalidate();
            response.sendRedirect("NovelServlet");
        } else if(action.equals("invalid")) {
                request.setAttribute("noti", "You must login to perform that action");
                request.getRequestDispatcher("LoginForm.jsp").forward(request, response);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
     
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

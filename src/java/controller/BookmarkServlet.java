/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import account.AccountDTO;
import bookmark.BookmarkDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * @author Gray
 */
@WebServlet(name = "BookmarkServlet", urlPatterns = {"/BookmarkServlet"})
public class BookmarkServlet extends HttpServlet {

        /**
         * Processes requests for both HTTP <code>GET</code> and
         * <code>POST</code> methods.
         *
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
                HttpSession session = request.getSession();
                AccountDTO user = (AccountDTO) session.getAttribute("user");
                String novelID = request.getParameter("id");
                String ERROR = "error.jsp";
                BookmarkDAO bDAO = new BookmarkDAO();
                NovelDAO nDAO = new NovelDAO();
                String action = request.getParameter("action");
                //action = bull -> add or remove bookmark of a novel
                if (action == null) {
                        if (user != null) {
                                if (bDAO.bookmarkHandler(user.getUserName(), novelID)) {
                                        request.getRequestDispatcher("NovelServlet?action=NovelInfo&nid=" + novelID).forward(request, response);
                                } else {
                                        response.sendRedirect(ERROR);
                                }
                        } else {
                                response.sendRedirect("LoginForm.jsp");
                        }
                } else if (action.equals("BookmarkList")) {
                        ArrayList<String> bidList = bDAO.getBookmarkIDList(user);
                        ArrayList<NovelDTO> nList = nDAO.getNovelListByID(bidList);
                        if (nList.size() > 0) {
                                request.setAttribute("novelList", nList);
                                request.setAttribute("BOOKMARKFLAG", "Your Bookmarks");
                        } else {
                                request.setAttribute("EMPTYBOOKMARK", "You haven't bookmarked any novel yet!");
                        }
                }
                request.getRequestDispatcher("Homepage.jsp").forward(request, response);
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

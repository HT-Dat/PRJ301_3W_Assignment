/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import chapter.ChapterDAO;
import chapter.ChapterDTO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
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
@WebServlet(name = "ChapterServlet", urlPatterns = {"/ChapterServlet"})
public class ChapterServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        System.out.println(action);
        try {
            if (session.getAttribute("user") == null) {
                response.sendRedirect("LoginServlet");
            } else {
                if (action.equals("AddChapterForm")) {
                    String nid = request.getParameter("nid");
                    NovelDAO nDAO = new NovelDAO();
                    NovelDTO novel = nDAO.get(nid);
                    request.setAttribute("novelObj", novel);
                    request.getRequestDispatcher("AddChapterForm.jsp").forward(request, response);
                    
                    
                    
                    
                    
                // B E W A R E
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                } else if (action.equals("add")) {
                    String novelID = request.getParameter("nid");
                    String chapID = "C1";
                    String chapName = request.getParameter("chapname");
                    String content = request.getParameter("content");
                    byte[] bytes = content.getBytes(StandardCharsets.ISO_8859_1);
                    content = new String(bytes, StandardCharsets.UTF_8);
                    chapName = new String(chapName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    ChapterDAO cDAO = new ChapterDAO();
                    LinkedList<ChapterDTO> chapList = cDAO.getChapters(novelID);
                    for (ChapterDTO chapter : chapList) {
                        if (chapID.equalsIgnoreCase(chapter.getChapterID())) {
                            chapID = "C" + (Integer.parseInt(chapID.substring(1)) + 1);
                        }
                    }
                    String fileURL = chapID + ".txt";
                    NovelDAO nDAO = new NovelDAO();
                    NovelDTO n = nDAO.get(novelID);
                    java.sql.Date uploadDate = new Date(System.currentTimeMillis());
                    ChapterDTO chap = new ChapterDTO(chapID, n, chapName, fileURL, uploadDate);
                    boolean create = createFile(chap, content);
                    System.out.println(create);
                    if (create == false) {
                        response.sendRedirect("error.jsp");
                    } else {
                        cDAO.add(chap);
                        response.sendRedirect("NovelServlet");
                    }
                } else if (action.equals("del")) {
                    String chapterID = request.getParameter("cid");
                    String novelID = request.getParameter("nid");
                    ChapterDAO cDAO = new ChapterDAO();
                    ChapterDTO chap = cDAO.getChapterByChapterIDNovelID(novelID, chapterID);
                    if (chap != null) {
                        deleteChapFile(chap);
                        cDAO.delete(chap);
                        response.sendRedirect("NovelServlet?action=NovelInfo&nid=" + chap.getNovel().getNovelID());
                    } else {
                        request.setAttribute("CHAPTERNOTFOUND", "Could not find this chapter");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                }
            }
        } catch (SQLException | IOException ex) {
            log(ex.getMessage());
        }
    }

    public boolean createFile(ChapterDTO chap, String content) throws FileNotFoundException, IOException {
        String filepath = getServletContext().getRealPath("") + "/Novels/" + chap.getNovel().getNovelID() + "/" + chap.getFileURL();
        File f = new File(filepath);
        if (f.exists()) {
            return false;
        } else {
            try {
                Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8));
                out.append(content);
                out.flush();
                out.close();
                return true;
            } catch (FileNotFoundException e) {
                log("ERRORWRITEFILE: " + e.getMessage());
            }
        }
        return false;
    }

    public void deleteChapFile(ChapterDTO chap) {
        String filepath = getServletContext().getRealPath("") + "/novels/" + chap.getNovel().getNovelID() + "/" + chap.getFileURL();
        File file = new File(filepath);
        if (!file.exists()) {
            return;
        } else {
            file.delete();
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import account.AccountDTO;
import bookmark.BookmarkDAO;
import chapter.ChapterDAO;
import chapter.ChapterDTO;
import comment.CommentDAO;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import novel.NovelDAO;
import novel.NovelDTO;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import tag.TagDAO;
import tag.TagDTO;

/**
 *
 * @author Gray
 */
@WebServlet(name = "NovelServlet", urlPatterns = {"/NovelServlet"})
@MultipartConfig(
        fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class NovelServlet extends HttpServlet {

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
        String info = "NovelInfo.jsp";
        String action = request.getParameter("action");
        NovelDAO nDAO = new NovelDAO();
        TagDAO tDAO = new TagDAO();
        ChapterDAO cDAO = new ChapterDAO();
        CommentDAO cmDAO = new CommentDAO();
        BookmarkDAO bDAO = new BookmarkDAO();
        ArrayList<TagDTO> tagList = tDAO.getAllTags();
        getServletContext().setAttribute("tagList", tagList);

        HttpSession session = request.getSession(false);
        //action = null -> display homepage.jsp
        if (action == null) {
            ArrayList<NovelDTO> novelList = (ArrayList<NovelDTO>) nDAO.getAll();
            request.setAttribute("novelList", novelList);
            request.getRequestDispatcher("Homepage.jsp").forward(request, response);
        } else if (action.equals("NovelInfo")) {
            String nid = request.getParameter("nid");
            ArrayList<TagDTO> tList = tDAO.getTagList(nid);
            //get novel by id
            NovelDTO ndto = nDAO.get(nid);
            //check if reader login or not to show bookmark
            AccountDTO user = (AccountDTO) session.getAttribute("user");
            LinkedList<ChapterDTO> chapterList = cDAO.getChapters(nid);
            //if reader logged in, check if they bookmark this novel or not
            if (user != null) {
                boolean isBookmarked = bDAO.isBookmarked(user.getUserName(), nid);
                request.setAttribute("bookmark", isBookmarked);
            }
            request.setAttribute("taglist", tList);
            request.setAttribute("chapterList", chapterList);
            request.setAttribute("novel", ndto);
            request.getRequestDispatcher("NovelInfo.jsp").forward(request, response);
        } else if (action.equals("SearchByTag")) {
            //get tagID to search for tag
            String tid = request.getParameter("tid");
            TagDTO tag = tDAO.getTag(tid);
            //if tag is found, find all novel with this tag
            if (tag != null) {
                ArrayList<NovelDTO> novelWithTag = (ArrayList<NovelDTO>) nDAO.searchByTag(tid);
                if (novelWithTag.size() > 0) {
                    request.setAttribute("size", novelWithTag.size());
                    request.setAttribute("novelList", novelWithTag);
                    request.setAttribute("tag", tDAO.getTag(tid));
                } else {
                    request.setAttribute("NoNovelError", "No novels could be found");
                }
                request.getRequestDispatcher("Homepage.jsp").forward(request, response);
            } else {
                request.setAttribute("TAGNOTFOUNDERROR", "Tag not found");
                request.getRequestDispatcher("Error.jsp").forward(request, response);
            }
        } else if (action.equals("SearchByName")) {
            String keyword = request.getParameter("keyword");
            //In case novel is in other languages than English, name are translated to an array of bytes with 
            //charsets "ISO_8859_1" rather than "UTF-8"
            keyword = new String(keyword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //find every novel which contains the keyword
            ArrayList<NovelDTO> nList = (ArrayList<NovelDTO>) nDAO.searchByName(keyword);
            if (nList.size() > 0) {
                //set information to request and redirect to homepage   
                request.setAttribute("novelList", nList);
                request.setAttribute("size", nList.size());
                request.setAttribute("keyword", keyword);
            } else {
                request.setAttribute("NoNovelNameError", "Sorry, we don't find any novel with keyword" + keyword);
            }
            request.getRequestDispatcher("Homepage.jsp").forward(request, response);
        } else if (session.getAttribute("user") != null) { //Actions only available if you logged in 
            if (action.equals("DeleteNovel")) {
                //first, delete the novel from database
                String nid = request.getParameter("nid");
                NovelDTO n = nDAO.get(nid);
                if (n != null) {
                    request.setAttribute("del_done", "Novel" + n.getNovelName() + "has been successfully deleted!");
                }
                if (n == null) {
                    request.setAttribute("NOVELNOTFOUND", "Could not find this novel");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                } else {
                    //if novel has any cover other than the default one, delete it
                    if (!n.getCoverURL().equals("defaultCover.png")) {
                        deleteCover(nid);
                    }
                    //then delete the novel from database
                    nDAO.delete(nid);
                    //finally delete the novel's files
                    deleteFile(nid);
                    ArrayList<NovelDTO> novelList = (ArrayList<NovelDTO>) nDAO.getAll();
                    request.setAttribute("novelList", novelList);
                    request.getRequestDispatcher("Homepage.jsp").forward(request, response);
                }
            }

        }
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public void deleteCover(String novelID) {
        //get location of the cover image
        String filepath = getServletContext().getRealPath("") + "/images/covers/" + novelID + ".jpg";
        File file = new File(filepath);
        //delete if exist, ignore if empty
        if (!file.exists()) {
            return;
        } else {
            file.delete();
        }
    }

    public void deleteFile(String novelID) throws IOException {
        String filepath = getServletContext().getRealPath("") + "/novels/" + novelID;
        File directory = new File(filepath);
        if (!directory.exists()) {
            return;
        } else {
            FileUtils.cleanDirectory(directory);
            directory.delete();
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
            try {
                processRequest(request, response);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NovelServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NovelServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NovelServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NovelServlet.class.getName()).log(Level.SEVERE, null, ex);
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

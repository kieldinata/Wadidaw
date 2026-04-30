package com.mlteam.wadidaw.controllers;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 * Servlet yang menangani konfigurasi awal aplikasi Wadidaw.
 * Digunakan untuk menginput dan menyimpan API Key yang diperlukan (TMDB dan Groq) ke dalam session.
 */
@WebServlet(name = "SetupServlet", urlPatterns = {"/setup"})
public class SetupServlet extends HttpServlet {
    /**
     * Menampilkan halaman formulir pengaturan.
     * Mengarahkan pengguna ke setup.jsp untuk melakukan input API Key.
     * @param request objek HttpServletRequest.
     * @param response objek HttpServletResponse.
     * @throws ServletException jika terjadi kesalahan pada servlet.
     * @throws IOException jika terjadi kesalahan I/O saat melakukan forward.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("setup.jsp").forward(request, response);
    }
    /**
     * Memproses penyimpanan API Key ke dalam session.
     * Mengambil parameter dari form, menyimpannya di session, dan mengalihkan pengguna ke halaman utama.
     * @param request objek HttpServletRequest yang berisi parameter groq_key dan tmdb_key.
     * @param response objek HttpServletResponse untuk melakukan redirect.
     * @throws ServletException jika terjadi kesalahan pada servlet.
     * @throws IOException jika terjadi kesalahan I/O saat melakukan redirect.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        String groqKey = request.getParameter("groq_key");
        String tmdbKey = request.getParameter("tmdb_key");
        HttpSession session = request.getSession();
        session.setAttribute("GROQ_API_KEY", groqKey);
        session.setAttribute("TMDB_API_KEY", tmdbKey);
        response.sendRedirect(request.getContextPath() + "/");
    }
}
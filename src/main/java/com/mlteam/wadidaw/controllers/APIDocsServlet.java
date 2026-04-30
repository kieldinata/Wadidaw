package com.mlteam.wadidaw.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Servlet yang berfungsi untuk menangani akses ke halaman dokumentasi API Wadidaw.
 * Mengarahkan endpoint "/api" menuju resource JSP yang sesuai.
 */
@WebServlet(name = "APIDocsServlet", urlPatterns = {"/api"})
public class APIDocsServlet extends HttpServlet {
    /**
     * Menangani permintaan HTTP GET.
     * Mengatur atribut tanggal pembaruan terakhir dan melakukan forward ke file JSP dokumentasi.
     * @param request objek request dari klien.
     * @param response objek response untuk mengirim data ke klien.
     * @throws ServletException jika terjadi kesalahan pada servlet.
     * @throws IOException jika terjadi kesalahan I/O.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("lastUpdated", "2026-04-23");
        request.getRequestDispatcher("/api_docs.jsp").forward(request, response);
    }
}
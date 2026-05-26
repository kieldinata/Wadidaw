package com.mlteam.wadidaw.controllers;
import com.mlteam.wadidaw.entities.*;
import com.mlteam.wadidaw.services.MediaServices;
import org.json.JSONObject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * Servlet yang menangani permintaan untuk menampilkan detail film.
 * Mengambil data film berdasarkan ID dari TMDB dan meneruskannya ke halaman detail.
 */
@WebServlet(name = "MovieDetailServlet", urlPatterns = {"/movie"})
public class MovieDetailServlet extends HttpServlet {
    /**
     * Menangani permintaan HTTP GET untuk mendapatkan rincian film.
     * Mengambil ID film dari parameter request, melakukan parsing data, 
     * dan mengisi detail tambahan seperti budget, revenue, dan trailer.
     * @param request objek HttpServletRequest yang berisi parameter id.
     * @param response objek HttpServletResponse untuk mengarahkan pengguna.
     * @throws ServletException jika terjadi kesalahan pada servlet.
     * @throws IOException jika terjadi kesalahan pada input atau output.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String TMDBKey = System.getenv("TMDB_API_KEY");
        if (idParam == null || TMDBKey == null) {
            response.sendRedirect("search");
            return;
        }
        try {
            String url = "https://api.themoviedb.org/3/movie/" + idParam + "?api_key=" + TMDBKey;
            String json = MediaServices.CallAPI(url);
            if (json != null) {
                JSONObject obj = new JSONObject(json);
                Media base = MediaServices.parseInitialMediaData(obj);
                Movies movie = (Movies) base;
                MediaServices.FillMovieDetails(movie, TMDBKey);
                request.setAttribute("movie", movie);
                request.getRequestDispatcher("detail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("search");
        }
    }
}
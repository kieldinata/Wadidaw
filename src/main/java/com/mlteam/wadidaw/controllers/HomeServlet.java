package com.mlteam.wadidaw.controllers;

import com.mlteam.wadidaw.entities.*;
import com.mlteam.wadidaw.services.MediaServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet yang menangani permintaan untuk halaman utama (Home).
 * Berfungsi untuk mengambil data discovery film dan acara TV dari TMDB API.
 */
@WebServlet(name = "HomeServlet", urlPatterns = {""})
public class HomeServlet extends HttpServlet {

    /**
     * Menangani permintaan HTTP GET untuk memuat konten halaman utama.
     * Melakukan pengecekan API Key di session dan mengambil data media untuk ditampilkan.
     * @param request objek HttpServletRequest.
     * @param response objek HttpServletResponse.
     * @throws ServletException jika terjadi kesalahan pada servlet.
     * @throws IOException jika terjadi kesalahan input/output.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String TMDBKey = System.getenv("TMDB_API_KEY");
        if (TMDBKey == null || TMDBKey.trim().isEmpty()) {
            response.sendError(500, "TMDB_API_KEY missing in environment variables.");
            return;
        }
        try {
            List<Media> discoverMovies = fetchFromTMDB("https://api.themoviedb.org/3/discover/movie?api_key=" + TMDBKey, "movie");
            List<Media> discoverTV = fetchFromTMDB("https://api.themoviedb.org/3/discover/tv?api_key=" + TMDBKey, "tv");
            request.setAttribute("discoverMovies", discoverMovies);
            request.setAttribute("discoverTV", discoverTV);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    /**
     * Mengambil daftar media dari endpoint TMDB yang ditentukan.
     * Mengonversi respon JSON menjadi list objek Media (Movies atau Shows).
     * @param url URL endpoint API TMDB.
     * @param type tipe media yang diambil (movie/tv).
     * @return List objek Media hasil parsing.
     * @throws Exception jika terjadi kesalahan saat pemanggilan API atau parsing data.
     */
    private List<Media> fetchFromTMDB(String url, String type) throws Exception {
        List<Media> list = new ArrayList<>();
        String jsonResponse = MediaServices.CallAPI(url);
        if (jsonResponse == null || jsonResponse.isEmpty()) return list;
        JSONArray results = new JSONObject(jsonResponse).optJSONArray("results");
        if (results != null) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                Media media = MediaServices.parseInitialMediaData(obj);
                if (media != null) {
                    list.add(media);
                }
            }
        }
        return list;
    }
}
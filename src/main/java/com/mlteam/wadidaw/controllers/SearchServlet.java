package com.mlteam.wadidaw.controllers;

import com.mlteam.wadidaw.entities.*;
import com.mlteam.wadidaw.services.MediaServices;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet yang menangani fitur pencarian media (Film dan TV Show).
 * Mengintegrasikan hasil dari TMDB API dan dukungan AI (Groq) jika hasil pencarian kosong.
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    /**
     * Menangani permintaan HTTP GET untuk pencarian.
     * Memastikan API Key tersedia di session sebelum memproses pencarian.
     * @param request objek HttpServletRequest.
     * @param response objek HttpServletResponse.
     * @throws ServletException jika terjadi kesalahan servlet.
     * @throws IOException jika terjadi kesalahan I/O.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groqKey = System.getenv("GROQ_API_KEY");
        String TMDBKey = System.getenv("TMDB_API_KEY");
        if (groqKey == null || TMDBKey == null || groqKey.trim().isEmpty() || TMDBKey.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "API Keys are missing in environment variables.");
            return;
        }
        handleSearchMedia(request, response, TMDBKey, groqKey);
    }

    /**
     * Mengelola logika pencarian media ke TMDB API.
     * Menggabungkan hasil pencarian Movies dan TV Shows, serta melakukan fallback ke AI jika perlu.
     * @param request objek HttpServletRequest.
     * @param response objek HttpServletResponse.
     * @param movieApi kunci API TMDB.
     * @param aiApi kunci API Groq AI.
     * @throws ServletException jika terjadi kesalahan servlet.
     * @throws IOException jika terjadi kesalahan I/O.
     */
    private void handleSearchMedia(HttpServletRequest request, HttpServletResponse response, String movieApi, String aiApi) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Media> mediaList = new ArrayList<>();
        try {
            if (query == null || query.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            String movieUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + movieApi + "&query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
            String movieJson = MediaServices.CallAPI(movieUrl);
            JSONArray movieResults = new JSONObject(movieJson).optJSONArray("results");
            String tvUrl = "https://api.themoviedb.org/3/search/tv?api_key=" + movieApi + "&query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
            String tvJson = MediaServices.CallAPI(tvUrl);
            JSONArray tvResults = new JSONObject(tvJson).optJSONArray("results");
            if ((movieResults == null || movieResults.length() == 0) && (tvResults == null || tvResults.length() == 0)) {
                String aiResultTitle = MediaServices.GenerateQueryWithAI(query, aiApi);
                if (aiResultTitle != null && !aiResultTitle.trim().equalsIgnoreCase(query.trim())) {
                    response.sendRedirect(request.getContextPath() + "/search?query=" + URLEncoder.encode(aiResultTitle.trim(), StandardCharsets.UTF_8));
                    return;
                }
            }
            if (movieResults != null) {
                for (int i = 0; i < movieResults.length(); i++) {
                    JSONObject item = movieResults.getJSONObject(i);
                    if (isValid(item)) {
                        mediaList.add(MediaServices.parseInitialMediaData(item));
                    }
                }
            }
            if (tvResults != null) {
                for (int i = 0; i < tvResults.length(); i++) {
                    JSONObject item = tvResults.getJSONObject(i);
                    if (isValid(item)) {
                        mediaList.add(MediaServices.parseInitialMediaData(item));
                    }
                }
            }
            mediaList.sort((a, b) -> Double.compare(b.getPopularity(), a.getPopularity()));
            request.setAttribute("mediaList", mediaList);
            request.getRequestDispatcher("search.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Memvalidasi apakah item media layak ditampilkan.
     * Mengecek keberadaan poster_path untuk menjaga kualitas tampilan UI.
     * @param item objek JSONObject media dari TMDB.
     * @return true jika item memiliki poster yang valid, false jika tidak.
     */
    private boolean isValid(JSONObject item) {
        String poster = item.optString("poster_path");
        return poster != null && !poster.equals("null") && !poster.isEmpty();
    }
}
package com.mlteam.wadidaw.controllers;
import com.mlteam.wadidaw.entities.Episodes;
import com.mlteam.wadidaw.entities.Seasons;
import com.mlteam.wadidaw.services.MediaServices;
import org.json.JSONArray;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
/**
 * Servlet yang menangani permintaan data episode untuk season tertentu secara asinkron.
 * Menghasilkan output dalam format JSON untuk dikonsumsi oleh front-end (AJAX).
 */
@WebServlet(name = "SeasonServlet", urlPatterns = {"/season"})
public class SeasonServlet extends HttpServlet {
    /**
     * Menangani permintaan HTTP GET untuk mengambil daftar episode.
     * Mengambil detail season dari TMDB API dan mengonversinya menjadi array JSON.
     * @param request objek HttpServletRequest yang berisi parameter id dan season_number.
     * @param response objek HttpServletResponse untuk menulis output JSON.
     * @throws ServletException jika terjadi kesalahan pada servlet.
     * @throws IOException jika terjadi kesalahan pada saat menulis response.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String showId = request.getParameter("id");
        String seasonNumber = request.getParameter("season_number");
        String TMDBKey = System.getenv("TMDB_API_KEY");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONArray jsonArray = new JSONArray();
        try {
            if (showId != null && seasonNumber != null && TMDBKey != null) {
                Seasons season = new Seasons();
                season.setSeason_number(Integer.parseInt(seasonNumber));
                MediaServices.FillSeasonDetails(Integer.parseInt(showId), season, TMDBKey);
                List<Episodes> episodes = season.getEpisodes();
                if (episodes != null) {
                    for (Episodes ep : episodes) {
                        JSONObject obj = new JSONObject();
                        obj.put("episode_number", ep.getEpisode_number());
                        String epName = (ep.getName() != null && !ep.getName().trim().isEmpty()) ? ep.getName() : "Episode " + ep.getEpisode_number();
                        obj.put("name", epName);
                        obj.put("overview", (ep.getOverview() != null && !ep.getOverview().isEmpty()) ? ep.getOverview() : "Deskripsi tidak tersedia.");
                        obj.put("still_path", ep.getStill_path());
                        jsonArray.put(obj);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonArray.toString());
            out.flush();
        }
    }
}
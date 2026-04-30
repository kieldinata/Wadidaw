package com.mlteam.wadidaw.services;
import com.mlteam.wadidaw.entities.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.net.*;
import java.util.List;
import java.util.ArrayList;
/**
 * Service class yang menangani logika bisnis utama aplikasi Wadidaw.
 * Mengelola interaksi dengan API TMDB (Movie/TV) dan API Groq (AI), 
 * serta melakukan parsing JSON ke objek entity.
 */
public class MediaServices {
    /**
     * Melakukan parsing data dasar dari objek JSON TMDB ke objek Media (Movies atau Shows).
     * @param item objek JSONObject tunggal dari hasil API TMDB.
     * @return objek Media (Movies/Shows) yang telah terisi data awal.
     */
    public static Media parseInitialMediaData(JSONObject item) {
        Media media;
        if (item.has("title")) {
            media = new Movies(); 
        } else {
            media = new Shows();
        }
        media.setId(item.optInt("id"));
        media.setTitle(item.has("title") ? item.optString("title") : item.optString("name"));
        media.setOriginal_title(item.has("original_title") ? item.optString("original_title") : item.optString("original_name"));
        media.setOverview(item.optString("overview"));
        String backdrop = item.optString("backdrop_path");
        if (backdrop != null && !backdrop.equals("null") && !backdrop.isEmpty()) {
            media.setBackdrop_path("https://image.tmdb.org/t/p/original" + backdrop);
        }
        String poster = item.optString("poster_path");
        if (poster != null && !poster.equals("null") && !poster.isEmpty()) {
            media.setPoster_path("https://image.tmdb.org/t/p/w500" + poster);
        }
        media.setRelease_date(item.has("release_date") ? item.optString("release_date") : item.optString("first_air_date"));
        media.setVote_average(item.optDouble("vote_average"));
        media.setVote_count(item.optInt("vote_count"));
        media.setPopularity(item.optDouble("popularity"));
        media.setOriginal_language(item.optString("original_language"));
        return media;
    }
    /**
     * Mengambil dan mengisi detail tambahan untuk objek Movies.
     * @param movie objek Movies yang akan dilengkapi datanya.
     * @param apiKey kunci API TMDB.
     */
    public static void FillMovieDetails(Movies movie, String apiKey) {
        try {
            String detailUrl = "https://api.themoviedb.org/3/movie/" + movie.getId() + "?api_key=" + apiKey;
            String response = CallAPI(detailUrl);
            if (response == null) return;
            JSONObject detailObj = new JSONObject(response);
            movie.setTagline(detailObj.optString("tagline"));
            movie.setBudget(detailObj.optInt("budget"));
            movie.setRevenue(detailObj.optLong("revenue"));
            movie.setStatus(detailObj.optString("status"));
            movie.setRuntime(detailObj.optInt("runtime"));
            movie.setGenres(parseJoinNames(detailObj.optJSONArray("genres")));
            movie.setSpoken_languages(parseJoinNames(detailObj.optJSONArray("spoken_languages")));
            movie.setProduction_companies(parseJoinNames(detailObj.optJSONArray("production_companies")));
            movie.setProduction_countries(parseJoinNames(detailObj.optJSONArray("production_countries")));
            movie.setAdult(detailObj.optBoolean("adult"));
            movie.setTrailer_key(fetchVideoKey("movie", movie.getId(), apiKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Mengambil dan mengisi detail utama untuk objek Shows (TV Show).
     * @param show objek Shows yang akan dilengkapi datanya.
     * @param apiKey kunci API TMDB.
     */
    public static void FillShowDetails(Shows show, String apiKey) {
        try {
            String url = "https://api.themoviedb.org/3/tv/" + show.getId() + "?api_key=" + apiKey;
            String res = CallAPI(url);
            if (res == null || res.isEmpty()) return;
            JSONObject obj = new JSONObject(res);
            show.setStatus(obj.optString("status"));
            show.setGenres(parseJoinNames(obj.optJSONArray("genres")));
            show.setNumber_of_seasons(obj.optInt("number_of_seasons"));
            show.setTrailer_key(fetchVideoKey("tv", show.getId(), apiKey));
            JSONArray seasonsArray = obj.optJSONArray("seasons");
            List<Seasons> seasonList = new ArrayList<>();
            if (seasonsArray != null) {
                for (int i = 0; i < seasonsArray.length(); i++) {
                    JSONObject sObj = seasonsArray.getJSONObject(i);
                    if (sObj.optInt("season_number") == 0) continue;
                    Seasons season = new Seasons();
                    season.setId(sObj.optInt("id"));
                    season.setSeason_number(sObj.optInt("season_number"));
                    season.setName(sObj.optString("name"));
                    season.setEpisode_count(sObj.optInt("episode_count"));
                    String sPoster = sObj.optString("poster_path");
                    if (sPoster != null && !sPoster.equals("null") && !sPoster.isEmpty()) {
                        season.setPoster_path("https://image.tmdb.org/t/p/w500" + sPoster);
                    }
                    seasonList.add(season);
                }
            }
            show.setSeasons(seasonList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Mengambil detail lengkap episode untuk season tertentu.
     * @param showId ID acara TV.
     * @param season objek Seasons yang akan diisi daftar episodenya.
     * @param TMDBKey kunci API TMDB.
     */
    public static void FillSeasonDetails(int showId, Seasons season, String TMDBKey) {
        String url = "https://api.themoviedb.org/3/tv/" + showId + "/season/" + season.getSeason_number() + "?api_key=" + TMDBKey;
        try {
            String response = CallAPI(url);
            if (response == null) return;
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("episodes")) {
                JSONArray episodesArray = jsonObject.getJSONArray("episodes");
                List<Episodes> episodeList = new ArrayList<>();
                for (int i = 0; i < episodesArray.length(); i++) {
                    JSONObject epJson = episodesArray.getJSONObject(i);
                    Episodes ep = new Episodes();
                    ep.setId(epJson.optInt("id"));
                    ep.setEpisode_number(epJson.optInt("episode_number"));
                    String name = epJson.optString("name", "").trim();
                    ep.setName(name.isEmpty() ? "Episode " + ep.getEpisode_number() : name);
                    String overview = epJson.optString("overview", "").trim();
                    ep.setOverview(overview.isEmpty() ? "Deskripsi tidak tersedia." : overview);
                    String path = epJson.optString("still_path");
                    if (path != null && !path.equals("null") && !path.isEmpty()) {
                        ep.setStill_path("https://image.tmdb.org/t/p/w500" + path);
                    } else {
                        ep.setStill_path("https://via.placeholder.com/500x281?text=No+Image"); 
                    }
                    episodeList.add(ep);
                }
                season.setEpisodes(episodeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Melakukan pemetaan data JSON episode ke objek entity Episodes secara spesifik.
     * @param ep objek Episodes tujuan.
     * @param obj objek JSONObject data episode.
     */
    public static void FillEpisodeDetails(Episodes ep, JSONObject obj) {
        ep.setId(obj.optInt("id"));
        int epNum = obj.optInt("episode_number");
        ep.setEpisode_number(epNum);
        String name = obj.optString("name", "").trim();
        ep.setName(name.isEmpty() ? "Episode " + epNum : name);
        String overview = obj.optString("overview", "").trim();
        ep.setOverview(overview.isEmpty() ? "Deskripsi tidak tersedia." : overview);
        String path = obj.optString("still_path");
        if (path != null && !path.equals("null") && !path.isEmpty()) {
            ep.setStill_path("https://image.tmdb.org/t/p/w500" + path);
        } else {
            ep.setStill_path("");
        }
    }
    /**
     * Mencari kunci video YouTube untuk trailer media.
     * @param type jenis media (movie/tv).
     * @param id ID media di TMDB.
     * @param apiKey kunci API TMDB.
     * @return String berupa kunci video YouTube.
     * @throws Exception jika terjadi kesalahan koneksi.
     */
    private static String fetchVideoKey(String type, int id, String apiKey) throws Exception {
        String url = "https://api.themoviedb.org/3/" + type + "/" + id + "/videos?api_key=" + apiKey;
        String json = CallAPI(url);
        if (json == null) return null;
        JSONArray videos = new JSONObject(json).optJSONArray("results");
        if (videos == null) return null;
        String fallback = null;
        for (int i = 0; i < videos.length(); i++) {
            JSONObject v = videos.getJSONObject(i);
            if ("YouTube".equalsIgnoreCase(v.optString("site")) && "Trailer".equalsIgnoreCase(v.optString("type"))) {
                if (v.optBoolean("official")) return v.optString("key");
                if (fallback == null) fallback = v.optString("key");
            }
        }
        return fallback;
    }
    /**
     * Menggabungkan nama-nama atribut dari JSONArray menjadi satu String terpisah koma.
     * @param arr JSONArray yang berisi kumpulan objek dengan atribut "name".
     * @return String gabungan nama.
     */
    private static String parseJoinNames(JSONArray arr) {
        if (arr == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length(); i++) {
            sb.append(arr.getJSONObject(i).optString("name"));
            if (i < arr.length() - 1) sb.append(", ");
        }
        return sb.toString();
    }
    /**
     * Melakukan koneksi HTTP GET ke URL yang ditentukan.
     * @param urlString alamat endpoint API.
     * @return String berupa respon JSON mentah.
     * @throws Exception jika koneksi gagal atau timeout.
     */
    public static String CallAPI(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        if (conn.getResponseCode() != 200) return null;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = rd.readLine()) != null) sb.append(line);
        } finally {
            conn.disconnect();
        }
        return sb.toString();
    }
    /**
     * Berinteraksi dengan Groq AI untuk melakukan pencarian semantik jika pencarian TMDB gagal.
     * @param failedQuery query asli dari pengguna.
     * @param apiKey kunci API Groq.
     * @return String judul film hasil saran AI.
     */
    public static String GenerateQueryWithAI(String failedQuery, String apiKey) {
        String endpoint = "https://api.groq.com/openai/v1/chat/completions";
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setDoOutput(true);
            JSONObject payload = new JSONObject();
            payload.put("model", "llama-3.3-70b-versatile");
            JSONArray messages = new JSONArray();
            messages.put(new JSONObject().put("role", "user").put("content", "Balas HANYA dengan 1 judul film yang paling mirip dengan deskripsi ini: " + failedQuery));
            payload.put("messages", messages);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
            }
            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
                JSONObject resObj = new JSONObject(response.toString());
                return resObj.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content").trim().replace("\"", "");
            }
        } catch (Exception e) {
            System.err.println("AI Service Error: " + e.getMessage());
        }
        return failedQuery;
    }
}
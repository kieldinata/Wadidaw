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
 * Servlet yang menangani permintaan untuk menampilkan detail acara TV (Shows).
 * Mengelola pengambilan data teknis dari API TMDB dan menyajikannya ke halaman detail_tv.jsp.
 */
@WebServlet(name = "ShowDetailServlet", urlPatterns = {"/show"})
public class ShowDetailServlet extends HttpServlet {
    /**
     * Menangani permintaan HTTP GET untuk rincian acara TV.
     * Mengambil ID dari parameter, melakukan casting objek Media menjadi Shows,
     * dan melengkapi data seperti musim (seasons) serta trailer.
     * @param request objek HttpServletRequest yang membawa parameter id.
     * @param response objek HttpServletResponse untuk navigasi halaman.
     * @throws ServletException jika terjadi kesalahan internal servlet.
     * @throws IOException jika terjadi kegagalan pada proses input/output.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String TMDBKey = (String) request.getSession().getAttribute("TMDB_API_KEY");
        if (idParam == null || TMDBKey == null) {
            response.sendRedirect("search");
            return;
        }
        try {
            String url = "https://api.themoviedb.org/3/tv/" + idParam + "?api_key=" + TMDBKey;
            String json = MediaServices.CallAPI(url);
            if (json != null) {
                JSONObject obj = new JSONObject(json);
                Media base = MediaServices.parseInitialMediaData(obj);
                Shows show = (Shows) base;
                MediaServices.FillShowDetails(show, TMDBKey);
                request.setAttribute("show", show);
                request.getRequestDispatcher("detail_tv.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("search");
        }
    }
}
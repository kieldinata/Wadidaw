<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.mlteam.wadidaw.entities.Movies" %>
<!DOCTYPE html>
<html>
<head>
    <title><%= ((Movies)request.getAttribute("movie")).getTitle() %></title>
    <link rel="icon" type="image/png" href="Resources/wadidaw-logo-white.png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: white;
            background: #0b0b0b;
            overflow-x: hidden;
        }

        .backdrop {
            min-height: 100vh;
            background-size: cover;
            background-position: center;
            position: relative;
            display: flex;
            align-items: center;
        }

        .overlay {
            position: absolute;
            inset: 0;
            background: linear-gradient(to right, rgba(0,0,0,0.9) 30%, rgba(0,0,0,0.4) 70%, rgba(0,0,0,0.1) 100%);
        }

        .content {
            position: relative;
            z-index: 5;
            padding: 100px 60px;
            max-width: 800px;
        }

        .tagline {
            font-style: italic;
            color: #aaa;
            font-size: 1.2rem;
            margin-bottom: 10px;
            display: block;
        }

        h1 {
            font-size: clamp(32px, 6vw, 56px);
            margin: 0 0 15px 0;
            line-height: 1.1;
        }

        .meta-row {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            align-items: center;
            margin-bottom: 20px;
            font-weight: bold;
            color: #46d369;
        }

        .badge {
            background: rgba(255,255,255,0.2);
            padding: 2px 8px;
            border-radius: 4px;
            color: white;
            font-size: 0.9rem;
        }

        .overview {
            font-size: 1.1rem;
            line-height: 1.6;
            color: #efefef;
            margin-bottom: 30px;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.8);
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            background: rgba(0,0,0,0.5);
            padding: 20px;
            border-radius: 8px;
            border-left: 4px solid #e50914;
        }

        .info-item label {
            display: block;
            color: #888;
            font-size: 0.85rem;
            text-transform: uppercase;
            margin-bottom: 5px;
        }

        .info-item span {
            font-size: 1rem;
            color: #fff;
        }

        .btn-play {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            padding: 12px 25px;
            background: white;
            color: black;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            font-size: 1.1rem;
            font-weight: bold;
            margin-bottom: 40px;
            transition: 0.2s;
        }

        .btn-play:hover {
            background: rgba(255,255,255,0.8);
        }

        .btn-back {
            position: fixed;
            top: 20px;
            left: 20px;
            width: 45px;
            height: 45px;
            border-radius: 50%;
            border: none;
            color: white;
            background: rgba(0,0,0,0.6);
            cursor: pointer;
            z-index: 1000;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .btn-back:hover { background: #e50914; }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            inset: 0;
            background: rgba(0,0,0,0.95);
            justify-content: center;
            align-items: center;
        }

        .modal-content { width: 90%; max-width: 1000px; aspect-ratio: 16/9; }
        iframe { width: 100%; height: 100%; border: none; }
        .close { position: absolute; top: 30px; right: 40px; font-size: 40px; cursor: pointer; }

        @media (max-width: 768px) {
            .backdrop { align-items: flex-end; }
            .overlay { background: linear-gradient(to top, black 40%, transparent 100%); }
            .content { padding: 40px 20px; }
            .info-grid { grid-template-columns: 1fr 1fr; }
        }
    </style>
</head>
<body>

<%
    Movies movie = (Movies) request.getAttribute("movie");
    String query = request.getParameter("query");
    java.text.NumberFormat fmt = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
%>

<button onclick="goHome()" class="btn-back">✕</button>

<div class="backdrop" style="background-image: url('<%= movie.getBackdrop_path() %>')">
    <div class="overlay"></div>

    <div class="content">
        <% if(movie.getTagline() != null && !movie.getTagline().isEmpty()) { %>
            <span class="tagline">"<%= movie.getTagline() %>"</span>
        <% } %>
        
        <h1><%= movie.getTitle() %></h1>

        <div class="meta-row">
            <span>⭐ <%= movie.getVote_average() %></span>
            <span><%= movie.getRelease_date().split("-")[0] %></span>
            <span class="badge"><%= movie.getRuntime() %> min</span>
            <span class="badge" style="border: 1px solid #777;"><%= movie.getStatus() %></span>
            <% if(movie.isAdult()) { %>
                <span class="badge" style="background: red;">18+</span>
            <% } %>
        </div>

        <p class="overview"><%= movie.getOverview() %></p>

        <div class="info-grid">
            <div class="info-item">
                <label>Genres</label>
                <span><%= movie.getGenres() != null ? movie.getGenres() : "-" %></span>
            </div>
            <div class="info-item">
                <label>Budget</label>
                <span><%= movie.getBudget() > 0 ? fmt.format(movie.getBudget()) : "N/A" %></span>
            </div>
            <div class="info-item">
                <label>Revenue</label>
                <span><%= movie.getRevenue() > 0 ? fmt.format(movie.getRevenue()) : "N/A" %></span>
            </div>
            <div class="info-item">
                <label>Languages</label>
                <span><%= movie.getSpoken_languages() %></span>
            </div>
            <div class="info-item">
                <label>Production</label>
                <span><%= movie.getProduction_companies() %></span>
            </div>
            <div class="info-item">
                <label>Origin</label>
                <span><%= movie.getProduction_countries() %></span>
            </div>
        </div>

        <% if (movie.getTrailer_key() != null) { %>
            <button class="btn-play" onclick="openTrailer()">
                <svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor"><path d="M8 5v14l11-7z"/></svg>
                Play Trailer
            </button>
        <% } %>
    </div>
</div>

<div id="trailerModal" class="modal">
    <span class="close" onclick="closeTrailer()">×</span>
    <div class="modal-content">
        <iframe id="trailerFrame" src="" allow="autoplay; encrypted-media" allowfullscreen></iframe>
    </div>
</div>

<script>
    function openTrailer() {
        const modal = document.getElementById("trailerModal");
        const frame = document.getElementById("trailerFrame");
        const trailerUrl = "https://www.youtube.com/embed/<%= movie.getTrailer_key() %>?autoplay=1";

        frame.contentWindow.location.replace(trailerUrl);

        modal.style.display = "flex";
        document.body.style.overflow = "hidden";
    }

    function closeTrailer() {
        const modal = document.getElementById("trailerModal");
        const frame = document.getElementById("trailerFrame");
        frame.contentWindow.location.replace("about:blank");
        modal.style.display = "none";
        document.body.style.overflow = "auto";
    }
    
    function goHome() {
        const backUrl = document.referrer;
        if (backUrl && backUrl !== "") {
            window.location.href = backUrl;
        } else {
            window.location.href = "search";
        }
    }

    window.onclick = function(event) {
        if (event.target.id === "trailerModal") closeTrailer();
    }
</script>

</body>
</html>
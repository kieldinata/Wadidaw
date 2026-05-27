<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mlteam.wadidaw.entities.Media" %>

<html>
<head>
    <title>Wadidaw - Discover</title>
    <link rel="icon" type="image/png" href="Resources/wadidaw-logo-white.png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { margin:0; background:#0b0b0b; color:#e5e5e5; font-family:Arial, sans-serif; overflow-x: hidden; }

        .header-container {
            position: absolute;
            top: 25px;
            left: 40px;
            z-index: 100;
        }

        .logo-wadidaw {
            height: 75px;
            width: auto;
            object-fit: contain;
        }

        .top-nav {
            position: absolute;
            top: 45px;
            right: 40px;
            z-index: 100;
        }

        .nav-link {
            color: #e5e5e5;
            text-decoration: none;
            font-size: 0.9rem;
            font-weight: bold;
            letter-spacing: 1px;
            text-transform: uppercase;
            border: 1px solid rgba(255,255,255,0.3);
            padding: 8px 15px;
            border-radius: 5px;
            transition: 0.3s;
        }

        .nav-link:hover {
            background: #e50914;
            border-color: #e50914;
            color: white;
        }

        .search-section {
            height: 65vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background: radial-gradient(circle at center, #1a1a1a 0%, #0b0b0b 100%);
            padding: 0 20px;
        }

        .search-section h1 {
            font-size: 3.5rem;
            margin-bottom: 30px;
            color: #ffffff;
            letter-spacing: 6px;
            font-weight: bold;
            text-shadow: 0 0 25px rgba(229, 9, 20, 0.4);
        }

        .main-search-bar {
            width: 100%;
            max-width: 800px;
        }

        .main-search-bar textarea {
            width: 100%;
            height: 120px; 
            padding: 20px 25px;
            font-size: 1.1rem;
            font-family: Arial, sans-serif;
            border: none;
            border-radius: 15px;
            background: rgba(255, 255, 255, 0.08);
            color: white;
            box-shadow: 0 15px 35px rgba(0,0,0,0.6);
            outline: none;
            border: 1px solid rgba(255, 255, 255, 0.15);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            resize: none; 
        }

        .main-search-bar textarea:focus {
            background: rgba(255, 255, 255, 0.12);
            border-color: #e50914;
            box-shadow: 0 0 25px rgba(229, 9, 20, 0.2);
            transform: translateY(-2px);
        }

        .content-area {
            margin-top: -60px;
            padding-bottom: 50px;
        }

        .section-title {
            padding: 0 40px;
            margin: 20px 0 5px 0;
            font-size: 1.4rem;
            font-weight: bold;
        }

        .movie-row {
            display: flex;
            overflow-x: auto;
            padding: 20px 40px;
            gap: 15px;
            position: relative;
            z-index: 10;
            scroll-behavior: smooth;
            scrollbar-width: none; 
            min-height: 320px;
        }

        .movie-row::-webkit-scrollbar { display: none; }

        .movie-card {
            flex: 0 0 170px; 
            width: 170px;
            transition: transform 0.3s;
            position: relative;
        }

        .movie-card:hover { transform: scale(1.08); z-index: 11; }

        .movie-card img {
            width: 100%;
            height: 250px;
            object-fit: cover;
            border-radius: 10px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.6);
        }

        .movie-card p {
            font-size: 13px;
            margin-top: 10px;
            text-align: center;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        a { text-decoration: none; color: inherit; }

        @media (max-width: 768px) {
            .header-container { left: 20px; top: 15px; }
            .logo-wadidaw { height: 50px; }
            .top-nav { top: 25px; right: 20px; }
            .nav-link { font-size: 0.7rem; padding: 5px 10px; }
            .section-title { padding: 0 20px; }
            .movie-row { padding: 20px 20px; }
            .search-section h1 { font-size: 2.2rem; }
            .main-search-bar textarea { height: 100px; font-size: 1rem; }
        }
    </style>
</head>
<body>

<div class="header-container">
    <a href="${pageContext.request.contextPath}/">
        <img src="https://github.com/user-attachments/assets/59f4440a-a936-4364-b2a6-825c4f9021f9" alt="Wadidaw Logo" class="logo-wadidaw">
    </a>
</div>

<div class="top-nav">
    <a href="${pageContext.request.contextPath}/api" class="nav-link">API Docs</a>
</div>

<div class="search-section">
    <h1>What Did I Watch?</h1>
    <div class="main-search-bar">
        <form action="search" method="get" id="searchForm">
            <textarea name="query" 
                      placeholder="Search movies, TV shows or describe the plot in detail..." 
                      autocomplete="off"></textarea>
        </form>
    </div>
</div>

<div class="content-area">
    
    <div class="section-title">Trending Movies</div>
    <div class="movie-row">
        <% 
            List<Media> discoverMovies = (List<Media>) request.getAttribute("discoverMovies");
            if(discoverMovies != null) {
                for(Media m : discoverMovies) { 
        %>
            <a href="movie?id=<%= m.getId() %>">
                <div class="movie-card">
                    <img src="<%= m.getPoster_path() %>" alt="<%= m.getTitle() %>">
                    <p><%= m.getTitle() %></p>
                </div>
            </a>
        <%      } 
            } 
        %>
    </div>

    <div class="section-title">Popular TV Series</div>
    <div class="movie-row">
        <% 
            List<Media> discoverTV = (List<Media>) request.getAttribute("discoverTV");
            if(discoverTV != null) {
                for(Media m : discoverTV) { 
        %>
            <a href="show?id=<%= m.getId() %>">
                <div class="movie-card">
                    <img src="<%= m.getPoster_path() %>" alt="<%= m.getTitle() %>">
                    <p><%= m.getTitle() %></p>
                </div>
            </a>
        <%      } 
            } 
        %>
    </div>

</div>

<script>
    const tx = document.querySelector('textarea');
    const form = document.getElementById('searchForm');

    tx.addEventListener('keydown', (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            form.submit();
        }
    });

    document.querySelectorAll('.movie-row').forEach(row => {
        row.addEventListener('wheel', (evt) => {
            if (evt.deltaY !== 0) {
                evt.preventDefault();
                row.scrollLeft += evt.deltaY;
            }
        }, { passive: false });
    });
</script>

</body>
</html>
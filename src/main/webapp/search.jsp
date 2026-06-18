<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mlteam.wadidaw.entities.Media" %>
<%@ page import="com.mlteam.wadidaw.entities.Movies" %>
<%@ page import="com.mlteam.wadidaw.entities.Shows" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html>
<head>
    <title>Wadidaw - Search</title>
    <link rel="icon" type="image/png" href="Resources/wadidaw-logo-white.png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { margin:0; background:#0b0b0b; color:#e5e5e5; font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; overflow-x: hidden; }

        .header-container {
            position: fixed;
            top: 25px;
            left: 40px;
            z-index: 110;
        }

        .logo-wadidaw {
            height: 75px;
            width: auto;
            object-fit: contain;
        }

        .header { 
            background: linear-gradient(to bottom, rgba(0, 0, 0, 0.8) 0%, rgba(0, 0, 0, 0) 100%); 
            padding: 20px 40px; 
            display: flex; 
            justify-content: flex-end;
            align-items: center; 
            position: fixed; 
            top: 0; 
            left: 0; 
            right: 0; 
            z-index: 100; 
            transition: background 0.3s; 
        }

        .search-box input { 
            padding: 10px 20px; 
            border: 1px solid rgba(255, 255, 255, 0.2); 
            border-radius: 10px; 
            background: rgba(255,255,255,0.1); 
            color: white; 
            width: 250px; 
            outline: none; 
            backdrop-filter: blur(5px);
            transition: all 0.3s;
        }
        
        .search-box input:focus {
            border-color: #e50914;
            background: rgba(255,255,255,0.15);
        }

        .hero { 
            height: 75vh; 
            background-size: cover; 
            background-position: center top; 
            position: relative; 
            display: flex; 
            align-items: flex-end; 
            transition: background-image 0.4s ease-in-out; 
        }
        
        .hero-overlay { 
            padding: 0 40px 200px 40px; 
            width: 100%; 
            background: linear-gradient(to top, #0b0b0b 0%, rgba(11,11,11,0.5) 40%, transparent 100%); 
            box-sizing: border-box; 
        }

        #hero-title { 
            font-size: clamp(1.8rem, 6vw, 3.5rem); 
            margin: 0; 
            text-shadow: 2px 2px 15px rgba(0,0,0,0.9); 
            max-width: 80%; 
            font-weight: bold; 
            color: #ffffff;
            letter-spacing: 2px;
        }

        .movie-row { 
            display: flex; 
            overflow-x: auto; 
            padding: 20px 40px; 
            gap: 15px; 
            margin-top: -150px; 
            position: relative; 
            z-index: 10; 
            scroll-behavior: smooth; 
            scrollbar-width: none; 
            min-height: 300px; 
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

        .type-badge { 
            position: absolute; 
            top: 10px; 
            right: 10px; 
            background: rgba(229, 9, 20, 0.9); 
            color: white; 
            font-size: 9px; 
            font-weight: bold; 
            padding: 3px 6px; 
            border-radius: 4px; 
            text-transform: uppercase; 
        }

        .movie-card p { 
            font-size: 13px; 
            margin-top: 10px; 
            text-align: center; 
            white-space: nowrap; 
            overflow: hidden; 
            text-overflow: ellipsis; 
        }

        .empty-state { flex: 1; text-align: center; padding: 100px 0; min-width: 100%; color: #888; }
        
        a { text-decoration:none; color:inherit; }

        @media (max-width: 768px) {
            .header-container { left: 20px; top: 15px; }
            .logo-wadidaw { height: 50px; }
            .search-box input { width: 150px; }
            .movie-row { padding: 20px 20px; }
        }
    </style>
</head>
<body>

<%
    List<Media> mediaList = (List<Media>) request.getAttribute("mediaList");
    String currentQuery = request.getParameter("query");
    Media hero = (mediaList != null && !mediaList.isEmpty()) ? mediaList.get(0) : null;
%>

<div class="header-container">
    <a href="${pageContext.request.contextPath}/">
        <img src="https://github.com/user-attachments/assets/59f4440a-a936-4364-b2a6-825c4f9021f9" alt="Wadidaw Logo" class="logo-wadidaw">
    </a>
</div>

<div class="header" id="mainHeader">
    <form class="search-box" action="search" method="get">
        <input type="text" name="query" placeholder="Search..." 
               value="<%= currentQuery != null ? currentQuery : "" %>" autocomplete="off">
    </form>
</div>

<% if(hero != null) { %>
<div id="hero-banner" class="hero" style="background-image:url('<%= hero.getBackdrop_path() %>')">
    <div class="hero-overlay">
        <h1 id="hero-title"><%= hero.getTitle() %></h1>
    </div>
</div>
<% } else { %>
    <div class="hero" style="background:#0b0b0b; height: 40vh;"></div>
<% } %>

<div class="movie-row" id="scrollRow">
<% 
    if(mediaList != null && !mediaList.isEmpty()){ 
        for(Media m : mediaList){ 
            
            String detailUrl = (m instanceof Shows) ? "show" : "movie";
            String label = (m instanceof Shows) ? "TV Show" : "Movie";
%>
    <a href="<%= detailUrl %>?id=<%= m.getId() %>"
       onmouseenter="previewHero('<%= m.getBackdrop_path() %>', '<%= m.getTitle().replace("'", "\\'") %>')">
        <div class="movie-card">
            <img src="<%= m.getPoster_path() %>" alt="<%= m.getTitle() %>">
            <span class="type-badge"><%= label %></span>
            <p><%= m.getTitle() %></p>
        </div>
    </a>
<% 
        }
    } else { 
%>
    <div class="empty-state">
        <div style="font-size: 50px; margin-bottom: 15px;">🍿</div>
        <h3 style="color: white; margin: 0;">No results found.</h3>
        <p>Try different keywords or a simple description.</p>
    </div>
<% } %>
</div>

<script>
function previewHero(backdropPath, title) {
    const heroBanner = document.getElementById('hero-banner');
    const heroTitle = document.getElementById('hero-title');
    if (heroBanner && backdropPath && backdropPath !== "null") {
        heroBanner.style.backgroundImage = "url('" + backdropPath + "')";
    }
    if (heroTitle) { heroTitle.innerText = title; }
}

window.addEventListener('scroll', () => {
    const header = document.getElementById('mainHeader');
    if (header) {
        header.style.background = window.scrollY > 50 
            ? "rgba(11, 11, 11, 0.95)" 
            : "linear-gradient(to bottom, rgba(0, 0, 0, 0.8) 0%, rgba(0, 0, 0, 0) 100%)";
    }
});

const el = document.getElementById('scrollRow');
if(el) {
    el.addEventListener('wheel', (evt) => {
        if (evt.deltaY !== 0) {
            evt.preventDefault();
            el.scrollLeft += evt.deltaY;
        }
    }, { passive: false });
}
</script>
</body>
</html>
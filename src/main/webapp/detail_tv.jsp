<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.mlteam.wadidaw.entities.*" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <% Shows show = (Shows) request.getAttribute("show"); %>
    <link rel="icon" type="image/png" href="Resources/wadidaw-logo-white.png">
    <title><%= (show != null) ? show.getTitle() : "Detail TV Show" %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: white;
            background: #0b0b0b;
            overflow-x: hidden;
        }
        .backdrop-container {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100vh;
            z-index: 1;
        }
        .backdrop-img {
            width: 100%;
            height: 100%;
            background-size: cover;
            background-position: center top;
            background-image: url('<%= show.getBackdrop_path() %>');
        }
        .overlay {
            position: absolute;
            inset: 0;
            background: linear-gradient(to bottom, rgba(11,11,11,0) 0%, rgba(11,11,11,0.6) 60%, rgba(11,11,11,1) 100%),
                        linear-gradient(to right, rgba(20,20,20,0.9) 20%, rgba(20,20,20,0.2) 100%);
        }
        .content {
            position: relative;
            z-index: 5;
            padding: 100px 60px;
            max-width: 950px;
        }
        h1 {
            font-size: clamp(32px, 6vw, 56px);
            margin: 0 0 15px 0;
            line-height: 1.1;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
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
            margin-bottom: 30px;
        }
        .info-item label {
            display: block;
            color: #888;
            font-size: 0.85rem;
            text-transform: uppercase;
            margin-bottom: 5px;
        }
        .custom-dropdown {
            position: relative;
            min-width: 280px;
            margin-bottom: 25px;
            cursor: pointer;
            z-index: 100;
        }
        .dropdown-selected {
            background: rgba(0,0,0,0.8);
            border: 1px solid #555;
            padding: 12px 20px;
            border-radius: 4px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            transition: 0.3s;
        }
        .dropdown-selected:hover { border-color: #e50914; }
        .dropdown-options {
            position: absolute;
            top: 110%;
            left: 0;
            right: 0;
            background: #242424;
            border: 1px solid #444;
            display: none;
            max-height: 300px;
            overflow-y: auto;
            border-radius: 4px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.7);
        }
        .custom-dropdown.active .dropdown-options { display: block; }
        .option-item {
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 10px 15px;
            border-bottom: 1px solid #333;
            transition: 0.2s;
        }
        .option-item:hover { background: #333; }
        .option-item img { width: 40px; height: 60px; object-fit: cover; border-radius: 3px; }
        .episode-container {
            background: rgba(20,20,20,0.9);
            border-radius: 8px;
            padding: 25px;
            margin-top: 20px;
            border: 1px solid rgba(255,255,255,0.1);
            position: relative;
            z-index: 10;
            min-height: 200px;
        }
        .episode-item {
            display: flex;
            gap: 20px;
            margin-bottom: 25px;
            border-bottom: 1px solid #333;
            padding-bottom: 15px;
        }
        .ep-thumb {
            flex-shrink: 0;
            width: 200px;
            height: 112px;
            overflow: hidden;
            border-radius: 4px;
            background: #333;
        }
        .ep-thumb img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
        }
        .ep-info h4 {
            margin: 0 0 8px 0;
            color: #fff;
            font-size: 1.1rem;
        }
        .ep-info p {
            margin: 0;
            font-size: 0.9rem;
            color: #ccc;
            line-height: 1.5;
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
        .btn-play:hover { background: rgba(255,255,255,0.8); }
        .btn-back {
            position: fixed; top: 20px; left: 20px; width: 45px; height: 45px;
            border-radius: 50%; border: none; color: white; background: rgba(0,0,0,0.6);
            cursor: pointer; z-index: 1000; display: flex; align-items: center; justify-content: center;
        }
        .btn-back:hover { background: #e50914; }
        .modal {
            display: none; position: fixed; z-index: 2000; inset: 0;
            background: rgba(0,0,0,0.9); justify-content: center; align-items: center;
        }
        .modal-content { width: 85%; max-width: 900px; aspect-ratio: 16/9; position: relative; }
        .close { position: absolute; top: -45px; right: 0; font-size: 35px; cursor: pointer; color: white; }
        @media (max-width: 768px) {
            .content { padding: 40px 20px; }
            .info-grid { grid-template-columns: 1fr; }
            .ep-thumb { width: 140px; height: 80px; }
        }
    </style>
</head>
<body>

<button onclick="goHome()" class="btn-back">✕</button>

<div class="backdrop-container">
    <div class="backdrop-img"></div>
    <div class="overlay"></div>
</div>

<div class="content">
    <h1><%= show.getTitle() %></h1>
    <div class="meta-row">
        <span>⭐ <%= String.format("%.1f", show.getVote_average()) %></span>
        <span><%= (show.getRelease_date() != null) ? show.getRelease_date().split("-")[0] : "-" %></span>
        <span class="badge"><%= show.getNumber_of_seasons() %> Seasons</span>
    </div>
    
    <p class="overview"><%= show.getOverview() %></p>
    
    <div class="info-grid">
        <div class="info-item">
            <label>Genres</label>
            <span><%= show.getGenres() %></span>
        </div>
        <div class="info-item">
            <label>Status</label>
            <span><%= show.getStatus() %></span>
        </div>
    </div>

    <% if (show.getTrailer_key() != null && !show.getTrailer_key().isEmpty()) { %>
        <button class="btn-play" onclick="openTrailer('<%= show.getTrailer_key() %>')">
            <svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor"><path d="M8 5v14l11-7z"/></svg>
            Play Trailer
        </button>
    <% } %>

    <div class="custom-dropdown" id="seasonDropdown" onclick="toggleDropdown()">
        <div class="dropdown-selected" id="selectedLabel">Select Season</div>
        <div class="dropdown-options">
            <%
                List<Seasons> seasons = show.getSeasons();
                if (seasons != null) {
                    for (Seasons s : seasons) {
            %>
                <div class="option-item" onclick="selectSeason(<%= s.getSeason_number() %>, '<%= s.getName() %>', event)">
                    <img src="<%= (s.getPoster_path() != null) ? s.getPoster_path() : "" %>"
                         onerror="this.onerror=null;this.style.display='none'">
                    <div>
                        <strong><%= s.getName() %></strong><br>
                        <span style="font-size:0.8rem; color:#888;"><%= s.getEpisode_count() %> Episodes</span>
                    </div>
                </div>
            <%
                    }
                }
            %>
        </div>
    </div>

    <div id="episodeList" class="episode-container">
        <p style="color: #888;">Select a season to view episodes.</p>
    </div>
</div>

<div id="trailerModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeTrailer()">×</span>
        <iframe id="trailerFrame" src="" allow="autoplay; encrypted-media" allowfullscreen
                style="width:100%; height:100%; border:none;"></iframe>
    </div>
</div>

<script>
    const dropdown         = document.getElementById('seasonDropdown');
    const selectedLabel    = document.getElementById('selectedLabel');
    const episodeContainer = document.getElementById('episodeList');
    const trailerModal      = document.getElementById('trailerModal');
    const trailerFrame      = document.getElementById('trailerFrame');

    function toggleDropdown() {
        dropdown.classList.toggle('active');
    }

    function selectSeason(num, name, e) {
        if (e) e.stopPropagation();
        selectedLabel.innerText = name;
        dropdown.classList.remove('active');

        episodeContainer.innerHTML = '<p style="text-align:center;padding:20px;color:#aaa;">Memuat episode...</p>';

        fetch('season?id=<%= show.getId() %>&season_number=' + num)
            .then(res => res.json())
            .then(episodes => {
                while (episodeContainer.firstChild) {
                    episodeContainer.removeChild(episodeContainer.firstChild);
                }

                if (!Array.isArray(episodes) || episodes.length === 0) {
                    const msg = document.createElement('p');
                    msg.style.cssText = 'padding:20px;color:#aaa;';
                    msg.textContent = 'Tidak ada episode.';
                    episodeContainer.appendChild(msg);
                    return;
                }

                episodes.forEach(function(ep, index) {
                    const epNum      = ep.episode_number || (index + 1);
                    const epName     = ep.name     || ('Episode ' + epNum);
                    const epOverview = ep.overview || 'Deskripsi tidak tersedia.';
                    const epImg      = ep.still_path || '';

                    var item = document.createElement('div');
                    item.className = 'episode-item';

                    var thumbDiv = document.createElement('div');
                    thumbDiv.className = 'ep-thumb';

                    if (epImg) {
                        var img = document.createElement('img');
                        img.src = epImg;
                        img.alt = epName;
                        img.onerror = function() {
                            this.onerror = null;
                            this.style.display = 'none';
                        };
                        thumbDiv.appendChild(img);
                    }

                    var infoDiv = document.createElement('div');
                    infoDiv.className = 'ep-info';

                    var h4 = document.createElement('h4');
                    h4.textContent = epNum + '. ' + epName;

                    var p = document.createElement('p');
                    p.textContent = epOverview;

                    infoDiv.appendChild(h4);
                    infoDiv.appendChild(p);

                    item.appendChild(thumbDiv);
                    item.appendChild(infoDiv);

                    episodeContainer.appendChild(item);
                });
            })
            .catch(function(err) {
                console.error('Error:', err);
                episodeContainer.innerHTML = '<p style="color:red;padding:20px;">Gagal memuat episode.</p>';
            });
    }

    function openTrailer(key) {
        trailerFrame.src = 'https://www.youtube.com/embed/' + key + '?autoplay=1';
        trailerModal.style.display = 'flex';
    }

    function closeTrailer() {
        trailerModal.style.display = 'none';
        trailerFrame.src = '';
    }

    document.addEventListener('DOMContentLoaded', function() {
        selectSeason(1, 'Season 1');
    });
    
    function goHome() {
        const backUrl = document.referrer;
        if (backUrl && backUrl !== "") {
            window.location.href = backUrl;
        } else {
            window.location.href = "search";
        }
    }

    window.onclick = function(event) {
        if (!event.target.closest('#seasonDropdown')) {
            dropdown.classList.remove('active');
        }
        if (event.target === trailerModal) closeTrailer();
    };
</script>
</body>
</html>
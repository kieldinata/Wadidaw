<html>
<head>
    <title>Wadidaw - Setup</title>
    <link rel="icon" type="image/png" href="Resources/wadidaw-logo-white.png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <style>
        body {
            margin:0;
            background:#0b0b0b;
            color:#e5e5e5;
            font-family:Arial, sans-serif;
            display:flex;
            align-items:center;
            justify-content:center;
            height:100vh;
        }

        /* Logo */
        .header-container {
            position:absolute;
            top:25px;
            left:40px;
        }

        .logo-wadidaw {
            height: 75px;
            width: auto;
            object-fit: contain;
        }

        /* Navigation API Docs */
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

        /* Card Container */
        .setup-box {
            width:100%;
            max-width:420px;
            padding:40px;
            border-radius:15px;
            background:rgba(255,255,255,0.05);
            border:1px solid rgba(255,255,255,0.15);
            box-shadow:0 20px 50px rgba(0,0,0,0.7);
            backdrop-filter: blur(8px);
        }

        .setup-box h2 {
            text-align:center;
            margin-bottom:30px;
            letter-spacing:2px;
            color:#fff;
        }

        label {
            font-size:0.9rem;
            color:#aaa;
            display:block;
            margin-bottom:5px;
        }

        input {
            width:100%;
            padding:12px 15px;
            margin-bottom:20px;
            border-radius:8px;
            border:1px solid rgba(255,255,255,0.15);
            background:rgba(255,255,255,0.08);
            color:white;
            outline:none;
            transition:0.3s;
        }

        input:focus {
            border-color:#e50914;
            box-shadow:0 0 15px rgba(229,9,20,0.3);
            background:rgba(255,255,255,0.12);
        }

        button {
            width:100%;
            padding:14px;
            border:none;
            border-radius:8px;
            background:#e50914;
            color:white;
            font-weight:bold;
            cursor:pointer;
            transition:0.3s;
        }

        button:hover {
            background:#ff1f2b;
            transform:translateY(-2px);
            box-shadow:0 10px 25px rgba(229,9,20,0.4);
        }

        @media (max-width:768px){
            .header-container { left:20px; top:15px; }
            .logo-wadidaw { height:50px; }
            .top-nav { top: 25px; right: 20px; }
            .nav-link { font-size: 0.7rem; padding: 5px 10px; }
            .setup-box { margin:0 20px; padding:30px; }
        }
    </style>
</head>

<body>

<div class="header-container">
    <a href="${pageContext.request.contextPath}/">
        <img src="Resources/wadidaw-logo-white.png" class="logo-wadidaw">
    </a>
</div>

<div class="top-nav">
    <a href="${pageContext.request.contextPath}/api" class="nav-link">API Docs</a>
</div>

<div class="setup-box">
    <form action="${pageContext.request.contextPath}/setup" method="POST">
        <h2>Konfigurasi API Key</h2>

        <label>Groq API Key</label>
        <input type="text" name="groq_key" placeholder="gsk_..." required>

        <label>TMDB API Key</label>
        <input type="text" name="tmdb_key" placeholder="Enter TMDB Key..." required>

        <button type="submit">Simpan & Mulai</button>
    </form>
</div>

</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page import="java.util.*" %>
<html>
  <head>
    <title>Wadidaw - API Documentation</title>
    <link rel="icon" type="image/png" href="Resources/wadidaw-logo-white.png" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <style>
      body {
        margin: 0;
        background: #0b0b0b;
        color: #e5e5e5;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        padding: 40px;
        padding-top: 120px; /* Jarak agar tidak tertutup logo */
      }

      /* Header Logo Section */
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

      .container {
        max-width: 1000px;
        margin: 0 auto;
      }
      h1 {
        color: #ffffff;
        border-bottom: 2px solid #e50914;
        padding-bottom: 10px;
        margin-bottom: 5px;
      }
      .subtitle {
        color: #888;
        margin-bottom: 40px;
      }

      table {
        width: 100%;
        border-collapse: collapse;
        background: rgba(255, 255, 255, 0.03);
        border-radius: 12px;
        overflow: hidden;
        border: 1px solid rgba(255, 255, 255, 0.1);
      }
      th {
        background: rgba(229, 9, 20, 0.2);
        text-align: left;
        padding: 15px;
        font-size: 0.9rem;
        text-transform: uppercase;
        letter-spacing: 1px;
      }
      td {
        padding: 20px 15px;
        border-bottom: 1px solid rgba(255, 255, 255, 0.05);
        vertical-align: top;
      }
      tr:hover {
        background: rgba(255, 255, 255, 0.05);
      }

      .api-name {
        font-weight: bold;
        color: #fff;
        font-size: 1.1rem;
        display: block;
      }
      .api-tag {
        display: inline-block;
        padding: 2px 8px;
        border-radius: 4px;
        font-size: 0.75rem;
        margin-top: 5px;
        background: #333;
      }
      .base-url {
        font-family: "Courier New", monospace;
        color: #46d369;
        font-size: 0.85rem;
        word-break: break-all;
      }
      .doc-link {
        color: #e50914;
        text-decoration: none;
        font-weight: bold;
        font-size: 0.85rem;
      }
      .doc-link:hover {
        text-decoration: underline;
      }
      .status-up {
        color: #46d369;
        font-size: 0.8rem;
      }

      @media (max-width: 768px) {
        .header-container {
          left: 20px;
          top: 15px;
        }
        .logo-wadidaw {
          height: 45px;
        }
        body {
          padding: 100px 20px 40px 20px;
        }
      }
    </style>
  </head>
  <body>
    <div class="header-container">
      <a href="${pageContext.request.contextPath}/">
        <img src="https://github.com/user-attachments/assets/59f4440a-a936-4364-b2a6-825c4f9021f9" alt="Wadidaw Logo" class="logo-wadidaw" />
      </a>
    </div>

    <div class="container">
      <h1>External API Registry</h1>
      <p class="subtitle">Daftar layanan pihak ketiga yang digunakan oleh sistem Wadidaw.</p>

      <table>
        <thead>
          <tr>
            <th width="25%">Provider & Purpose</th>
            <th width="45%">Endpoint Configuration</th>
            <th width="30%">Resources</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <span class="api-name">TMDB API</span>
              <span class="api-tag">Metadata & Images</span>
            </td>
            <td>
              <span class="base-url">https://api.themoviedb.org/3</span>
              <p style="font-size: 0.85rem; color: #aaa">Digunakan untuk mengambil data film, tv shows, poster, dan backdrop.</p>
            </td>
            <td>
              <a href="https://developer.themoviedb.org/docs" target="_blank" class="doc-link">Official Docs ↗</a><br />
              <span class="status-up">● System Active</span>
            </td>
          </tr>

          <tr>
            <td>
              <span class="api-name">Groq Cloud AI</span>
              <span class="api-tag">Llama 3 / NLP</span>
            </td>
            <td>
              <span class="base-url">https://api.groq.com/openai/v1</span>
              <p style="font-size: 0.85rem; color: #aaa">Digunakan untuk fitur "What Did I Watch" (Semantic search berdasarkan deskripsi plot).</p>
            </td>
            <td>
              <a href="https://console.groq.com/docs/quickstart" target="_blank" class="doc-link">Groq Console ↗</a><br />
              <span class="status-up">● System Active</span>
            </td>
          </tr>

          <tr>
            <td>
              <span class="api-name">YouTube Embed</span>
              <span class="api-tag">Video Player</span>
            </td>
            <td>
              <span class="base-url">https://www.youtube.com/embed/</span>
              <p style="font-size: 0.85rem; color: #aaa">Digunakan untuk memutar trailer film di dalam modal detail.</p>
            </td>
            <td>
              <a href="https://developers.google.com/youtube/player_parameters" target="_blank" class="doc-link">IFrame API Docs ↗</a><br />
              <span class="status-up">● System Active</span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </body>
</html>

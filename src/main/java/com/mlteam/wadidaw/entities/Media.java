package com.mlteam.wadidaw.entities;
/**
 * Abstract class yang berfungsi sebagai base entity untuk semua tipe media (Movies dan Shows).
 * Berisi atribut umum yang dimiliki oleh film maupun serial TV hasil dari API TMDB.
 */
public abstract class Media {
    protected int id;
    protected String title;
    protected String original_title;
    protected String overview;
    protected String backdrop_path;
    protected String poster_path;
    protected String release_date;
    protected double vote_average;
    protected int vote_count;
    protected double popularity;
    protected String genres;
    protected String origin_country;
    protected String original_language;
    protected String status;
    protected String tagline;
    protected String homepage;
    protected String spoken_languages;
    protected String production_companies;
    protected String production_countries;
    protected String trailer_key;
    /**
     * Constructor default untuk class Media.
     */
    public Media() {
    }
    /** @return ID unik media. */
    public int getId() {
        return id;
    }
    /** @param id ID unik dari TMDB. */
    public void setId(int id) {
        this.id = id;
    }
    /** @return Judul utama media. */
    public String getTitle() {
        return title;
    }
    /** @param title Judul utama media. */
    public void setTitle(String title) {
        this.title = title;
    }
    /** @return Judul asli media. */
    public String getOriginal_title() {
        return original_title;
    }
    /** @param original_title Judul asli media. */
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }
    /** @return Ringkasan deskripsi media. */
    public String getOverview() {
        return overview;
    }
    /** @param overview Ringkasan deskripsi media. */
    public void setOverview(String overview) {
        this.overview = overview;
    }
    /** @return URL path untuk gambar latar belakang. */
    public String getBackdrop_path() {
        return backdrop_path;
    }
    /** @param backdrop_path URL path gambar latar belakang. */
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
    /** @return URL path untuk gambar poster. */
    public String getPoster_path() {
        return poster_path;
    }
    /** @param poster_path URL path gambar poster. */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
    /** @return Tanggal rilis atau tayang perdana. */
    public String getRelease_date() {
        return release_date;
    }
    /** @param release_date Tanggal rilis. */
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    /** @return Skor rata-rata voting pengguna. */
    public double getVote_average() {
        return vote_average;
    }
    /** @param vote_average Skor rata-rata voting. */
    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
    /** @return Jumlah total suara yang masuk. */
    public int getVote_count() {
        return vote_count;
    }
    /** @param vote_count Jumlah suara. */
    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
    /** @return Skor popularitas media di TMDB. */
    public double getPopularity() {
        return popularity;
    }
    /** @param popularity Skor popularitas. */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
    /** @return Daftar genre dalam bentuk String. */
    public String getGenres() {
        return genres;
    }
    /** @param genres Daftar genre. */
    public void setGenres(String genres) {
        this.genres = genres;
    }
    /** @return Negara asal produksi. */
    public String getOrigin_country() {
        return origin_country;
    }
    /** @param origin_country Negara asal produksi. */
    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }
    /** @return Bahasa asli media. */
    public String getOriginal_language() {
        return original_language;
    }
    /** @param original_language Bahasa asli. */
    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }
    /** @return Status rilis media (misal: Released, Canceled). */
    public String getStatus() {
        return status;
    }
    /** @param status Status rilis. */
    public void setStatus(String status) {
        this.status = status;
    }
    /** @return Slogan atau tagline media. */
    public String getTagline() {
        return tagline;
    }
    /** @param tagline Slogan media. */
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
    /** @return URL situs resmi media. */
    public String getHomepage() {
        return homepage;
    }
    /** @param homepage URL situs resmi. */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
    /** @return Daftar bahasa yang digunakan dalam media. */
    public String getSpoken_languages() {
        return spoken_languages;
    }
    /** @param spoken_languages Daftar bahasa. */
    public void setSpoken_languages(String spoken_languages) {
        this.spoken_languages = spoken_languages;
    }
    /** @return Nama perusahaan produksi. */
    public String getProduction_companies() {
        return production_companies;
    }
    /** @param production_companies Nama perusahaan produksi. */
    public void setProduction_companies(String production_companies) {
        this.production_companies = production_companies;
    }
    /** @return Daftar negara lokasi produksi. */
    public String getProduction_countries() {
        return production_countries;
    }
    /** @param production_countries Daftar negara produksi. */
    public void setProduction_countries(String production_countries) {
        this.production_countries = production_countries;
    }
    /** @return Key video YouTube untuk trailer. */
    public String getTrailer_key() {
        return trailer_key;
    }
    /** @param trailer_key Key video YouTube. */
    public void setTrailer_key(String trailer_key) {
        this.trailer_key = trailer_key;
    }
}
package com.mlteam.wadidaw.entities;
import java.util.List;
/**
 * Entity class yang merepresentasikan data Musim (Season) dari sebuah serial TV.
 * Menyimpan informasi umum season dan daftar episode yang terkandung di dalamnya.
 */
public class Seasons {
    private int id;
    private String name;
    private String overview;
    private String poster_path;
    private int season_number;
    private int episode_count;
    private String air_date;
    private double vote_average;
    private List<Episodes> episodes;
    /**
     * Constructor default untuk instansiasi objek Seasons.
     */
    public Seasons() {
    }
    /** @return List objek Episodes yang termasuk dalam season ini. */
    public List<Episodes> getEpisodes() {
        return episodes;
    }
    /** @param episodes List objek episode yang akan diset. */
    public void setEpisodes(List<Episodes> episodes) {
        this.episodes = episodes;
    }
    /** @return ID unik season dari TMDB. */
    public int getId() {
        return id;
    }
    /** @param id ID unik season. */
    public void setId(int id) {
        this.id = id;
    }
    /** @return Nama atau judul season. */
    public String getName() {
        return name;
    }
    /** @param name Nama season. */
    public void setName(String name) {
        this.name = name;
    }
    /** @return Ringkasan deskripsi season. */
    public String getOverview() {
        return overview;
    }
    /** @param overview Ringkasan deskripsi season. */
    public void setOverview(String overview) {
        this.overview = overview;
    }
    /** @return URL path untuk poster season. */
    public String getPoster_path() {
        return poster_path;
    }
    /** @param poster_path URL path poster. */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
    /** @return Nomor urutan season. */
    public int getSeason_number() {
        return season_number;
    }
    /** @param season_number Nomor urutan season. */
    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }
    /** @return Jumlah total episode dalam season ini. */
    public int getEpisode_count() {
        return episode_count;
    }
    /** @param episode_count Jumlah episode. */
    public void setEpisode_count(int episode_count) {
        this.episode_count = episode_count;
    }
    /** @return Tanggal penayangan perdana season. */
    public String getAir_date() {
        return air_date;
    }
    /** @param air_date Tanggal penayangan. */
    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }
    /** @return Nilai rata-rata rating untuk season ini. */
    public double getVote_average() {
        return vote_average;
    }
    /** @param vote_average Nilai rata-rata rating. */
    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
}
package com.mlteam.wadidaw.entities;
/**
 * Entity class yang merepresentasikan data episode dari sebuah serial TV.
 * Menyimpan informasi rinci seperti nomor episode, sinopsis, dan gambar cuplikan (still path).
 */
public class Episodes {
    private int id;
    private String name;
    private String overview;
    private String air_date;
    private int episode_number;
    private int season_number;
    private String still_path;
    private double vote_average;
    private int runtime;
    /**
     * Constructor default untuk instansiasi objek Episodes.
     */
    public Episodes() {
    }
    /** @return ID unik episode. */
    public int getId() {
        return id;
    }
    /** @param id ID unik episode dari TMDB. */
    public void setId(int id) {
        this.id = id;
    }
    /** @return Nama atau judul episode. */
    public String getName() {
        return name;
    }
    /** @param name Nama atau judul episode. */
    public void setName(String name) {
        this.name = name;
    }
    /** @return Ringkasan cerita episode. */
    public String getOverview() {
        return overview;
    }
    /** @param overview Ringkasan cerita episode. */
    public void setOverview(String overview) {
        this.overview = overview;
    }
    /** @return Tanggal penayangan episode. */
    public String getAir_date() {
        return air_date;
    }
    /** @param air_date Tanggal penayangan episode. */
    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }
    /** @return Urutan nomor episode dalam satu season. */
    public int getEpisode_number() {
        return episode_number;
    }
    /** @param episode_number Urutan nomor episode. */
    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }
    /** @return Nomor season tempat episode ini berada. */
    public int getSeason_number() {
        return season_number;
    }
    /** @param season_number Nomor season. */
    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }
    /** @return URL path untuk gambar cuplikan episode. */
    public String getStill_path() {
        return still_path;
    }
    /** @param still_path URL path gambar cuplikan. */
    public void setStill_path(String still_path) {
        this.still_path = still_path;
    }
    /** @return Nilai rata-rata rating episode. */
    public double getVote_average() {
        return vote_average;
    }
    /** @param vote_average Nilai rata-rata rating. */
    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
    /** @return Durasi waktu putar episode dalam menit. */
    public int getRuntime() {
        return runtime;
    }
    /** @param runtime Durasi waktu putar dalam menit. */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
}
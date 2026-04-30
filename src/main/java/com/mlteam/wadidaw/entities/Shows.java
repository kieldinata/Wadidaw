package com.mlteam.wadidaw.entities;
import java.util.List;
/**
 * Entity class yang merepresentasikan data Acara TV (Show).
 * Merupakan turunan dari class Media dan menyimpan informasi spesifik serial seperti jumlah season, episode, dan status produksi.
 */
public class Shows extends Media {
    private int number_of_episodes;
    private int number_of_seasons;
    private String last_air_date;
    private boolean in_production;
    private String type;
    private List<Seasons> seasons;
    /**
     * Constructor default untuk instansiasi objek Shows.
     * Memanggil constructor superclass Media.
     */
    public Shows() {
        super();
    }
    /** @return List objek Seasons yang dimiliki oleh acara TV ini. */
    public List<Seasons> getSeasons() {
        return seasons;
    }
    /** @param seasons List objek season yang akan diset. */
    public void setSeasons(List<Seasons> seasons) {
        this.seasons = seasons;
    }
    /** @return Jumlah total episode yang tersedia. */
    public int getNumber_of_episodes() {
        return number_of_episodes;
    }
    /** @param number_of_episodes Jumlah total episode. */
    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }
    /** @return Jumlah total season yang tersedia. */
    public int getNumber_of_seasons() {
        return number_of_seasons;
    }
    /** @param number_of_seasons Jumlah total season. */
    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }
    /** @return Tanggal penayangan terakhir episode terbaru. */
    public String getLast_air_date() {
        return last_air_date;
    }
    /** @param last_air_date Tanggal penayangan terakhir. */
    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }
    /** @return true jika serial masih dalam status produksi, false jika sudah tamat. */
    public boolean isIn_production() {
        return in_production;
    }
    /** @param in_production Status produksi serial. */
    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }
    /** @return Tipe acara (misal: Scripted, Reality). */
    public String getType() {
        return type;
    }
    /** @param type Tipe acara TV. */
    public void setType(String type) {
        this.type = type;
    }
}
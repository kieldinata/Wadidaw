package com.mlteam.wadidaw.entities;
/**
 * Entity class yang merepresentasikan data Film (Movie).
 * Merupakan turunan dari class Media dan menyimpan informasi spesifik film seperti budget, revenue, dan durasi.
 */
public class Movies extends Media {
    private boolean adult;
    private int budget;
    private long revenue;
    private int runtime;
    private String imdb_id;
    private String belongs_to_collection;
    /**
     * Constructor default untuk instansiasi objek Movies.
     * Memanggil constructor superclass Media.
     */
    public Movies() {
        super();
    }
    /** @return true jika film kategori dewasa, false jika tidak. */
    public boolean isAdult() {
        return adult;
    }
    /** @param adult status kategori dewasa. */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }
    /** @return Anggaran produksi film dalam USD. */
    public int getBudget() {
        return budget;
    }
    /** @param budget Anggaran produksi film. */
    public void setBudget(int budget) {
        this.budget = budget;
    }
    /** @return Pendapatan kotor film dalam USD. */
    public long getRevenue() {
        return revenue;
    }
    /** @param revenue Pendapatan kotor film. */
    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }
    /** @return Durasi film dalam satuan menit. */
    public int getRuntime() {
        return runtime;
    }
    /** @param runtime Durasi film dalam menit. */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
    /** @return ID unik film di database IMDB. */
    public String getImdb_id() {
        return imdb_id;
    }
    /** @param imdb_id ID film di IMDB. */
    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }
    /** @return Informasi koleksi atau sekuel film jika tersedia. */
    public String getBelongs_to_collection() {
        return belongs_to_collection;
    }
    /** @param belongs_to_collection Informasi koleksi film. */
    public void setBelongs_to_collection(String belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }
}
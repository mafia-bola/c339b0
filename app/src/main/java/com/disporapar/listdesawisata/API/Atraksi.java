package com.disporapar.listdesawisata.API;

public class Atraksi {

    long id_atraksi, desa_wisata_id;
    String nama_atraksi, deskripsi, foto_atraksi;

    String nama_wisata;

    public long getId_atraksi() {
        return id_atraksi;
    }

    public void setId_atraksi(long id_atraksi) {
        this.id_atraksi = id_atraksi;
    }

    public long getDesa_wisata_id() {
        return desa_wisata_id;
    }

    public void setDesa_wisata_id(long desa_wisata_id) {
        this.desa_wisata_id = desa_wisata_id;
    }

    public String getNama_atraksi() {
        return nama_atraksi;
    }

    public void setNama_atraksi(String nama_atraksi) {
        this.nama_atraksi = nama_atraksi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto_atraksi() {
        return foto_atraksi;
    }

    public void setFoto_atraksi(String foto_atraksi) {
        this.foto_atraksi = foto_atraksi;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }
}

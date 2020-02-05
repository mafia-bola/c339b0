package com.disporapar.listdesawisata.API;

public class Kegiatan {

    long id_atraksi, desa_wisata_id;
    long kategori_id;
    String nama_kegiatan, deskripsi, foto_kegiatan;
    String nama_kategori;

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

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

    public long getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(long kategori_id) {
        this.kategori_id = kategori_id;
    }

    public String getNama_kegiatan() {
        return nama_kegiatan;
    }

    public void setNama_kegiatan(String nama_kegiatan) {
        this.nama_kegiatan = nama_kegiatan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto_kegiatan() {
        return foto_kegiatan;
    }

    public void setFoto_kegiatan(String foto_kegiatan) {
        this.foto_kegiatan = foto_kegiatan;
    }
}

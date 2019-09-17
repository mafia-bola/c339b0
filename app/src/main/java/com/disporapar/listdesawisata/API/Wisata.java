package com.disporapar.listdesawisata.API;

public class Wisata {

    long id_wisata;
    String nama_wisata, alamat_wisata, sejarah_desa, demografi, potensi;
    String thumbnail;
    double lat;
    double lng;

    public long getId_wisata() {
        return id_wisata;
    }

    public void setId_wisata(long id_wisata) {
        this.id_wisata = id_wisata;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getAlamat_wisata() {
        return alamat_wisata;
    }

    public void setAlamat_wisata(String alamat_wisata) {
        this.alamat_wisata = alamat_wisata;
    }

    public String getSejarah_desa() {
        return sejarah_desa;
    }

    public void setSejarah_desa(String sejarah_desa) {
        this.sejarah_desa = sejarah_desa;
    }

    public String getDemografi() {
        return demografi;
    }

    public void setDemografi(String demografi) {
        this.demografi = demografi;
    }

    public String getPotensi() {
        return potensi;
    }

    public void setPotensi(String potensi) {
        this.potensi = potensi;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

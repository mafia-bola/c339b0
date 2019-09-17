package com.disporapar.listdesawisata.API;

public class Kalendar {

    long id_kalendar;
    String judul_kalendar, deskripsi, foto_kalendar;

    public long getId_kalendar() {
        return id_kalendar;
    }

    public void setId_kalendar(long id_kalendar) {
        this.id_kalendar = id_kalendar;
    }

    public String getJudul_kalendar() {
        return judul_kalendar;
    }

    public void setJudul_kalendar(String judul_kalendar) {
        this.judul_kalendar = judul_kalendar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto_kalendar() {
        return foto_kalendar;
    }

    public void setFoto_kalendar(String foto_kalendar) {
        this.foto_kalendar = foto_kalendar;
    }
}

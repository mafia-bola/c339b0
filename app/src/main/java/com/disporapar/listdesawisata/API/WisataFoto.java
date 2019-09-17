package com.disporapar.listdesawisata.API;

public class WisataFoto {

    long id_foto, desa_wisata_id;
    String foto_wisata;

    public long getId_foto() {
        return id_foto;
    }

    public void setId_foto(long id_foto) {
        this.id_foto = id_foto;
    }

    public long getDesa_wisata_id() {
        return desa_wisata_id;
    }

    public void setDesa_wisata_id(long desa_wisata_id) {
        this.desa_wisata_id = desa_wisata_id;
    }

    public String getFoto_wisata() {
        return foto_wisata;
    }

    public void setFoto_wisata(String foto_wisata) {
        this.foto_wisata = foto_wisata;
    }
}

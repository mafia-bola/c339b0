package com.disporapar.listdesawisata.API;

public class Event {

    long id_event;
    String tanggal_event, foto_event;

    public long getId_event() {
        return id_event;
    }

    public void setId_event(long id_event) {
        this.id_event = id_event;
    }

    public String getTanggal_event() {
        return tanggal_event;
    }

    public void setTanggal_event(String tanggal_event) {
        this.tanggal_event = tanggal_event;
    }

    public String getFoto_event() {
        return foto_event;
    }

    public void setFoto_event(String foto_event) {
        this.foto_event = foto_event;
    }
}

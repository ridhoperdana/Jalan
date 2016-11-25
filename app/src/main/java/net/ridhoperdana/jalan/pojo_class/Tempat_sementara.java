package net.ridhoperdana.jalan.pojo_class;

/**
 * Created by RIDHO on 11/25/2016.
 */

public class Tempat_sementara {
    private String nama_tempat, alamat_tempat;
    private Double lat_tempat, longt_tempat;
    private int position_tempat;

    public Tempat_sementara(String nama_tempat, String alamat_tempat, Double lat_tempat, Double longt_tempat, int position_tempat) {
        this.nama_tempat = nama_tempat;
        this.alamat_tempat = alamat_tempat;
        this.lat_tempat = lat_tempat;
        this.longt_tempat = longt_tempat;
        this.position_tempat = position_tempat;
    }

    public int getPosition_tempat() {
        return position_tempat;
    }

    public void setPosition_tempat(int position_tempat) {
        this.position_tempat = position_tempat;
    }

    public String getNama_tempat() {
        return nama_tempat;
    }

    public void setNama_tempat(String nama_tempat) {
        this.nama_tempat = nama_tempat;
    }

    public String getAlamat_tempat() {
        return alamat_tempat;
    }

    public void setAlamat_tempat(String alamat_tempat) {
        this.alamat_tempat = alamat_tempat;
    }

    public Double getLat_tempat() {
        return lat_tempat;
    }

    public void setLat_tempat(Double lat_tempat) {
        this.lat_tempat = lat_tempat;
    }

    public Double getLongt_tempat() {
        return longt_tempat;
    }

    public void setLongt_tempat(Double longt_tempat) {
        this.longt_tempat = longt_tempat;
    }
}

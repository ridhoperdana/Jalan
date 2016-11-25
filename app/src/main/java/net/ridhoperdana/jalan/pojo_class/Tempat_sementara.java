package net.ridhoperdana.jalan.pojo_class;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by RIDHO on 11/25/2016.
 */

public class Tempat_sementara implements Parcelable{
    private String nama_tempat, alamat_tempat;
    private Double lat_tempat, longt_tempat;

    public Tempat_sementara(String nama_tempat, String alamat_tempat, Double lat_tempat, Double longt_tempat) {
        this.nama_tempat = nama_tempat;
        this.alamat_tempat = alamat_tempat;
        this.lat_tempat = lat_tempat;
        this.longt_tempat = longt_tempat;
    }

    private Tempat_sementara(Parcel in) {
        nama_tempat = in.readString();
        alamat_tempat = in.readString();
        lat_tempat = in.readDouble();
        longt_tempat = in.readDouble();
    }

    public static final Creator<Tempat_sementara> CREATOR = new Creator<Tempat_sementara>() {
        @Override
        public Tempat_sementara createFromParcel(Parcel in) {
            return new Tempat_sementara(in);
        }

        @Override
        public Tempat_sementara[] newArray(int size) {
            return new Tempat_sementara[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama_tempat);
        dest.writeString(alamat_tempat);
        dest.writeDouble(lat_tempat);
        dest.writeDouble(longt_tempat);
    }
}

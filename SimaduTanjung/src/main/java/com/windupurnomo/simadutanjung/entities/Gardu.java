package com.windupurnomo.simadutanjung.entities;

import android.support.v7.appcompat.R;

import java.util.Date;

/**
 * Created by Windu Purnomo on 7/1/14.
 */
public class Gardu {
    private int id;
    private String nomor;
    private String address;
    private float daya;
    private String penyulang;
    private int tiang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getDaya() {
        return daya;
    }

    public void setDaya(float daya) {
        this.daya = daya;
    }

    public String getPenyulang() {
        return penyulang;
    }

    public void setPenyulang(String penyulang) {
        this.penyulang = penyulang;
    }

    public int getTiang() {
        return tiang;
    }

    public void setTiang(int tiang) {
        this.tiang = tiang;
    }
}

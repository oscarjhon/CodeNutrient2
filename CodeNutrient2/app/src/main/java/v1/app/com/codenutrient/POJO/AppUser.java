package v1.app.com.codenutrient.POJO;

import android.net.Uri;

import java.io.Serializable;

public class AppUser implements Serializable {
    private String Token;
    private int code;
    private String email;
    private String name;
    private Uri photo;
    private String provider;
    private String uid;

    private InfoAppUser infoAppUser;

    public AppUser(){
        infoAppUser = null;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public InfoAppUser getInfoAppUser() {
        return infoAppUser;
    }

    public void setInfoAppUser(InfoAppUser infoAppUser) {
        this.infoAppUser = infoAppUser;
    }
}

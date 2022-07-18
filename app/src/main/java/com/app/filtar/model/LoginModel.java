package com.app.filtar.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


import com.app.filtar.BR;

import java.io.Serializable;

public class LoginModel extends BaseObservable implements Serializable {
    private String phone_code;
    private String phone;
    private boolean valid;

    public LoginModel() {
        phone_code = "+20";
        phone = "";
        valid = false;
    }

    public void isDataValid() {
        if (!phone.isEmpty()) {
            setValid(true);
        } else {
            setValid(false);
        }
    }

    @Bindable
    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
        notifyPropertyChanged(BR.phone_code);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
        isDataValid();

    }

    @Bindable
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        notifyPropertyChanged(BR.valid);
    }
}
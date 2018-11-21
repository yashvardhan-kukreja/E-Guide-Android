package com.eguide.yash1300.e_guide.listeners;

public interface LoginResultListener {

    void onSuccess(String token);
    void onFailure(String message);

}

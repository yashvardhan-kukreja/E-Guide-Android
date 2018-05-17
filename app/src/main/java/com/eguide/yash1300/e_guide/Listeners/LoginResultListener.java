package com.eguide.yash1300.e_guide.Listeners;

public interface LoginResultListener {

    void onSuccess(String token);
    void onFailure(String message);

}

package com.eguide.yash1300.e_guide.Listeners;

import com.eguide.yash1300.e_guide.NetworkModels.LoginResponse;

public interface LoginResultListener {

    void onSuccess(String token);
    void onFailure(String message);

}

package com.eguide.yash1300.e_guide.listeners.student;

import com.eguide.yash1300.e_guide.models.FavoriteModel;

import java.util.List;

public interface StudentFetchFavoriteTeachersListener {

    void onSuccess(String message, List<FavoriteModel> favTeachers);
    void onFailure(String message);

}

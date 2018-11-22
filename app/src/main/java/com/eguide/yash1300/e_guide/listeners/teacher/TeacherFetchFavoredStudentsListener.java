package com.eguide.yash1300.e_guide.listeners.teacher;

import com.eguide.yash1300.e_guide.models.FavoriteModel;

import java.util.List;

public interface TeacherFetchFavoredStudentsListener {

    void onSuccess(String message, List<FavoriteModel> favoredStudents);
    void onFailure(String message);

}

package com.eguide.yash1300.e_guide.listeners.student;

import com.eguide.yash1300.e_guide.models.TeacherModel;

import java.util.List;

public interface StudentFetchAllTeachersListener {

    void onSuccess(String message, List<TeacherModel> teachers);
    void onFailure(String message);

}

package com.eguide.yash1300.e_guide.listeners.student;

import com.eguide.yash1300.e_guide.models.StudentModel;

public interface StudentFetchAllDetailsListener {

    void onSuccess(String message, StudentModel student);
    void onFailure(String message);

}

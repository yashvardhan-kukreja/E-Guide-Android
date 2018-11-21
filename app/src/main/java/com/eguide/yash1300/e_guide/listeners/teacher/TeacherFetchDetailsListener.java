package com.eguide.yash1300.e_guide.listeners.teacher;

import com.eguide.yash1300.e_guide.models.TeacherModel;

public interface TeacherFetchDetailsListener {

    void onSuccess (String message, TeacherModel teacher);
    void onFailure (String message);

}

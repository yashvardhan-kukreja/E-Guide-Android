package com.eguide.yash1300.e_guide.Listeners;

import com.eguide.yash1300.e_guide.Models.TeacherModel;

public interface TeacherFetchDetailsListener {

    void onSuccess (String message, TeacherModel teacher);
    void onFailure (String message);

}

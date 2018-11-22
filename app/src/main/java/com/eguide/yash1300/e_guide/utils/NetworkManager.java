package com.eguide.yash1300.e_guide.utils;

import com.eguide.yash1300.e_guide.api.UntokenTeacherAPI;
import com.eguide.yash1300.e_guide.listeners.LoginResultListener;
import com.eguide.yash1300.e_guide.listeners.RegisterListener;
import com.eguide.yash1300.e_guide.listeners.student.StudentFavorTeacherListener;
import com.eguide.yash1300.e_guide.listeners.student.StudentFetchAllDetailsListener;
import com.eguide.yash1300.e_guide.listeners.student.StudentFetchAllTeachersListener;
import com.eguide.yash1300.e_guide.listeners.student.StudentFetchFavoriteTeachersListener;
import com.eguide.yash1300.e_guide.listeners.student.StudentUnfavorTeacherListener;
import com.eguide.yash1300.e_guide.listeners.teacher.TeacherCheckListener;
import com.eguide.yash1300.e_guide.listeners.teacher.TeacherFetchAllSkillsListener;
import com.eguide.yash1300.e_guide.listeners.teacher.TeacherFetchDetailsListener;
import com.eguide.yash1300.e_guide.api.AuthAPI;
import com.eguide.yash1300.e_guide.api.StudentAPI;
import com.eguide.yash1300.e_guide.api.TeacherAPI;
import com.eguide.yash1300.e_guide.listeners.teacher.TeacherSkillsAddedListener;
import com.eguide.yash1300.e_guide.models.TeacherModel;
import com.eguide.yash1300.e_guide.responses.BasicResponse;
import com.eguide.yash1300.e_guide.responses.LoginResponse;
import com.eguide.yash1300.e_guide.responses.student.StudentDetailsResponse;
import com.eguide.yash1300.e_guide.responses.student.StudentFetchFavoriteTeachersResponse;
import com.eguide.yash1300.e_guide.responses.student.StudentFetchTeachersResponse;
import com.eguide.yash1300.e_guide.responses.teacher.TeacherDetailsResponse;
import com.eguide.yash1300.e_guide.responses.teacher.TeacherFetchAllSkillsResponse;
import com.eguide.yash1300.e_guide.listeners.teacher.TeacherFetchFavoredStudentsListener;
import com.eguide.yash1300.e_guide.responses.teacher.TeacherFetchFavoredStudentsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static NetworkManager networkManager = null;

    Retrofit authBuilder = new Retrofit.Builder().baseUrl(GlobalConstants.BASE_URL_AUTH).addConverterFactory(GsonConverterFactory.create()).build();
    Retrofit studentBuilder = new Retrofit.Builder().baseUrl(GlobalConstants.BASE_URL_STUDENT).addConverterFactory(GsonConverterFactory.create()).build();
    Retrofit teacherBuilder = new Retrofit.Builder().baseUrl(GlobalConstants.BASE_URL_TEACHER).addConverterFactory(GsonConverterFactory.create()).build();
    Retrofit untokenTeacherBuilder = new Retrofit.Builder().baseUrl(GlobalConstants.BASE_URL_TEACHER_UNTOKEN).addConverterFactory(GsonConverterFactory.create()).build();

    AuthAPI authAPI = authBuilder.create(AuthAPI.class);
    StudentAPI studentAPI = studentBuilder.create(StudentAPI.class);
    TeacherAPI teacherAPI = teacherBuilder.create(TeacherAPI.class);
    UntokenTeacherAPI untokenTeacherAPI = untokenTeacherBuilder.create(UntokenTeacherAPI.class);

    public static NetworkManager getInstance() {
        if (networkManager == null)
            networkManager = new NetworkManager();
        return networkManager;
    }

    // Auth related API calls
    public void studentLogin(String email, String password, final LoginResultListener loginResultListener) {
        Call<LoginResponse> loginResponseCall = authAPI.studentLogin(email, password);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (!success) {
                    loginResultListener.onFailure(message);
                    return;
                }
                String token = response.body().getToken();
                loginResultListener.onSuccess(token);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                loginResultListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void teacherLogin(String email, String password, final LoginResultListener loginResultListener) {
        Call<LoginResponse> loginResponseCall = authAPI.teacherLogin(email, password);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (!success) {
                    loginResultListener.onFailure(message);
                    return;
                }
                String token = response.body().getToken();
                loginResultListener.onSuccess(token);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                loginResultListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void studentRegister(String name, String username, String email, String password, String contact, final RegisterListener studentRegisterListener) {
        Call<BasicResponse> studentRegisterCall = authAPI.studentRegister(name, username, email, password, contact);
        studentRegisterCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    studentRegisterListener.onSuccess(message);
                    return;
                }
                studentRegisterListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                studentRegisterListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void teacherRegister(String name, String username, String email, String password, String contact, String[] skills, final RegisterListener teacherRegisterListener) {
        Call<BasicResponse> teacherRegisterCall = authAPI.teacherRegister(name, username, email, password, contact, skills);
        teacherRegisterCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    teacherRegisterListener.onSuccess(message);
                    return;
                }
                teacherRegisterListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                teacherRegisterListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }


    // Teacher related API calls
    public void teacherFetchDetails(String token, final TeacherFetchDetailsListener teacherFetchDetailsListener) {
        Call<TeacherDetailsResponse> fetchTeacherDetails = teacherAPI.fetchDetails(token);
        fetchTeacherDetails.enqueue(new Callback<TeacherDetailsResponse>() {
            @Override
            public void onResponse(Call<TeacherDetailsResponse> call, Response<TeacherDetailsResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    teacherFetchDetailsListener.onSuccess(message, response.body().getTeacher());
                    return;
                }
                teacherFetchDetailsListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<TeacherDetailsResponse> call, Throwable t) {
                t.printStackTrace();
                teacherFetchDetailsListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void teacherFetchAllSkills(final TeacherFetchAllSkillsListener teacherFetchAllSkillsListener) {
        Call<TeacherFetchAllSkillsResponse> fetchAllSkillsResponseCall = untokenTeacherAPI.fetchAllSkills();
        fetchAllSkillsResponseCall.enqueue(new Callback<TeacherFetchAllSkillsResponse>() {
            @Override
            public void onResponse(Call<TeacherFetchAllSkillsResponse> call, Response<TeacherFetchAllSkillsResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    teacherFetchAllSkillsListener.onSuccess(response.body().getSkills());
                    return;
                }
                teacherFetchAllSkillsListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<TeacherFetchAllSkillsResponse> call, Throwable t) {
                t.printStackTrace();
                teacherFetchAllSkillsListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void teacherAddSkills(String token, String[] skill_ids, final RegisterListener registerListener) {
        Call<BasicResponse> addSkillsCall = teacherAPI.addSkills(token, skill_ids);
        addSkillsCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    registerListener.onSuccess(message);
                } else {
                    registerListener.onFailure(message);
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                registerListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });

    }

    public void untokenTeacherAddSkills(String teacher_email, String[] skills, final TeacherSkillsAddedListener teacherSkillsAddedListener) {
        Call<BasicResponse> addSkillsCall = untokenTeacherAPI.addSkills(teacher_email, skills);
        addSkillsCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    teacherSkillsAddedListener.onSuccess(message);
                    return;
                }
                teacherSkillsAddedListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                teacherSkillsAddedListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void checkTeacherExistense(String email, String username, final TeacherCheckListener teacherCheckListener) {
        Call<BasicResponse> checkTeacherCall = authAPI.teacherCheck(email, username);
        checkTeacherCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success){
                    teacherCheckListener.onSuccess();
                    return;
                }
                teacherCheckListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                teacherCheckListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void fetchTeacherFavoredStudents(String token, final TeacherFetchFavoredStudentsListener teacherFetchFavoredStudentsListener) {
        Call<TeacherFetchFavoredStudentsResponse> fetchFavoredStudentsResponseCall = teacherAPI.fetchFavoredStudents(token);
        fetchFavoredStudentsResponseCall.enqueue(new Callback<TeacherFetchFavoredStudentsResponse>() {
            @Override
            public void onResponse(Call<TeacherFetchFavoredStudentsResponse> call, Response<TeacherFetchFavoredStudentsResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    teacherFetchFavoredStudentsListener.onSuccess(message, response.body().getFavStudents());
                    return;
                }
                teacherFetchFavoredStudentsListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<TeacherFetchFavoredStudentsResponse> call, Throwable t) {
                t.printStackTrace();
                teacherFetchFavoredStudentsListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    // Student related API calls
    public void studentFetchAllDetails(String token, final StudentFetchAllDetailsListener studentFetchAllDetailsListener) {
        final Call<StudentDetailsResponse> studentDetailsResponseCall = studentAPI.fetchStudentDetails(token);
        studentDetailsResponseCall.enqueue(new Callback<StudentDetailsResponse>() {
            @Override
            public void onResponse(Call<StudentDetailsResponse> call, Response<StudentDetailsResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();

                if (success) {
                    studentFetchAllDetailsListener.onSuccess(message, response.body().getStudent());
                    return;
                }
                studentFetchAllDetailsListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<StudentDetailsResponse> call, Throwable t) {
                t.printStackTrace();
                studentFetchAllDetailsListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void studentFetchAllTeachers(String token, final StudentFetchAllTeachersListener studentFetchAllTeachersListener) {
        Call<StudentFetchTeachersResponse> studentFetchTeachersResponseCall = studentAPI.fetchAllTeachers(token);
        studentFetchTeachersResponseCall.enqueue(new Callback<StudentFetchTeachersResponse>() {
            @Override
            public void onResponse(Call<StudentFetchTeachersResponse> call, Response<StudentFetchTeachersResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                List<TeacherModel> teachers = response.body().getTeachers();
                if (success) {
                    studentFetchAllTeachersListener.onSuccess(message, teachers);
                    return;
                }
                studentFetchAllTeachersListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<StudentFetchTeachersResponse> call, Throwable t) {
                t.printStackTrace();
                studentFetchAllTeachersListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void favorTeacher(String token, String skillId, String teacherId, final StudentFavorTeacherListener studentFavorTeacherListener) {
        Call<BasicResponse> favorTeacherCall = studentAPI.favorTeacher(token, skillId, teacherId);
        favorTeacherCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    studentFavorTeacherListener.onSuccess(message);
                    return;
                }
                studentFavorTeacherListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                studentFavorTeacherListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void studentFetchFavoriteTeachers(String token, final StudentFetchFavoriteTeachersListener studentFetchFavoriteTeachersListener) {
        Call<StudentFetchFavoriteTeachersResponse> fetchFavoriteTeachersResponseCall = studentAPI.fetchFavTeachers(token);
        fetchFavoriteTeachersResponseCall.enqueue(new Callback<StudentFetchFavoriteTeachersResponse>() {
            @Override
            public void onResponse(Call<StudentFetchFavoriteTeachersResponse> call, Response<StudentFetchFavoriteTeachersResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    studentFetchFavoriteTeachersListener.onSuccess(message, response.body().getFavTeachers());
                    return;
                }
                studentFetchFavoriteTeachersListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<StudentFetchFavoriteTeachersResponse> call, Throwable t) {
                t.printStackTrace();
                studentFetchFavoriteTeachersListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void unfavorTeacher(String token, String teacherId, String skillId, final StudentUnfavorTeacherListener studentUnfavorTeacherListener) {
        Call<BasicResponse> unfavorTeacherCall = studentAPI.unfavorTeacher(token, teacherId, skillId);
        unfavorTeacherCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (success) {
                    studentUnfavorTeacherListener.onSuccess(message);
                    return;
                }
                studentUnfavorTeacherListener.onFailure(message);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                studentUnfavorTeacherListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }
}

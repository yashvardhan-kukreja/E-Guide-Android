package com.eguide.yash1300.e_guide.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eguide.yash1300.e_guide.R;
import com.eguide.yash1300.e_guide.listeners.student.StudentFavorTeacherListener;
import com.eguide.yash1300.e_guide.models.SkillModel;
import com.eguide.yash1300.e_guide.utils.NetworkManager;

import java.util.List;

public class StudentFavorTeacherSkillAdapter extends RecyclerView.Adapter<StudentFavorTeacherSkillAdapter.ViewHolder> {

    Context context;
    String token;
    List<SkillModel> skills;
    String teacherId;

    public StudentFavorTeacherSkillAdapter(Context context, String token, List<SkillModel> skills, String teacherId) {
        this.context = context;
        this.token = token;
        this.skills = skills;
        this.teacherId = teacherId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_student_favor_teacher, parent, false);
        return (new ViewHolder(rootView));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.skillname.setText(skills.get(position).getName());
        holder.studentFavorTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String skillId = skills.get(position).getId();
                NetworkManager.getInstance().favorTeacher(token, skillId, teacherId, new StudentFavorTeacherListener() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView skillname;
        LinearLayout studentFavorTeacher;

        public ViewHolder(View itemView) {
            super(itemView);

            skillname = itemView.findViewById(R.id.student_favor_teacher_skill_name);
            studentFavorTeacher = itemView.findViewById(R.id.student_favor_teacher);
        }
    }
}

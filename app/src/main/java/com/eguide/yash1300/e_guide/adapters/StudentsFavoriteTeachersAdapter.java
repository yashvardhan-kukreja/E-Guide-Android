package com.eguide.yash1300.e_guide.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eguide.yash1300.e_guide.R;
import com.eguide.yash1300.e_guide.activities.StudentHomeActivity;
import com.eguide.yash1300.e_guide.listeners.student.StudentUnfavorTeacherListener;
import com.eguide.yash1300.e_guide.models.FavoriteModel;
import com.eguide.yash1300.e_guide.models.TeacherModel;
import com.eguide.yash1300.e_guide.utils.NetworkManager;

import java.util.List;

public class StudentsFavoriteTeachersAdapter extends RecyclerView.Adapter<StudentsFavoriteTeachersAdapter.ViewHolder> {

    Context context;
    List<FavoriteModel> favoriteTeachers;
    String token;

    public StudentsFavoriteTeachersAdapter(Context context, List<FavoriteModel> favoriteTeachers, String token) {
        this.context = context;
        this.favoriteTeachers = favoriteTeachers;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_student_all_teachers, parent, false);
        return (new ViewHolder(rootView));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final TeacherModel teacher = favoriteTeachers.get(position).getTeacher();
        String name = teacher.getName();
        String email = teacher.getEmail();
        String contact = teacher.getContact();
        String skill = "- " + favoriteTeachers.get(position).getSkill().getName();

        holder.name.setText(name);
        holder.contact.setText(contact);
        holder.email.setText(email);
        holder.skill.setText(skill);

        holder.unfavorTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View confirmView = LayoutInflater.from(context).inflate(R.layout.dialog_unfavor_teacher_confirm, null, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(confirmView);
                Button confirmYes = confirmView.findViewById(R.id.unfavor_confirm_yes);
                Button confirmNo = confirmView.findViewById(R.id.unfavor_confirm_no);
                final AlertDialog confirmDialog = builder.create();
                confirmDialog.show();

                confirmYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmDialog.dismiss();

                        final ProgressDialog unfavorProgDialog = new ProgressDialog(context);
                        unfavorProgDialog.setTitle("Please wait");
                        unfavorProgDialog.setMessage("Removing the teacher from the favorites...");
                        unfavorProgDialog.setCancelable(false);
                        unfavorProgDialog.show();

                        NetworkManager.getInstance().unfavorTeacher(token, teacher.getId(), favoriteTeachers.get(position).getSkill().getId(), new StudentUnfavorTeacherListener() {
                            @Override
                            public void onSuccess(String message) {
                                unfavorProgDialog.dismiss();
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                context.startActivity(new Intent(context, StudentHomeActivity.class));
                            }

                            @Override
                            public void onFailure(String message) {
                                unfavorProgDialog.dismiss();
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });

                confirmNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmDialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteTeachers.size();
    }

    //TODO: Create interface for unfavoring a teacher

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, skill, email, contact;
        ImageView unfavorTeacher;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.adapter_fav_teacher_name);
            skill = itemView.findViewById(R.id.adapter_fav_teacher_skill);
            email = itemView.findViewById(R.id.adapter_fav_teacher_email);
            contact = itemView.findViewById(R.id.adapter_fav_teacher_contact);
            unfavorTeacher = itemView.findViewById(R.id.favor_teacher);

            unfavorTeacher.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp);

        }
    }
}

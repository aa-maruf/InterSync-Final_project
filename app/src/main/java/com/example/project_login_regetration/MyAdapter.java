package com.example.project_login_regetration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_login_regetration.Model.Data;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Data> dataList;
    private Context context;

    public MyAdapter(Context context, ArrayList<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data model = dataList.get(position);
        holder.bind(model);

        // Set a click listener for the card view
        holder.itemView.setOnClickListener(v -> {
            if (context instanceof allJob) {
                ((allJob) context).openDetailsActivity(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Add new data to the list
    public void setDataList(ArrayList<Data> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public ArrayList<Data> getDataList() {
        return dataList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView jobNameTextView;
        private TextView jobTitleTextView;
        private TextView jobDateTextView;
        private TextView jobDescriptionTextView;
        private TextView jobSkillsTextView;
        private TextView jobTEmailTextView;
        private TextView jobSalaryTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            jobNameTextView = itemView.findViewById(R.id.job_name);
            jobTitleTextView = itemView.findViewById(R.id.job_title);
            jobDateTextView = itemView.findViewById(R.id.job_post_date);
            jobDescriptionTextView = itemView.findViewById(R.id.job_descreption);
            jobSkillsTextView = itemView.findViewById(R.id.job_skills);
            jobTEmailTextView = itemView.findViewById(R.id.job_email);
            jobSalaryTextView = itemView.findViewById(R.id.job_salary);
        }

        public void bind(Data data) {
            jobNameTextView.setText("Company: " + data.getName());
            jobTitleTextView.setText("Job Title: " + data.getTitle());
            jobDateTextView.setText(data.getDate());
            jobDescriptionTextView.setText("Description: " + data.getDescription());
            jobSkillsTextView.setText("Skills: " + data.getSkills());
            jobTEmailTextView.setText("Email: " + data.getEmail());
            jobSalaryTextView.setText("Salary: " + data.getSalary());
        }
    }
}

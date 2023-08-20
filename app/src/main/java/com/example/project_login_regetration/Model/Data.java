package com.example.project_login_regetration.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    private String name;
    private String title;
    private String date;
    private String description;
    private String skills;
    private String salary;
    private String email;
    private String jobId; // Add this field

    public Data(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
//    public void setEmail(String email) {
//        this.email = email;
//    }

//    public Data(String email) {
//        this.email = email;
//    }

    public Data() {
        // Default constructor required for Firestore
    }

    public Data(String name, String title, String date, String description, String skills,String email, String salary) {
        this.name = name;
        this.title = title;
        this.date = date;
        this.description = description;
        this.skills = skills;
        this.email = email;
        this.salary = salary;
    }
    public String getTitle() {
        return title;
    }

    public String getName() {

        return name;
    }


    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getSkills() {
        return skills;
    }

    public String getEmail() {
        return email;
    }

    public String getSalary() {
        return salary;
    }

    // Parcelable implementation
    protected Data(Parcel in) {
        name = in.readString();
        title = in.readString();
        date = in.readString();
        description = in.readString();
        skills = in.readString();
        email = in.readString();
        salary = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(skills);
        dest.writeString(email);
        dest.writeString(salary);
    }
}

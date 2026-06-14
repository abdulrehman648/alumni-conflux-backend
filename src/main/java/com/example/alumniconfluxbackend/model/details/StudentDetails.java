package com.example.alumniconfluxbackend.model.details;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentDetails implements Serializable {
    private String department;
    private String degreeProgram;
    private String major;
    private Short currentSemester;
    private List<String> skills = new ArrayList<>();
    private List<String> careerPreferences = new ArrayList<>();

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Short getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(Short currentSemester) {
        this.currentSemester = currentSemester;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getCareerPreferences() {
        return careerPreferences;
    }

    public void setCareerPreferences(List<String> careerPreferences) {
        this.careerPreferences = careerPreferences;
    }
}

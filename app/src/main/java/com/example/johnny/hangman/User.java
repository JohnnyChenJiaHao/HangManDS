package com.example.johnny.hangman;

/**
 * Created by sammy on 08-05-2018.
 */

public class User {
    private String Student_Id;
    private String score;
    private String NumberOfTries;
    private String time_used;

    public User(String Student_Id, String score, String NumberOfTries, String time_used){
        this.Student_Id = Student_Id;
        this.score = score;
        this.NumberOfTries = NumberOfTries;
        this.time_used = time_used;
    }

    public String getStudent_Id() {
        return Student_Id;
    }

    public void setStudent_Id(String student_Id) {
        Student_Id = student_Id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNumberOfTries() {
        return NumberOfTries;
    }

    public void setNumberOfTries(String numberOfTries) {
        NumberOfTries = numberOfTries;
    }

    public String getTime_used() {
        return time_used;
    }

    public void setTime_used(String time_used) {
        this.time_used = time_used;
    }

    public String toString(){
        return getStudent_Id() + " " + getScore() + " " + getNumberOfTries() + " " + getTime_used();
    }
}

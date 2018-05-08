package com.example.johnny.hangman;

/**
 * Created by sammy on 08-05-2018.
 */

public class User {
    private String student_Id;
    private String score;
    private String numberOfTries;
    private String time_used;

    public User(String student_Id, String score, String numberOfTries, String time_used){
        this.student_Id = student_Id;
        this.score = score;
        this.numberOfTries = numberOfTries;
        this.time_used = time_used;
    }

    public String getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(String student_Id) {
        this.student_Id = student_Id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = "Score: " + score;
    }

    public String getNumberOfTries() {
        return numberOfTries;
    }

    public void setNumberOfTries(String numberOfTries) {
        this.numberOfTries = "Tries: " + numberOfTries;
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

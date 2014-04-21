package com.adgad.tflstatus;

/**
 * Created by arg01 on 16/09/2013.
 */
public class Status {
    String name;
    String status;
    String details;

    public Status(String name, String status, String details) {
        this.name = name;
        this.status = status;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFullDetails() {
        String full = this.getStatus();
        if(this.getDetails().length() > 0) {
            full += " - " + this.getDetails();
        }
        return full;
    }

    public boolean hasProblems() {
        return !(this.getStatus().equals("Good Service"));
    }

}

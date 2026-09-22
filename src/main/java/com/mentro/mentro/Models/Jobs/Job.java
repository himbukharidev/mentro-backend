package com.mentro.mentro.Models.Jobs;

import com.mentro.mentro.Models.Users.TeacherProfile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Jobs")
public class Job {
    @ManyToOne 
    private TeacherProfile teacher;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;
    private String jobTitle;
    private String jobDescription;
    private int jobPrice;
    private int jobDuration;
    private int jobSlots;
    private int jobStatus;

}

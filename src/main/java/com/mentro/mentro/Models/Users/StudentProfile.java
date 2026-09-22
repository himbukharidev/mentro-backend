package com.mentro.mentro.Models.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table (name = "Students")
public class StudentProfile {
    @OneToOne
    private Users user;
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int studentRoll;
    private String studentGender;
    private String studentSubjectsOfInterests;
    private String studentBio;
    // private String studentPic; for future
}

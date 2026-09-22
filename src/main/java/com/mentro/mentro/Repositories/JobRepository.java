package com.mentro.mentro.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentro.mentro.Models.Jobs.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {

    // List<Job> findByJobTitle(String jobTitle);

    List<Job> findByJobTitleContainingIgnoreCase(String jobTitle);

}

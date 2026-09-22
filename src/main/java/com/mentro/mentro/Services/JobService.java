package com.mentro.mentro.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentro.mentro.Models.Jobs.Job;
import com.mentro.mentro.Repositories.JobRepository;

@Service
public class JobService {
    private JobRepository repository;

    public List<Job> getJobs() {
        return repository.findAll();
    }

    public JobRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(JobRepository repository) {
        this.repository = repository;
    }

    public void add(Job job) {
        repository.save(job);
    }

    public List<Job> getByNames(String jobTitle) {
        return repository.findByJobTitle(jobTitle);
    }

}

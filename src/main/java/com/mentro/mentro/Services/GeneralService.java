package com.mentro.mentro.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentro.mentro.Models.Jobs.Job;
import com.mentro.mentro.Repositories.GeneralRepository;

@Service
public class GeneralService {
    private GeneralRepository repository;

    public List<Job> getJobs() {
        return repository.findAll();
    }






    public GeneralRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(GeneralRepository repository) {
        this.repository = repository;
    }

}

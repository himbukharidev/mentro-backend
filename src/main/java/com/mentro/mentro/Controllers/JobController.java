package com.mentro.mentro.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mentro.mentro.Models.Jobs.Job;
import com.mentro.mentro.Services.JobService;

@RestController
public class JobController {
    private JobService service;

    @GetMapping("/")
    public List<Job> allJobs() {
        return service.getJobs();
    }

    @PostMapping("/")
    public String addJob(@RequestBody Job job) {
        service.add(job);
        return "Success";
    }

    @GetMapping("/{jobTitle}")
    public List<Job> getByName(@PathVariable String jobTitle) {
        return service.getByNames(jobTitle);
    }

    public JobService getService() {
        return service;
    }

    @Autowired
    public void setService(JobService service) {
        this.service = service;
    }

}

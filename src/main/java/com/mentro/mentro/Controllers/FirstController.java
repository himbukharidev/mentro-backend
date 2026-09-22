package com.mentro.mentro.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentro.mentro.Models.Jobs.Job;
import com.mentro.mentro.Services.GeneralService;

@RestController
public class FirstController {
    private GeneralService service;

    @GetMapping("/")
    public List<Job> allJobs() {
        return service.getJobs();
    }








    
    public GeneralService getService() {
        return service;
    }

    @Autowired
    public void setService(GeneralService service) {
        this.service = service;
    }

}

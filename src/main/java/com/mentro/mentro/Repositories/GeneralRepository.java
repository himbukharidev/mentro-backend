package com.mentro.mentro.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentro.mentro.Models.Jobs.Job;

public interface GeneralRepository extends JpaRepository<Job, Integer> {

}

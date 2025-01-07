package com.example.company.controller;


import com.example.company.model.Company;
import com.example.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")

public class CompanyController {


    @Autowired
    CompanyService cs;


    @PostMapping("/insert")
    public String addCompany(@RequestBody Company c){
        cs.addCompany(c);
        return "Company added";
    }

    @GetMapping("/fetchCompanies")
    public void getCompany(){
        cs.getCompanies();
    }


}

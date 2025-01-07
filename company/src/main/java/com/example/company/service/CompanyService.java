package com.example.company.service;


import com.example.company.model.Company;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {


    List<Company> companies = new ArrayList<>();

    public void addCompany(Company c){
        companies.add(c);
    }

    public List<Company> getCompanies(){

        return new ArrayList<>(companies);
    }


}

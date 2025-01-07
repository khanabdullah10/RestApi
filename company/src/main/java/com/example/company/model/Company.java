package com.example.company.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Company {

    String cName;
    String cAddress;
    Long cReviews;

    @Override
    public String toString() {
        return "Company " +
                "Company name :" + cName + '\'' +
                " Company Address : " + cAddress + '\'' +
                " Company Reviews " + cReviews ;
    }
}

package com.supriseTest.restApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpResponse {
    private Integer empId;
    private String name;
    private Integer totalAwards;
    private Map<String,Integer> awardsGivenQuarterly;

}

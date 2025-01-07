package com.supriseTest.restApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpRequest {
    private Integer empId;
    private String name;
    private Map<String, List<String>> awardsGivenQuarterly;

}

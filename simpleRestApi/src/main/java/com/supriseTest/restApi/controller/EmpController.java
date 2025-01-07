package com.supriseTest.restApi.controller;

import com.supriseTest.restApi.model.EmpRequest;
import com.supriseTest.restApi.model.EmpResponse;
import exception.EmployeePerformanceException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emp")
public class EmpController {

    public Map<Integer, EmpResponse> database = new HashMap<>();

    @PostMapping("/insert")
    public String createEmployee(@RequestBody EmpRequest employeeRequest){

        if(employeeRequest.getAwardsGivenQuarterly() == null || employeeRequest.getAwardsGivenQuarterly().isEmpty())
            throw new EmployeePerformanceException("Employee performance is average");

        int totalAward = employeeRequest.getAwardsGivenQuarterly().values().stream().mapToInt(List::size).sum();

        Map<String,Integer> award = new HashMap<>();
        employeeRequest.getAwardsGivenQuarterly().forEach((a,n)-> award.put(a,n.size()));

        EmpResponse empResponse = new EmpResponse();
        empResponse.setEmpId(employeeRequest.getEmpId());
        empResponse.setName(employeeRequest.getName().toUpperCase());
        empResponse.setTotalAwards(totalAward);
        empResponse.setAwardsGivenQuarterly(award);

        database.put(empResponse.getEmpId(),empResponse);

        return "Employee created successfully";
    }


@GetMapping("/{empID}")
public EmpResponse getEmployee(@PathVariable int empID) {
    return database.get(empID);
}




}

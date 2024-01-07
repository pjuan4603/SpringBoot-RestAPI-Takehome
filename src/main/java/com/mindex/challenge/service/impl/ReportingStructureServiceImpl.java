package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.info("Acquiring reporting structure with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);

        // Create a queue to store employeeIds that should be checked
        Queue<Employee> checkQueue = new LinkedList<>();
        int reportingCount = 0;
        // Add the employee we found from the input employeeId as it's going to be the first to check
        checkQueue.offer(employee);


        // Will continuously check every employee and their direct reports
        while (!checkQueue.isEmpty()) {
            // Poll from the queue to get next employeeId
            String tempEmployeeId = checkQueue.poll().getEmployeeId();
            Employee tempEmployee = employeeRepository.findByEmployeeId(tempEmployeeId);

            // If this employee got direct reports
            if (tempEmployee != null && tempEmployee.getDirectReports() != null) {
                List<Employee> tempList = tempEmployee.getDirectReports();
                for (Employee e : tempList) {
                    // Add its direct reports to the queue for future checks
                    checkQueue.offer(e);
                }
                reportingCount += tempList.size();
            }
        }

        reportingStructure.setNumberOfReports(reportingCount);

        return reportingStructure;
    }
}

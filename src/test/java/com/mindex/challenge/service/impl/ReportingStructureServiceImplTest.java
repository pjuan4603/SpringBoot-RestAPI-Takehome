package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingUrl;

    private Map<String, Integer> testCases = new HashMap<>();

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    public ReportingStructureServiceImplTest() {
    }

    @Before
    public void setup() {
        reportingUrl = "http://localhost:" + port + "/reportingStructure/{id}";

        // Test cases for valid employeeId
        testCases.put("16a596ae-edd3-4847-99fe-c4518e82c86f", 4);
        testCases.put("03aa1462-ffa9-4978-901b-7c001562cf6f", 2);
        testCases.put("c0c2293d-16bd-4603-8e08-638a9d18b22c", 0);
    }

    @Test
    public void testNumberOfReportsCounting() {
        // Run through test cases
        for (Map.Entry<String, Integer> entry : testCases.entrySet()){
            ReportingStructure reportingStructure = restTemplate.getForEntity(reportingUrl, ReportingStructure.class, entry.getKey()).getBody();
            assertNotNull(reportingStructure);
            int actual = entry.getValue();
            int expected = reportingStructure.getNumberOfReports();
            assertEquals(reportingStructure.getEmployee().getEmployeeId(), entry.getKey());
            assertEquals(actual, expected);
        }
    }
}

package com.example.pharmacymanagement.Models;

import java.time.LocalDate;

public class Report {
    private String reportId;
    private String reportName;
    private String reportDate;
    private String reportDetails;

    public Report(String reportId, String reportName, LocalDate reportDate, String reportDetails) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.reportDate = String.valueOf(reportDate);
        this.reportDetails = reportDetails;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(String reportDetails) {
        this.reportDetails = reportDetails;
    }
}

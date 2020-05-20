package com.example.SmartWorker.Model;

public class MyJobsModel {
    private String Title,Budget,Category,Description,DueDate,JobLocation,ServiceType,JobType,ContactName,ContactNumber,ImageUrl;

    public MyJobsModel() {
    }

    public MyJobsModel(String title, String budget, String category, String description, String dueDate, String jobLocation, String serviceType, String jobType, String contactName, String contactNumber, String imageUrl) {
        Title = title;
        Budget = budget;
        Category = category;
        Description = description;
        DueDate = dueDate;
        JobLocation = jobLocation;
        ServiceType = serviceType;
        JobType = jobType;
        ContactName = contactName;
        ContactNumber = contactNumber;
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getJobLocation() {
        return JobLocation;
    }

    public void setJobLocation(String jobLocation) {
        JobLocation = jobLocation;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}

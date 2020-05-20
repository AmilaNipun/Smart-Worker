package com.example.SmartWorker.Model;

public class ServicesModel {
    private String ServiceTopic,ServiceDescription,ServiceLocation,ServiceContact,ImageURL;

    public ServicesModel() {
    }

    public ServicesModel(String serviceTopic, String serviceDescription, String serviceLocation, String serviceContact, String imageURL) {
        ServiceTopic = serviceTopic;
        ServiceDescription = serviceDescription;
        ServiceLocation = serviceLocation;
        ServiceContact = serviceContact;
        ImageURL = imageURL;
    }

    public String getServiceTopic() {
        return ServiceTopic;
    }

    public void setServiceTopic(String serviceTopic) {
        ServiceTopic = serviceTopic;
    }

    public String getServiceDescription() {
        return ServiceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        ServiceDescription = serviceDescription;
    }

    public String getServiceLocation() {
        return ServiceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        ServiceLocation = serviceLocation;
    }

    public String getServiceContact() {
        return ServiceContact;
    }

    public void setServiceContact(String serviceContact) {
        ServiceContact = serviceContact;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}

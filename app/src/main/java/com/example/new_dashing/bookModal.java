package com.example.new_dashing;

public class bookModal {
    String patientName, etSelectedLocation;

    public bookModal() {
    }

    public bookModal(String patientName, String etSelectedLocation) {
        this.patientName = patientName;
        this.etSelectedLocation = etSelectedLocation;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getEtSelectedLocation() {
        return etSelectedLocation;
    }

    public void setEtSelectedLocation(String etSelectedLocation) {
        this.etSelectedLocation = etSelectedLocation;
    }
}

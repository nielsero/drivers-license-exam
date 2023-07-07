package com.nielsero.driverslicenseexam;

/**
 * Candidate applying for drivers license exam.
 */
public class Candidate {
    private int id;
    private String name;
    private String bi;
    private int age;
    private String contact;
    private String examType;
    private String licenseCategory;

    /**
     * Creates a new candidate
     * @param id candidate id
     * @param name candidate name
     * @param bi candidate bi
     * @param age candidate age
     * @param contact candidate contact
     * @param examType type of exam candidate is applying for (theory/practice)
     * @param licenseCategory category of the license (heavy/light)
     */
    public Candidate(int id, String name, String bi, int age, String contact, String examType, String licenseCategory) {
        this.id = id;
        this.name = name;
        this.bi = bi;
        this.age = age;
        this.contact = contact;
        this.examType = examType;
        this.licenseCategory = licenseCategory;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBi() {
        return bi;
    }

    public int getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }

    public String getExamType() {
        return examType;
    }

    public String getLicenseCategory() {
        return licenseCategory;
    }

    @Override
    public String toString() {
        return name + ": " + bi;
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jegon
 */

public class AdminFile {

    private int ID;
    private String name;
    private String lastname_1;
    private String lastname_2;
    private String areaMuni;
    private String employment;
    private double salary;
    private Date admission_Date;
    private String email;
    private int phone;
    private int public_years;
    private int holidays;
    private int daysEarlyVacations;
    private int totalEarlyVacations;
    private boolean isEarlyVacations;
    private Role role;
    List<Vacations> vacations;
    List<LicenseCGS> cgs;
    List<LicenseSGS> sgs;
    List<LaboraInhability> inhability;
    List<EarlyVacations> early;
    List<VacationDays> days;

    public AdminFile() {
        this.ID = 0;
        this.lastname_1 = "";
        this.name = "";
        this.lastname_2 = "";
        this.employment = "";
        this.admission_Date = new Date();
        this.phone = 0;
        this.email = "";
        this.holidays = 0;
        this.public_years = 0;
        this.role = new Role();
        this.vacations = new ArrayList<>();
        this.cgs = new ArrayList<>();
        this.sgs = new ArrayList<>();
        this.inhability = new ArrayList<>();
        this.days = new ArrayList<>();
        this.early = new ArrayList<>();
        this.daysEarlyVacations = 0;
        this.totalEarlyVacations = 0;
        this.isEarlyVacations = false;
        this.salary = 0.0;
    }

    public AdminFile(int ID, String name, String lastname_1, String lastname_2,
            String areaMuni, String employment, double salary,Date admission_Date, String email,
            int phone, int public_years, int holidays, Role role, List<Vacations> vacations,
            List<LicenseCGS> cgs, List<LicenseSGS> sgs, List<LaboraInhability> inhability,
            List<EarlyVacations> early, List<VacationDays> days) {
        this.ID = ID;
        this.name = name;
        this.lastname_1 = lastname_1;
        this.lastname_2 = lastname_2;
        this.areaMuni = areaMuni;
        this.employment = employment;
        this.admission_Date = admission_Date;
        this.email = email;
        this.phone = phone;
        this.public_years = public_years;
        this.holidays = holidays;
        this.role = role;
        this.vacations = vacations;
        this.cgs = cgs;
        this.sgs = sgs;
        this.inhability = inhability;
        this.early = early;
        this.salary = salary;
        this.days = days;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname_1() {
        return lastname_1;
    }

    public void setLastname_1(String lastname_1) {
        this.lastname_1 = lastname_1;
    }

    public String getLastname_2() {
        return lastname_2;
    }

    public void setLastname_2(String lastname_2) {
        this.lastname_2 = lastname_2;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getAdmission_Date() {
        return admission_Date;
    }

    public void setAdmission_Date(Date admission_Date) {
        this.admission_Date = admission_Date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAreaMuni() {
        return areaMuni;
    }

    public void setAreaMuni(String areaMuni) {
        this.areaMuni = areaMuni;
    }

    public List<Vacations> getVacations() {
        return vacations;
    }

    public void setVacations(List<Vacations> vacations) {
        this.vacations = vacations;
    }

    public List<LicenseCGS> getCgs() {
        return cgs;
    }

    public void setCgs(List<LicenseCGS> cgs) {
        this.cgs = cgs;
    }

    public List<LicenseSGS> getSgs() {
        return sgs;
    }

    public void setSgs(List<LicenseSGS> sgs) {
        this.sgs = sgs;
    }

    public List<LaboraInhability> getInhability() {
        return inhability;
    }

    public void setInhability(List<LaboraInhability> inhability) {
        this.inhability = inhability;
    }

    public int getPublic_years() {
        return public_years;
    }

    public void setPublic_years(int public_years) {
        this.public_years = public_years;
    }

    public int getHolidays() {
        return holidays;
    }

    public void setHolidays(int holidays) {
        this.holidays = holidays;
    }

    public List<EarlyVacations> getEarly() {
        return early;
    }

    public void setEarly(List<EarlyVacations> early) {
        this.early = early;
    }

    public int getDaysEarlyVacations() {
        return daysEarlyVacations;
    }

    public void setDaysEarlyVacations(int daysEarlyVacations) {
        this.daysEarlyVacations = daysEarlyVacations;
    }

    public int getTotalEarlyVacations() {
        return totalEarlyVacations;
    }

    public void setTotalEarlyVacations(int totalEarlyVacations) {
        this.totalEarlyVacations = totalEarlyVacations;
    }

    public boolean getIsEarlyVacations() {
        return isEarlyVacations;
    }

    public void setIsEarlyVacations(boolean isEarlyVacations) {
        this.isEarlyVacations = isEarlyVacations;
    }

    public List<VacationDays> getDays() {
        return days;
    }

    public void setDays(List<VacationDays> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "AdminFile{" + "ID=" + ID + ", name=" + name + ", lastname_1=" + lastname_1 + 
                ", lastname_2=" + lastname_2 + ", areaMuni=" + areaMuni + 
                ", employment=" + employment + ", salary=" + salary + 
                ", admission_Date=" + admission_Date + ", email=" + email + 
                ", phone=" + phone + ", public_years=" + public_years + 
                ", holidays=" + holidays + ", daysEarlyVacations=" + daysEarlyVacations + 
                ", totalEarlyVacations=" + totalEarlyVacations + 
                ", isEarlyVacations=" + isEarlyVacations + ", role=" + role + 
                ", vacations=" + vacations + ", cgs=" + cgs + ", sgs=" + sgs + 
                ", inhability=" + inhability + ", early=" + early + ", days=" + days + '}';
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 *
 * @author f_var
 */
public class LaboraInhability {

    private int number_Inhability;
    private byte[] voucher ;
    private Date mainDate;
    private AdminFile file;

    public LaboraInhability() {
        this.number_Inhability = 0;
        this.file = new AdminFile();
        this.mainDate = new Date();
    }

    public int getNumber_Inhability() {
        return number_Inhability;
    }

    public void setNumber_Inhability(int number_Inhability) {
        this.number_Inhability = number_Inhability;
    }

    public byte[] getVoucher() {
        return voucher;
    }

    public void setVoucher(byte[] voucher) {
        this.voucher = voucher;
    }

    public AdminFile getFile() {
        return file;
    }

    public void setFile(AdminFile file) {
        this.file = file;
    }

    public Date getMainDate() {
        return mainDate;
    }

    public void setMainDate(Date mainDate) {
        this.mainDate = mainDate;
    }

    @Override
    public String toString() {
        return "LaboraInhability{" + "number_Inhability=" + number_Inhability + ", voucher=" + voucher + ", mainDate=" + mainDate + ", file=" + file + '}';
    }

}

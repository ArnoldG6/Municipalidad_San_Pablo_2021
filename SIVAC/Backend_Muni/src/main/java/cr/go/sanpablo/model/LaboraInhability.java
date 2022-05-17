/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author jegon
 */

@Entity(name = "Inhability")
@Table(name = "T_Inhability")
public class LaboraInhability implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "inhability_number")
    private int number_Inhability;

    @Column(name = "voucher")
    private byte[] voucher;
    
    @Column(name = "main_Date")
    private Date mainDate;
    
    @ManyToOne
    @JoinColumn(name = "id_inha", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private AdminFile file;

    public LaboraInhability() {
        this.number_Inhability = 0;
        this.voucher = new byte[100];
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

    public AdminFile getFile() {
        return file;
    }

    public void setFile(AdminFile file) {
        this.file = file;
    }

    public void setVoucher(byte[] voucher) {
        this.voucher = voucher;
    }

    public Date getMainDate() {
        return mainDate;
    }

    public void setMainDate(Date mainDate) {
        this.mainDate = mainDate;
    }

    @Override
    public String toString() {
        return "LaboraInhability{" + "number_Inhability=" + number_Inhability + ", voucher=" + voucher 
                + ", mainDate=" + mainDate + ", file=" + file + '}';
    }
}

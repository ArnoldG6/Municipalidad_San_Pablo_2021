/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.dao;

import cr.go.sanpablo.model.LaboraInhability;
import java.util.List;

/**
 *
 * @author jegon
 */
public interface LaboralDao {

    LaboraInhability insertLaboral(LaboraInhability lic);

    boolean deleteLaboral(int id);

    public List listAll();

    public LaboraInhability updateLaboral(LaboraInhability cgs);
}

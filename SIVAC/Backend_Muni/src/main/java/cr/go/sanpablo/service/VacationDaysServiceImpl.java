/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.VacationDaysDao;
import cr.go.sanpablo.dao.VacationDaysDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.model.Holidays;
import cr.go.sanpablo.model.VacationDays;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author jegon
 */
public class VacationDaysServiceImpl implements VacationDaysService {

    VacationDaysDao dao;
    AdminService admin;
    HolidayService holiday;

    @Override
    public VacationDays insertVacationDay(VacationDays vacation) throws DaoExceptions, SQLException, ServiceExceptions {
        VacationDays saved;
        dao = new VacationDaysDaoImpl();
        saved = this.dao.insertVacationDay(vacation);
        return saved;
    }

    @Override
    public List<VacationDays> listAllDays() throws DaoExceptions, SQLException, ServiceExceptions {
        List<VacationDays> days = null;
        this.dao = new VacationDaysDaoImpl();
        days = this.dao.listAllDays();
        return days;
    }

    @Override
    public VacationDays updateVacations(VacationDays vac) throws DaoExceptions, SQLException, ServiceExceptions {
        VacationDays updated;
        dao = new VacationDaysDaoImpl();
        this.admin = new AdminServImpl();
        int result = 0;
        AdminFile file = this.admin.searchByID(returnID(vac.getId_day()));
        if (isApproved(vac)) {
            result = file.getHolidays() - 1;
            if (isZero(file.getHolidays())) {
                if (greaterThanOrEqualToZero(result)) {
                    updated = this.dao.updateVacations(vac);
                    file.setHolidays(result);
                    this.admin.updateHolidays(file);
                } else {
                    updated = null;
                }
            } else {
                updated = null;
            }
        } else {
            updated = this.dao.updateVacations(vac);
        }
        return updated;
    }

    @Override
    public VacationDays searchByID(int id) throws DaoExceptions, SQLException, ServiceExceptions {
        VacationDays found;
        dao = new VacationDaysDaoImpl();
        found = this.dao.searchByID(id);
        return found;
    }

    private boolean isApproved(VacationDays vac) throws DaoExceptions, SQLException, ServiceExceptions {
        return vac.getStatus().equals("Aprobado");
    }

    private boolean isZero(int holidays) {
        return holidays != 0;
    }

    private int returnID(int vacation) throws DaoExceptions, SQLException, ServiceExceptions {
        this.admin = new AdminServImpl();
        List<AdminFile> files = this.admin.allFiles();
        for (AdminFile file : files) {
            List<VacationDays> vacations = file.getDays();
            for (VacationDays result : vacations) {
                if (result.getId_day() == vacation) {
                    return file.getID();
                }
            }
        }
        return 0;
    }

    private boolean greaterThanOrEqualToZero(int holidays) {
        return holidays >= 0;
    }

    @Override
    public boolean deleteDaysVacation(int id) throws DaoExceptions, SQLException, ServiceExceptions {
        dao = new VacationDaysDaoImpl();
        return this.dao.deleteDaysVacation(id);
    }

}

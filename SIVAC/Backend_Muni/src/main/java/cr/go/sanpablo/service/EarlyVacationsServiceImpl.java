/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.EarlyVacationsDao;
import cr.go.sanpablo.dao.EarlyVacationsDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.model.EarlyVacations;
import cr.go.sanpablo.model.Holidays;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author jegon
 */
public class EarlyVacationsServiceImpl implements EarlyVacationsService{
    private EarlyVacationsDao dao;    
    HolidayService holiday;
    AdminService admin;

    @Override
    public EarlyVacations insertVacations(EarlyVacations early) throws DaoExceptions, SQLException, ServiceExceptions {
        EarlyVacations saved = null;
        this.dao = new EarlyVacationsDaoImpl();
        this.admin = new AdminServImpl();
        AdminFile result = admin.searchByID(early.getFile().getID());
        if(result != null){
            int days = getDaysInsert(early);
            if(days != 0){
                int totalDays = days + result.getTotalEarlyVacations();
                result.setDaysEarlyVacations(totalDays * -1);
                result.setTotalEarlyVacations(totalDays);
                saved = this.dao.insertVacations(verifyNextYear(early));
                admin.updateEarlyVacations(result);
            }
        }
        return saved;
    }
    
    @Override
    public List<EarlyVacations> AllEarly() throws DaoExceptions, SQLException, ServiceExceptions{
        List early = null;
        dao = new EarlyVacationsDaoImpl();
        early = dao.AllEarly();
        return early;
    }
    
    private EarlyVacations verifyNextYear(EarlyVacations early) {
        Date start = early.getStart_Date();
        Date end = early.getFinal_Date();
        Date actual = new Date();
        GregorianCalendar fechastart = new GregorianCalendar();
        GregorianCalendar fechaend = new GregorianCalendar();
        GregorianCalendar actualDate = new GregorianCalendar();
        fechastart.setTime(start);
        fechaend.setTime(end);
        actualDate.setTime(actual);
        
        int yearStart = fechastart.get(Calendar.YEAR);
        int yearEnd = fechaend.get(Calendar.YEAR);
        int yearActual = actualDate.get(Calendar.YEAR);
        
        if (yearStart - yearActual == 1 && yearEnd - yearActual == 1) {
            return early;
        }
        
        return null;
    }
    
    private boolean verifyHoliday(int day, int month) throws DaoExceptions, SQLException, ServiceExceptions {
        holiday = new HolidayServiceImpl();
        List<Holidays> holidays = holiday.listAll();

        for (Holidays holy : holidays) {
            if (holy.getDay() == day && holy.getMonth() == month) {
                return true;
            }
        }
        return false;
    }
    
    private int getDaysInsert(EarlyVacations vac) throws DaoExceptions, SQLException, ServiceExceptions {

        GregorianCalendar fechaCalendario = new GregorianCalendar();
        Date start = vac.getStart_Date();
        Date end = vac.getFinal_Date();
        int totalDays = 0;

        while (end.compareTo(start) != -1) {
            fechaCalendario.setTime(start);
            int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);
            int dayMonth = fechaCalendario.get(Calendar.DAY_OF_MONTH);
            int month = fechaCalendario.get(Calendar.MONTH) + 1;

            if (diaSemana == 2 || diaSemana == 3 || diaSemana == 4
                    || diaSemana == 5 || diaSemana == 6) {
                if (!verifyHoliday(dayMonth, month)) {
                    totalDays++;
                }
            }
            Calendar c = Calendar.getInstance();
            c.setTime(start);
            c.add(Calendar.DATE, 1);
            start = c.getTime();
        }

        return totalDays;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.go.sanpablo.service;

import cr.go.sanpablo.dao.VacationsDao;
import cr.go.sanpablo.dao.VacationsDaoImpl;
import cr.go.sanpablo.excepciones.DaoExceptions;
import cr.go.sanpablo.excepciones.ServiceExceptions;
import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.model.Holidays;
import cr.go.sanpablo.model.VacationDays;
import cr.go.sanpablo.model.Vacations;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jegon
 */
public class VacationsServiceImpl implements VacationsService {

    VacationsDao dao;
    AdminService admin;
    HolidayService holiday;
    VacationDaysService days;

    @Override
    public Vacations insertVacations(Vacations vac) throws DaoExceptions, SQLException, ServiceExceptions {

        Vacations saved = null;
        this.dao = new VacationsDaoImpl();
        this.admin = new AdminServImpl();
        int days = getDaysInsert(vac);
        int result = 0;
        AdminFile file = this.admin.searchByID(vac.getFile().getID());
        if (days != 0) {
            if (isZero(file.getHolidays())) {
                result = file.getHolidays() - days;
                if (greaterThanOrEqualToZero(result)) {
                    saved = this.dao.insertVacations(verificarAnnio(vac));
                    addDaysVacations(vac);
                }
            }
        }
        return saved;

    }

    @Override
    public boolean deleteVacations(int number) throws DaoExceptions, SQLException, ServiceExceptions {
        this.dao = new VacationsDaoImpl();
        return this.dao.deleteVacations(number);
    }

    @Override
    public List<Vacations> allVacations() throws DaoExceptions, SQLException, ServiceExceptions {
        List vacas = null;
        dao = new VacationsDaoImpl();
        vacas = dao.listAllVacations();
        return vacas;
    }

    @Override
    public Vacations updateVacations(Vacations vac) throws DaoExceptions, SQLException, ServiceExceptions {
        Vacations updated;
        this.dao = new VacationsDaoImpl();
        updated = this.dao.updateVacations(vac);
        return updated;
    }

    private Vacations verificarAnnio(Vacations vac) throws DaoExceptions, SQLException, ServiceExceptions {
        if ((cumplioAniio(vac) && verifyVacationDays(vac)) && (returnLessDate(vac) || returnSameDate(vac))) {
            return vac;
        }
        return new Vacations();
    }

    private boolean cumplioAniio(Vacations vac) throws DaoExceptions, SQLException, ServiceExceptions {
        Date date = new Date();
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(date);
        this.admin = new AdminServImpl();
        AdminFile file = this.admin.searchByID(vac.getFile().getID());
        String fechaAdmision = new SimpleDateFormat("yyyy-MM-dd").format(file.getAdmission_Date());
        LocalDate mayor = LocalDate.parse(fechaActual, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate menor = LocalDate.parse(fechaAdmision, DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(menor, mayor);
        int years = Math.abs(period.getYears());
        return years >= 1;
    }

    private boolean verifyVacationDays(Vacations vac) {
        this.admin = new AdminServImpl();
        try {
            AdminFile file = this.admin.searchByID(vac.getFile().getID());
            return file.getHolidays() != 0;
        } catch (DaoExceptions | SQLException | ServiceExceptions ex) {
            Logger.getLogger(VacationsServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private int getDaysInsert(Vacations vac) throws DaoExceptions, SQLException, ServiceExceptions {

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

    private boolean returnLessDate(Vacations vac) {
        return vac.getStart_Date().before(vac.getFinal_Date());
    }

    private boolean returnSameDate(Vacations vac) {
        return vac.getStart_Date().equals(vac.getFinal_Date());
    }

    private boolean isZero(int holidays) {
        return holidays != 0;
    }

    private boolean greaterThanOrEqualToZero(int holidays) {
        return holidays >= 0;
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

    private void addDaysVacations(Vacations vacation) throws DaoExceptions, SQLException, ServiceExceptions {
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        Date start = vacation.getStart_Date();
        Date end = vacation.getFinal_Date();
        days = new VacationDaysServiceImpl();
        VacationDays vd = new VacationDays();
        while (end.compareTo(start) != -1) {
            fechaCalendario.setTime(start);
            int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);
            int dayMonth = fechaCalendario.get(Calendar.DAY_OF_MONTH);
            int month = fechaCalendario.get(Calendar.MONTH) + 1;

            if (diaSemana == 2 || diaSemana == 3 || diaSemana == 4
                    || diaSemana == 5 || diaSemana == 6) {
                if (!verifyHoliday(dayMonth, month)) {
                    vd.setDays(start);
                    vd.setFile(vacation.getFile());
                    vd.setStatus("Pendiente");
                    days.insertVacationDay(vd);
                }
            }
            Calendar c = Calendar.getInstance();
            c.setTime(start);
            c.add(Calendar.DATE, 1);
            start = c.getTime();
        }
    }
}

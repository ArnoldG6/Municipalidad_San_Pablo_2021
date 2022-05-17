package cr.go.sanpablo.model;

import cr.go.sanpablo.model.EarlyVacations;
import cr.go.sanpablo.model.LaboraInhability;
import cr.go.sanpablo.model.LicenseCGS;
import cr.go.sanpablo.model.LicenseSGS;
import cr.go.sanpablo.model.Role;
import cr.go.sanpablo.model.VacationDays;
import cr.go.sanpablo.model.Vacations;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-05-16T15:31:13")
@StaticMetamodel(AdminFile.class)
public class AdminFile_ { 

    public static volatile SingularAttribute<AdminFile, Integer> daysEarlyVacations;
    public static volatile SingularAttribute<AdminFile, Role> role;
    public static volatile SingularAttribute<AdminFile, Date> admission_Date;
    public static volatile SingularAttribute<AdminFile, Integer> totalEarlyVacations;
    public static volatile SingularAttribute<AdminFile, String> employment;
    public static volatile SingularAttribute<AdminFile, Double> salary;
    public static volatile SingularAttribute<AdminFile, Boolean> isEarlyVacations;
    public static volatile ListAttribute<AdminFile, LicenseCGS> cgs;
    public static volatile SingularAttribute<AdminFile, String> lastname_1;
    public static volatile SingularAttribute<AdminFile, Integer> public_years;
    public static volatile SingularAttribute<AdminFile, Integer> holidays;
    public static volatile ListAttribute<AdminFile, LaboraInhability> inhability;
    public static volatile SingularAttribute<AdminFile, Integer> phone;
    public static volatile SingularAttribute<AdminFile, String> lastname_2;
    public static volatile ListAttribute<AdminFile, Vacations> vacations;
    public static volatile SingularAttribute<AdminFile, String> name;
    public static volatile ListAttribute<AdminFile, VacationDays> days;
    public static volatile SingularAttribute<AdminFile, Integer> ID;
    public static volatile SingularAttribute<AdminFile, String> areaMuni;
    public static volatile SingularAttribute<AdminFile, String> email;
    public static volatile ListAttribute<AdminFile, LicenseSGS> sgs;
    public static volatile ListAttribute<AdminFile, EarlyVacations> early;

}
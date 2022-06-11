package cr.go.sanpablo.model.dav;

import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.model.VacationDays;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-06-11T15:18:42")
@StaticMetamodel(VacationDays.class)
public class VacationDays_ { 

    public static volatile SingularAttribute<VacationDays, Integer> id_day;
    public static volatile SingularAttribute<VacationDays, AdminFile> file;
    public static volatile SingularAttribute<VacationDays, Date> days;
    public static volatile SingularAttribute<VacationDays, String> status;

}
package cr.go.sanpablo.model.div;

import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.model.Vacations;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-06-05T13:42:59")
@StaticMetamodel(Vacations.class)
public class Vacations_ { 

    public static volatile SingularAttribute<Vacations, AdminFile> file;
    public static volatile SingularAttribute<Vacations, Integer> vacations_ID;
    public static volatile SingularAttribute<Vacations, Date> start_Date;
    public static volatile SingularAttribute<Vacations, Date> final_Date;
    public static volatile SingularAttribute<Vacations, String> status;

}
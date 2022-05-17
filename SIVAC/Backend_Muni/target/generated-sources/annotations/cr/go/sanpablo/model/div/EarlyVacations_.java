package cr.go.sanpablo.model.div;

import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.model.EarlyVacations;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-05-16T15:31:13")
@StaticMetamodel(EarlyVacations.class)
public class EarlyVacations_ { 

    public static volatile SingularAttribute<EarlyVacations, Integer> early_ID;
    public static volatile SingularAttribute<EarlyVacations, AdminFile> file;
    public static volatile SingularAttribute<EarlyVacations, Date> start_Date;
    public static volatile SingularAttribute<EarlyVacations, Date> final_Date;

}
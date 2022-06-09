package cr.go.sanpablo.model;

import cr.go.sanpablo.model.AdminFile;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-06-05T13:42:59")
@StaticMetamodel(LicenseSGS.class)
public class LicenseSGS_ { 

    public static volatile SingularAttribute<LicenseSGS, Integer> number_License;
    public static volatile SingularAttribute<LicenseSGS, AdminFile> file;
    public static volatile SingularAttribute<LicenseSGS, String> justification;
    public static volatile SingularAttribute<LicenseSGS, String> category;
    public static volatile SingularAttribute<LicenseSGS, Date> start_Date;
    public static volatile SingularAttribute<LicenseSGS, Date> final_Date;
    public static volatile SingularAttribute<LicenseSGS, String> status;

}
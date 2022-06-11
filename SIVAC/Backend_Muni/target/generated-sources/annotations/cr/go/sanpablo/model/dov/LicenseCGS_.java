package cr.go.sanpablo.model.dov;

import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.model.LicenseCGS;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-06-06T08:57:55")
@StaticMetamodel(LicenseCGS.class)
public class LicenseCGS_ { 

    public static volatile SingularAttribute<LicenseCGS, Integer> number_License;
    public static volatile SingularAttribute<LicenseCGS, byte[]> PDF;
    public static volatile SingularAttribute<LicenseCGS, AdminFile> file;
    public static volatile SingularAttribute<LicenseCGS, String> justification;
    public static volatile SingularAttribute<LicenseCGS, String> category;
    public static volatile SingularAttribute<LicenseCGS, Date> start_Date;
    public static volatile SingularAttribute<LicenseCGS, Date> final_Date;
    public static volatile SingularAttribute<LicenseCGS, String> status;

}
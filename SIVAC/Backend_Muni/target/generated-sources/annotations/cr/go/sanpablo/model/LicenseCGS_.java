package cr.go.sanpablo.model;

import cr.go.sanpablo.model.AdminFile;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-06-11T15:18:42")
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
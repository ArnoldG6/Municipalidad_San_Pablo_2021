package cr.go.sanpablo.model.dev;

import cr.go.sanpablo.model.AdminFile;
import cr.go.sanpablo.model.LaboraInhability;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-06-11T11:15:05")
@StaticMetamodel(LaboraInhability.class)
public class LaboraInhability_ { 

    public static volatile SingularAttribute<LaboraInhability, Date> mainDate;
    public static volatile SingularAttribute<LaboraInhability, AdminFile> file;
    public static volatile SingularAttribute<LaboraInhability, Integer> number_Inhability;
    public static volatile SingularAttribute<LaboraInhability, byte[]> voucher;

}
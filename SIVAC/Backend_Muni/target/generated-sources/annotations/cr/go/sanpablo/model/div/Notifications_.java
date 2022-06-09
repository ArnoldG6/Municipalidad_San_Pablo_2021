package cr.go.sanpablo.model.div;

import cr.go.sanpablo.model.Notifications;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-06-05T13:42:59")
@StaticMetamodel(Notifications.class)
public class Notifications_ { 

    public static volatile SingularAttribute<Notifications, Integer> id_Transmitter;
    public static volatile SingularAttribute<Notifications, Boolean> isRead;
    public static volatile SingularAttribute<Notifications, Integer> id_Notification;
    public static volatile SingularAttribute<Notifications, String> description;
    public static volatile SingularAttribute<Notifications, Integer> idReceiver;

}
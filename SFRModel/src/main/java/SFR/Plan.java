package SFR;

import common.model.User;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author arnold
 */
public class Plan {
    private int id;
    private String author;
    private String name;
    private String desc;
    private Date dateOfAdm;
    private HashMap<String, User> involvedList;
    private HashMap<String, Incidence> incidenceList;
    private HashMap<String, Risk> riskList;
    private HashMap<String, Comment> commentList;
}

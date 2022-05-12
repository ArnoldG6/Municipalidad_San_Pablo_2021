package sfr.dao;
/**
 *
 * @author ArnoldG6
 * Enum class for Transactions records
 */
public enum Transaction {
    INSERT_PLAN,
    DELETE_PLAN,
    EDIT_PLAN,
    INSERT_RISK,
    DELETE_RISK,
    EDIT_RISK,
    ASSOCIATE_RISK_TO_PLAN,
    DELETE_RISK_FROM_PLAN,
    ADD_INCIDENCE_TO_PLAN,
    DELETE_INCIDECE_FROM_PLAN,
    ADD_COMMENT_TO_PLAN,
    DELETE_COMMENT_FROM_PLAN,
    ADD_INVOLVED_TO_PLAN,
    DELETE_INVOLVED_FROM_PLAN
}

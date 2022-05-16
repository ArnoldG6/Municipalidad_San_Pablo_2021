-- Author: "Arnoldo González Quesada".
-- Contact me via: "arnoldgq612@gmail.com".
-- GitHub username: "ArnoldG6".
USE si_db;
DROP EVENT IF EXISTS delete_exp_codes;
CREATE EVENT delete_exp_codes
ON SCHEDULE EVERY 30 MINUTE
ON COMPLETION PRESERVE
DO DELETE FROM si_resetpass WHERE expirationTime < NOW() ;
SHOW EVENTS FROM si_db;
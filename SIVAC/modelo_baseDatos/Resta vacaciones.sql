DELIMITER //
CREATE PROCEDURE subtractHolidays(in idEmployee INT, in early INT) 
BEGIN
	UPDATE t_administrative_file SET holidays = holidays + early, early_vacations = 0 where id = idEmployee;
END 
//
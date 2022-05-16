DELIMITER //
CREATE PROCEDURE addHolidays(in dias INT, in idEmployee INT) 
BEGIN
	UPDATE t_administrative_file SET holidays = holidays + dias where id = idEmployee;
END 
//

DELIMITER //
CREATE PROCEDURE verifyEarlyHolidays() 
BEGIN
	/*DECLARACION DE VARIABLES*/
DECLARE var_id varchar(15);
DECLARE var_admission date;
DECLARE verificar_dia integer;
DECLARE verificar_mes integer;
DECLARE var_final INTEGER DEFAULT 0;
DECLARE var_early integer DEFAULT 0;

DECLARE vacations CURSOR FOR SELECT id, admission_Date, early_vacations from t_administrative_file;

	/*VARIABLE EN CASO DE QUE NO EXISTA UN DATO*/
DECLARE CONTINUE HANDLER FOR NOT FOUND SET var_final = 1;

	/*SE ABRE EL CURSOR*/
OPEN vacations;

holidays: LOOP
	/*RECORRE LA TABLA*/
FETCH vacations INTO var_id, var_admission, var_early;
SET @verificar_dia:= (select day(var_admission));
SET @verificar_mes:= (select month(var_admission));

/*VERIFICA SI YA RECORRIO TODO*/
IF var_final = 1 THEN
      LEAVE holidays;
END IF;

	/*VALIDACIONES PARA LAS VACACIONES Y SETEAR LAS VACACIONES Y VERIFICAR DIA Y MES*/
    if (select day(curdate())) = (@verificar_dia) and (select month(curdate())) = (@verificar_mes) then
	  call subtractHolidays(var_id, var_early);
	end if;	
END LOOP holidays;

	/*SE CIERRA EL CURSOR*/
CLOSE vacations;

END //

/*MODIFICAR PARA CADA 24 HORAS*/
CREATE EVENT updateEarlyHolidays
    ON SCHEDULE every 1 minute starts '2022-05-18 16:42:0000'
    on completion preserve
DO
call verifyEarlyHolidays();

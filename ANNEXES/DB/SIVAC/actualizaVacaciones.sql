
DELIMITER //
CREATE PROCEDURE verifyHolidays() 
BEGIN
	/*DECLARACION DE VARIABLES*/
DECLARE var_id varchar(15);
DECLARE var_admission date;
DECLARE var_public integer;
DECLARE annio_cumplido integer;
DECLARE total_annios integer;
DECLARE verificar_dia integer;
DECLARE verificar_mes integer;
DECLARE var_final INTEGER DEFAULT 0;


	/*DECLARACION DEL CURSOR*/
DECLARE vacations CURSOR FOR SELECT id, admission_Date, public_years from t_administrative_file;

	/*VARIABLE EN CASO DE QUE NO EXISTA UN DATO*/
DECLARE CONTINUE HANDLER FOR NOT FOUND SET var_final = 1;

	/*SE ABRE EL CURSOR*/
OPEN vacations;

holidays: LOOP
	/*RECORRE LA TABLA*/
FETCH vacations INTO var_id, var_admission, var_public;
SET @annio_cumplido:=(select datediff(curdate(),var_admission)/365);
SET @total_annios:=@annio_cumplido + var_public;
SET @verificar_dia:= (select day(var_admission));
SET @verificar_mes:=(select month(var_admission));

/*VERIFICA SI YA RECORRIO TODO*/
IF var_final = 1 THEN
      LEAVE holidays;
END IF;

	/*VALIDACIONES PARA LAS VACACIONES Y SETEAR LAS VACACIONES Y VERIFICAR DIA Y MES*/
    if (select day(curdate())) = (@verificar_dia) and (select month(curdate())) = (@verificar_mes) then
	  if @annio_cumplido>=1 then 
		if @total_annios < 5 then  call addHolidays(15, var_id);

		elseif @total_annios < 9 then call addHolidays(20, var_id);

		else call addHolidays(30,var_id) ;

		end if;
	 end if;
   end if;	
END LOOP holidays;

	/*SE CIERRA EL CURSOR*/
CLOSE vacations;

END //

/*MODIFICAR PARA CADA 24 HORAS*/
CREATE EVENT updateHolidays
    ON SCHEDULE every 3 minute starts '2021-12-16 17:03:0000'
    on completion preserve
DO
call verifyHolidays();



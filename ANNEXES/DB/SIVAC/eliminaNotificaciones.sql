CREATE EVENT deleteNotifications
ON SCHEDULE every 3 minute starts '2022-03-02 15:44:0000'
on completion preserve
DO
delete from t_notifications where is_Read=true;
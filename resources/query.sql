DECLARE
  resData VARCHAR2(2000); 
  tmp VARCHAR2(2000); 
  resStatus INTEGER := 0;
  n integer := 0;
BEGIN 
  WHILE (resStatus = 0 and n < 300) 
  LOOP
    BEGIN
      resStatus := SYS.DBMS_PIPE.RECEIVE_MESSAGE(:1, 0);
      SYS.DBMS_PIPE.UNPACK_MESSAGE(tmp);
      resData := resData || tmp || chr(10);
      n := n + 1;
      EXCEPTION 
        when others then exit; 
      END;
    END LOOP;
    select resData into :2 from dual;
END;
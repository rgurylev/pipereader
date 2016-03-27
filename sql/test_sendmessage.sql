declare
    s varchar2(10000);
    pipe_name VARCHAR2(100) := 'toto';
    res INTEGER;
begin
        DBMS_PIPE.PACK_MESSAGE('#' || 'тестовое сообщение');
        res := DBMS_PIPE.SEND_MESSAGE(pipe_name);
end;
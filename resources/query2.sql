DECLARE
  pipename VARCHAR(100) := :1;
  retVal TYPE1; 
BEGIN
  retVal := TYPE1('D Caruso', 'J Hamil', 'D Piro', 'R Singh');  
  :2 := retVal;
END;
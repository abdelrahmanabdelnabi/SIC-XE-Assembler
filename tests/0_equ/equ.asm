MAIN     START  0000         
FIRST    +LDA    #BEGIN
SECOND   +LDX    #FINAL
LOOP     ADDR   X,A
         TIX    #11
         JLT    LOOP
BEGIN    EQU    FIRST-SECOND+LOOP
DUMP     RESW   100
FINAL    EQU    LOOP+SECOND-DUMP-BEGIN 
         END    MAIN  
                  

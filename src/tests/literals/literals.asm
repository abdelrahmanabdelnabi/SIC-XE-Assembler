main    START   0
first   LDA    =C'FOO'
        LDS    =C'BAR'
        LDL    =C'FOO'
        J       next1
        LTORG
next1   LDB    =X'123456'
        LDS    =X'ABCDEF'
        LDT    =X'123456'
        J       next2
        LTORG
next2   LDA    =C'WUTWUT'
halt    J       halt
        END     first
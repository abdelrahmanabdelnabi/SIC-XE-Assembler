. Tests: base-relative, directives BASE, NOBASE

base	START	0xA000

. load B register and notify src.assembler
		+LDB	#b
        BASE	b

        LDA		#b
        LDA		#b
        RESB    2047
b       BYTE    C'FOO'

. ********** other **********
        LDA		#c
        NOBASE
       +LDA		#c
        RESB    2048
c       BYTE    C'BAR'

. Tests: base-relative, directives BASE, NOBASE

base	START	0xA000

. load B register and notify assembler
		+LDB	#b
        BASE	b
        .base-relative addressing: (B)+0
        LDA		#b
        .but pc-relative addressing prefered: (PC)+2047
        LDA		#b
        RESB    2047
        .b displaced by 2048 bytes
b       BYTE    C'FOO'

. ********** other **********
        .base-relative (since c-b < 4096)
        LDA		#c
        NOBASE
       .direct extended, LDA #c would fail here
       +LDA		#c
        RESB    2048
c       BYTE    C'BAR'

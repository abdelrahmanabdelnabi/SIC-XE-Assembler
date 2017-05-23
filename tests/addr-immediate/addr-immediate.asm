. don't run, examine generate code
. immediate addressing
imm		START	0
.imm		START	0x1000		. label first cannot be resolved
.
. ******* numbers *********
. absolute/direct
first	LDA		#0xAB
		LDA		#-1
		.min displacement
		LDA		#-2048
		.max displacement
        LDA		#2047
. extended format, absolute
       +LDA		#0x012345
	  .min address
       +LDA		#0
      .max address
       +LDA		#1048575

. ********** symbols **********
. absolute
.if absolute symbol then absolute addressing
        LDA		#5
. pc-relative
.pc-relative: (PC)+0
		LDA		#a
		.pc-relative: (PC)-3
a       LDA		#a
. base-relative
		+LDB	#b
        BASE	b
        .base-relative: (B)+0
        LDA		#b
        .but pc-relative prefered: (PC)+2047
        LDA		#b
        RESB    2047
        .b displaced by 2048 bytes
b       BYTE    C'FOO'

. careful: start address may be too large
.direct: pc-rel fail, base-rel fail
.		LDA		#first
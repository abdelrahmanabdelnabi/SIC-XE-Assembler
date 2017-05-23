. don't run, examine generate code
. indirect addressing
ind		START	0
.ind		START	0x1000		. label first cannot be resolved
.
. ******* numbers *********
. absolute/direct
first	LDA		@0xAB
		LDA		@0
        LDA		@4095
. extended format, absolute
       +LDA		@0x012345
	   +LDA		@0
       +LDA		@1048575

. ********** symbols **********
. absolute
        LDA		@5
. pc-relative
		LDA		@a
a       LDA		@a
. base-relative
		+LDB	@b
        BASE	b
        LDA		@b
        LDA		@b
        RESB    2047
b       BYTE    C'FOO'

. careful: start address may be too large
.		LDA		@first

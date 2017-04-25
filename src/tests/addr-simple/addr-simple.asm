. don't run, examine generate code
. immediate addressing
simple		START	0
.simple		START	0x1000		. label first cannot be resolved in SIC/XE
.simple		START	0x8000		. label first: fallback to old SIC
.
. ******* numbers *********
. absolute/direct
first	LDA		0xAB
		.+ indexed
		LDA		0xAB,X
		.min displacement
		LDA		0
        .max displacement
        LDA		4095

. extended format, absolute
		+LDA	0x012345
		.min address
		+LDA	0
		.max address
		+LDA	1048575

. ********** symbols **********
. absolute
        .if absolute symbol then absolute addressing
        LDA		5
. pc-relative
        .(PC)+0
		LDA		a
		.(PC)-3
a       LDA		a
		.+ indexed
		LDA		a,X
. base-relative
		+LDB	#b
		.(B)+0
        BASE	b
        LDA		b
        .+ indexed
		LDA		b,X
		.but pc-relative prefered: (PC)+2047
        LDA		b
        RESB    2047
        ..b displaced by 2048 bytes
b       BYTE    C'FOO'

. careful: start address may be too large
        .direct: pc-rel fail, base-rel fail
		LDA		first
		ORG		0x1000
c		FIX
		RESB	2084
		NOBASE
		.fallback to old SIC
		LDA		c


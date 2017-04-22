ind		START	0
first	LDA		@0xAB
		LDA		@0
        LDA		@4095
       +LDA		@0x012345
	   +LDA		0
       +LDA		@1048575
		+LDA		@a
a       +LDA		@a
		+LDB	@b
        LDA		@b
        LDA		@b
b       LDA     @a
		LDA		@first
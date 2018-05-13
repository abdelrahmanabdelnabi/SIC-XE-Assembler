JCC = javac
MAIN = sicXE/Asm.java
TARGET = SIC-XE-Assembler


all:
	$(JCC) -d ./build $(MAIN) && \
	cd build && \
	jar cf $(TARGET) * && \
	cp $(TARGET) ../


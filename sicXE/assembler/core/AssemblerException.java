package sicXE.assembler.core;

/*
  Created by abdelrahman on 4/7/17.
 */

/**
 * Used to to throw exceptions when encountering errors during assembling of object code
 *
 */
public class AssemblerException extends RuntimeException {

    public AssemblerException(String message) {
        super(message);
    }
}

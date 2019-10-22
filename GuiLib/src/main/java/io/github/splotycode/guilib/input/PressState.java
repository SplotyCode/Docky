package io.github.splotycode.guilib.input;

public enum PressState {

    /* Indicates that the button was pressed and is still pressed */
    DOWN,
    /* Indicates that the button was pressed NOW */
    PRESSED,
    /* Indicates that the button was pressed for so long that it gets repressed */
    REPRESSED,

}

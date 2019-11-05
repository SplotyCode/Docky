package io.github.splotycode.guilib.input;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class InputUser {

    private static int current = 0;

    public static InputUser create() {
        return new InputUser(++current);
    }

    protected void onUnlock(InputController controller) {}
    protected void onLock(InputController controller) {}

    private int id;

}

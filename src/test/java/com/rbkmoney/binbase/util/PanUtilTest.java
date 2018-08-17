package com.rbkmoney.binbase.util;

import org.junit.Test;

import static com.rbkmoney.binbase.util.PanUtil.toLongValue;
import static com.rbkmoney.binbase.util.PanUtil.validatePan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PanUtilTest {

    @Test
    public void testValidatePan() {
        validatePan("220020");
        validatePan("2200200249999999999");
        try {
            validatePan("22003/");
            fail();
        } catch (IllegalArgumentException ex) {

        }
        try {
            validatePan("22003");
            fail();
        } catch (IllegalArgumentException ex) {

        }
        try {
            validatePan("22002002499999999999");
            fail();
        } catch (IllegalArgumentException ex) {

        }
    }

    @Test
    public void testToLongValue() {
        assertEquals(220020000000000000L, toLongValue("220020"));
        assertEquals(220012323200000000L, toLongValue("22001232320"));
        assertEquals(234234234234234234L, toLongValue("234234234234234234"));
        assertEquals(324234234234234432L, toLongValue("3242342342342344324"));
        assertEquals(999999999999999999L, toLongValue("9999999999999999999"));
    }

}

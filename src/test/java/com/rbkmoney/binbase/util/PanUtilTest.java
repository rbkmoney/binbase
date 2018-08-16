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
        assertEquals(2200200000000000000L, toLongValue("220020"));
        assertEquals(2200123232000000000L, toLongValue("22001232320"));
        assertEquals(2342342342342342340L, toLongValue("234234234234234234"));
        assertEquals(3242342342342344324L, toLongValue("3242342342342344324"));
    }

}

package com.rbkmoney.binbase.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static com.rbkmoney.binbase.util.PanUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Slf4j
public class PanUtilTest {

    @Test
    public void testValidatePan() {
        validatePan("220020");
        validatePan("2200200249999999999");
        try {
            validatePan("22003/");
            fail();
        } catch (IllegalArgumentException ex) {
            log.debug("Invalid pan format");
        }
        try {
            validatePan("22003");
            fail();
        } catch (IllegalArgumentException ex) {
            log.debug("Invalid pan format");
        }
        try {
            validatePan("22002002499999999999");
            fail();
        } catch (IllegalArgumentException ex) {
            log.debug("Invalid pan format");
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

    @Test
    public void testFormatPan() {
        assertEquals("123", formatPan("123"));
        assertEquals("123322*", formatPan("123322/"));
        assertEquals("123322", formatPan("123322"));
        assertEquals("123322*", formatPan("1233222"));
        assertEquals("123322****", formatPan("1233222321"));
        assertEquals("233222*************", formatPan("2332222312312312333"));
    }

}

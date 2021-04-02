package com.rbkmoney.binbase.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import com.rbkmoney.binbase.domain.BinRange;
import org.junit.Test;

import java.util.*;

import static com.rbkmoney.binbase.util.BinRangeUtil.getLastIntersectionRanges;
import static com.rbkmoney.binbase.util.BinRangeUtil.subtractFromRange;
import static org.junit.Assert.assertEquals;

public class BinRangeUtilTest {

    @Test
    public void testGetLastIntersectionRanges() {
        List<BinRange> binRanges = Arrays.asList(
                new BinRange(Range.openClosed(0L, 50L), 1L, 1L),
                new BinRange(Range.openClosed(50L, 90L), 1L, 1L),
                new BinRange(Range.openClosed(90L, 100L), 1L, 1L),
                new BinRange(Range.openClosed(10L, 50L), 2L, 1L),
                new BinRange(Range.openClosed(50L, 60L), 2L, 1L),
                new BinRange(Range.openClosed(60L, 90L), 2L, 1L),
                new BinRange(Range.openClosed(20L, 50L), 3L, 1L),
                new BinRange(Range.openClosed(50L, 60L), 3L, 1L)
        );

        assertEquals(
                Arrays.asList(
                        new BinRange(Range.openClosed(0L, 10L), 1L, 1L),
                        new BinRange(Range.openClosed(10L, 20L), 2L, 1L),
                        new BinRange(Range.openClosed(20L, 50L), 3L, 1L),
                        new BinRange(Range.openClosed(50L, 60L), 3L, 1L),
                        new BinRange(Range.openClosed(60L, 90L), 2L, 1L),
                        new BinRange(Range.openClosed(90L, 100L), 1L, 1L)
                ),
                getLastIntersectionRanges(binRanges)
        );

        binRanges = Arrays.asList(
                new BinRange(Range.openClosed(0L, 50L), 1L, 1L),
                new BinRange(Range.openClosed(50L, 90L), 1L, 1L),
                new BinRange(Range.openClosed(90L, 100L), 1L, 1L),
                new BinRange(Range.openClosed(100L, 200L), 1L, 1L),
                new BinRange(Range.openClosed(200L, 300L), 1L, 1L),
                new BinRange(Range.openClosed(10L, 50L), 2L, 1L),
                new BinRange(Range.openClosed(50L, 290L), 2L, 1L)
        );
        assertEquals(
                Arrays.asList(
                        new BinRange(Range.openClosed(0L, 10L), 1L, 1L),
                        new BinRange(Range.openClosed(10L, 50L), 2L, 1L),
                        new BinRange(Range.openClosed(50L, 90L), 2L, 1L),
                        new BinRange(Range.openClosed(90L, 100L), 2L, 1L),
                        new BinRange(Range.openClosed(100L, 200L), 2L, 1L),
                        new BinRange(Range.openClosed(200L, 290L), 2L, 1L),
                        new BinRange(Range.openClosed(290L, 300L), 1L, 1L)
                ),
                getLastIntersectionRanges(binRanges)
        );

        BinRange binRange = new BinRange(Range.openClosed(0L, 50L), 1L, 1L);
        assertEquals(
                Arrays.asList(binRange),
                getLastIntersectionRanges(Arrays.asList(binRange))
        );
    }

    @Test
    public void testSubtractFromRange() {
        assertEquals(
                Collections.singleton(Range.openClosed(30, 50)),
                subtractFromRange(Range.openClosed(10, 50), Arrays.asList(Range.openClosed(0, 30)))
        );

        assertEquals(
                Collections.singleton(Range.openClosed(20, 40)),
                subtractFromRange(
                        Range.openClosed(10, 50),
                        Arrays.asList(Range.openClosed(0, 20), Range.openClosed(40, 60)))
        );

        assertEquals(
                ImmutableSet.of(Range.openClosed(20, 25), Range.openClosed(26, 35), Range.openClosed(36, 40)),
                subtractFromRange(
                        Range.openClosed(10, 50),
                        Arrays.asList(
                                Range.openClosed(35, 36),
                                Range.openClosed(25, 26),
                                Range.openClosed(0, 20),
                                Range.openClosed(40, 60)))
        );
    }

}

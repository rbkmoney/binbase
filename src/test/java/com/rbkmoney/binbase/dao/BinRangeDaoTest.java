package com.rbkmoney.binbase.dao;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.AbstractIntegrationTest;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.BinRange;
import com.rbkmoney.binbase.exception.DaoException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Transactional
public class BinRangeDaoTest extends AbstractIntegrationTest {

    @Autowired
    private BinRangeDao binRangeDao;

    @Autowired
    private BinDataDao binDataDao;

    @Test
    public void testSaveAndGetByCardPanAndRange() throws DaoException {
        BinData binData = random(BinData.class);
        long binDataId = binDataDao.save(binData);

        BinRange binRange = new BinRange();
        binRange.setRange(Range.openClosed(1000000000000000000L, 2000000000000000000L));
        binRange.setVersionId(1L);
        binRange.setBinDataId(binDataId);
        binRangeDao.save(binRange);
        assertEquals(binRange, binRangeDao.getIntersectionRanges(binRange.getRange()).get(0));
    }

    @Test
    public void testWhenRangesConflict() throws DaoException {
        BinData binData = random(BinData.class);
        long binDataId = binDataDao.save(binData);

        BinRange binRange = new BinRange();
        binRange.setRange(Range.openClosed(1000000000000000000L, 2000000000000000000L));
        binRange.setVersionId(1L);
        binRange.setBinDataId(binDataId);
        binRangeDao.save(binRange);

        binRange.setRange(Range.openClosed(1500000000000000000L, 2500000000000000000L));
        binRangeDao.save(binRange);

        assertEquals(1, binRangeDao.getIntersectionRanges(Range.openClosed(1000000000000000000L, 2500000000000000000L)).size());
    }

    @Test
    public void testMergeAdjacent() throws DaoException {
        BinData binData = random(BinData.class);
        long binDataId = binDataDao.save(binData);

        BinRange binRange = new BinRange();
        binRange.setRange(Range.openClosed(1000000000000000000L, 2000000000000000000L));
        binRange.setVersionId(1L);
        binRange.setBinDataId(binDataId);
        binRangeDao.save(binRange);

        binRange.setRange(Range.openClosed(2000000000000000000L, 3000000000000000000L));
        binRangeDao.save(binRange);

        binRange.setRange(Range.openClosed(3000000000000000000L, 4000000000000000000L));
        binRangeDao.save(binRange);

        assertEquals(1, binRangeDao.getIntersectionRanges(Range.openClosed(1000000000000000000L, 2000000000000000000L)).size());
    }

    @Test
    public void testBatchInsertBinRanges() throws DaoException {
        BinData binData = random(BinData.class);
        long binDataId = binDataDao.save(binData);
        binRangeDao.save(
                Arrays.asList(
                        new BinRange(1000000000000000000L, 2000000000000000000L, 1L, binDataId),
                        new BinRange(2000000000000000000L, 3000000000000000000L, 1L, binDataId),
                        new BinRange(3000000000000000000L, 4000000000000000000L, 1L, binDataId)
                )
        );

    }

}

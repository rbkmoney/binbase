package com.rbkmoney.binbase.dao;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.AbstractIntegrationTest;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.BinRange;
import com.rbkmoney.binbase.exception.DaoException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

@Transactional
public class BinDataDaoTest extends AbstractIntegrationTest {

    @Autowired
    private BinDataDao binDataDao;

    @Autowired
    private BinRangeDao binRangeDao;

    @Test
    public void testGetBinDataByPan() throws DaoException {
        BinData binData = random(BinData.class);
        long binDataId = binDataDao.save(binData);

        binRangeDao.save(new BinRange(1000000000000000000L, 2000000000000000000L, 1L, binDataId));
        assertEquals(binData, binDataDao.getBinDataByCardPan(1230679997775486545L).getValue());
        assertEquals(binData, binDataDao.getBinDataByCardPanAndVersion(1230679997775486545L, 1).getValue());
    }

    @Test
    public void testSaveBinData() throws DaoException {
        BinData binData = random(BinData.class);

        long binDataId = binDataDao.save(binData);
        assertEquals(binDataId, binDataDao.save(binData));
        assertEquals(binData, binDataDao.get(binDataId));
    }

    @Test
    public void testSaveBinDataWithEmptyValues() throws DaoException {
        BinData binData = new BinData();
        binData.setPaymentSystem("visa");

        long binDataId = binDataDao.save(binData);
        assertEquals(binDataId, binDataDao.save(binData));
        assertEquals(binData, binDataDao.get(binDataId));
    }

}

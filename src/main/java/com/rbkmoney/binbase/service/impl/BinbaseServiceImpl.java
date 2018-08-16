package com.rbkmoney.binbase.service.impl;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.dao.BinDataDao;
import com.rbkmoney.binbase.dao.BinRangeDao;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.BinRange;
import com.rbkmoney.binbase.exception.BinNotFoundException;
import com.rbkmoney.binbase.exception.DaoException;
import com.rbkmoney.binbase.exception.StorageException;
import com.rbkmoney.binbase.service.BinbaseService;
import com.rbkmoney.binbase.util.BinRangeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rbkmoney.binbase.util.PanUtil.toLongValue;

@Service
public class BinbaseServiceImpl implements BinbaseService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BinRangeDao binRangeDao;

    private final BinDataDao binDataDao;

    @Autowired
    public BinbaseServiceImpl(BinRangeDao binRangeDao, BinDataDao binDataDao) {
        this.binRangeDao = binRangeDao;
        this.binDataDao = binDataDao;
    }

    @Override
    public Map.Entry<Long, BinData> getBinDataByCardPan(String pan) throws BinNotFoundException, StorageException, IllegalArgumentException {
        try {
            return binDataDao.getBinDataByCardPan(toLongValue(pan));
        } catch (DaoException ex) {
            throw new StorageException(ex);
        }
    }

    @Override
    public Map.Entry<Long, BinData> getBinDataByCardPanAndVersion(String pan, long version) throws BinNotFoundException, StorageException, IllegalArgumentException {
        try {
            return binDataDao.getBinDataByCardPanAndVersion(toLongValue(pan), version);
        } catch (DaoException ex) {
            throw new StorageException(String.format("Failed to get bin data by card pan and version, cardPan='%s', version='%d'", pan, version), ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRange(BinData binData, Range<Long> range) throws StorageException {
        try {
            long binDataId = binDataDao.save(binData);
            List<BinRange> lastIntersectionRanges = BinRangeUtil.getLastIntersectionRanges(binRangeDao.getIntersectionRanges(range));

            List<Range<Long>> intersectionRanges = new ArrayList<>();
            List<BinRange> newBinRanges = new ArrayList<>();
            for (BinRange intersectionRange : lastIntersectionRanges) {
                if (intersectionRange.getRange().isConnected(range)
                        && !intersectionRange.getRange().intersection(range).isEmpty()) {
                    intersectionRanges.add(intersectionRange.getRange());
                    if (intersectionRange.getBinDataId().equals(binDataId)) {
                        continue;
                    }
                    Range<Long> intersectRange = intersectionRange.getRange().intersection(range);
                    newBinRanges.add(new BinRange(intersectRange, intersectionRange.getVersionId() + 1L, binDataId));
                }
            }

            List<BinRange> otherRanges = BinRangeUtil.subtractFromRange(range, intersectionRanges)
                    .stream().map(
                            subtractRange -> new BinRange(subtractRange, 1L, binDataId)
                    ).collect(Collectors.toList());
            newBinRanges.addAll(otherRanges);

            binRangeDao.save(newBinRanges);
        } catch (DaoException ex) {
            throw new StorageException(String.format("Failed to save range, binData='%s', range='%s'", binData, range), ex);
        }
    }

}

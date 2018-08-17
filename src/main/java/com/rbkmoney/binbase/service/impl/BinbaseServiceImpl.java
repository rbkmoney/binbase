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
            log.info("Trying to get bin data by pan, pan='{}'", pan);
            Map.Entry<Long, BinData> binDataWithVersion = binDataDao.getBinDataByCardPan(toLongValue(pan));
            log.info("Bin data have been retrieved, pan='{}', binDataWithVersion='{}'", pan, binDataWithVersion);
            return binDataWithVersion;
        } catch (DaoException ex) {
            throw new StorageException(String.format("Failed to get bin data by card pan, pan='%s'", pan), ex);
        }
    }

    @Override
    public Map.Entry<Long, BinData> getBinDataByCardPanAndVersion(String pan, long version) throws BinNotFoundException, StorageException, IllegalArgumentException {
        try {
            log.info("Trying to get bin data by pan and version, pan='{}', version='{}'", pan, version);
            Map.Entry<Long, BinData> binDataWithVersion = binDataDao.getBinDataByCardPanAndVersion(toLongValue(pan), version);
            log.info("Bin data have been retrieved, pan='{}', version='{}', binDataWithVersion='{}'", pan, version, binDataWithVersion);
            return binDataWithVersion;
        } catch (DaoException ex) {
            throw new StorageException(String.format("Failed to get bin data by card pan and version, pan='%s', version='%d'", pan, version), ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRange(BinData binData, Range<Long> range) throws StorageException {
        log.info("Trying to save bin range, binData='{}', range='{}'", binData, range);
        try {
            long binDataId = saveBinData(binData);
            List<BinRange> lastIntersectionRanges = getLastIntersectionBinRanges(range);

            List<Range<Long>> intersectionRanges = new ArrayList<>();
            List<BinRange> newBinRanges = new ArrayList<>();
            for (BinRange intersectionRange : lastIntersectionRanges) {
                if (intersectionRange.getRange().isConnected(range)
                        && !intersectionRange.getRange().intersection(range).isEmpty()) {
                    Range<Long> newIntersectRange = intersectionRange.getRange().intersection(range);
                    intersectionRanges.add(newIntersectRange);
                    if (intersectionRange.getBinDataId().equals(binDataId)) {
                        log.info("Range of intersections with same data was found, binData='{}', range='{}', intersectionRange='{}'. Skipped...", binData, range, intersectionRange);
                        continue;
                    }
                    newBinRanges.add(new BinRange(newIntersectRange, intersectionRange.getVersionId() + 1L, binDataId));
                }
            }
            if (!newBinRanges.isEmpty()) {
                log.info("Ranges with new version was created, binData='{}', range='{}', rangesWithNewVersion='{}'", binData, range, newBinRanges);
            }

            List<BinRange> otherRanges = BinRangeUtil.subtractFromRange(range, intersectionRanges).stream()
                    .map(
                            subtractRange -> new BinRange(subtractRange, 1L, binDataId)
                    ).collect(Collectors.toList());
            if (!otherRanges.isEmpty()) {
                log.info("New ranges was created, binData='{}', range='{}', newRanges='{}'", binData, range, otherRanges);
                newBinRanges.addAll(otherRanges);
            }

            binRangeDao.save(newBinRanges);
            log.info("Bin range have been saved, binData='{}', binRanges='{}'", binData, newBinRanges);
        } catch (DaoException ex) {
            throw new StorageException(String.format("Failed to save range, binData='%s', range='%s'", binData, range), ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public long saveBinData(BinData binData) throws StorageException {
        try {
            log.info("Trying to save bin data, binData='{}'", binData);
            long id = binDataDao.save(binData);
            log.info("Bin data have been saved, id='{}', binData='{}'", id, binData);
            return id;
        } catch (DaoException ex) {
            throw new StorageException(String.format("Failed to save bin data, binData='%s'", binData), ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<BinRange> getLastIntersectionBinRanges(Range<Long> range) throws StorageException {
        try {
            log.info("Trying to get last ranges of intersections, range='{}'", range);
            List<BinRange> lastIntersectionRanges = BinRangeUtil.getLastIntersectionRanges(binRangeDao.getIntersectionRanges(range));
            log.info("Last ranges of intersections have been retrieved, range='{}', lastIntersectionRanges='{}'", range, lastIntersectionRanges);
            return lastIntersectionRanges;
        } catch (DaoException ex) {
            throw new StorageException(String.format("Failed to get last ranges of intersections, range='%s'", range), ex);
        }
    }

}

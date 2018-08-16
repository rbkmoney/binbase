package com.rbkmoney.binbase.service;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.exception.BinNotFoundException;
import com.rbkmoney.binbase.exception.StorageException;

import java.util.Map;

public interface BinbaseService {

    Map.Entry<Long, BinData> getBinDataByCardPan(String pan) throws BinNotFoundException, StorageException, IllegalArgumentException;

    Map.Entry<Long, BinData> getBinDataByCardPanAndVersion(String pan, long version) throws BinNotFoundException, StorageException, IllegalArgumentException;

    void saveRange(BinData binData, Range<Long> range) throws StorageException;

}

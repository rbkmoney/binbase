package com.rbkmoney.binbase.dao;

import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.exception.DaoException;

import java.util.Map;

public interface BinDataDao {

    Map.Entry<Long, BinData> getBinDataByCardPan(long pan) throws DaoException;

    Map.Entry<Long, BinData> getBinDataByCardPanAndVersion(long pan, long versionId) throws DaoException;

    BinData get(long id) throws DaoException;

    long save(BinData binData) throws DaoException;

}

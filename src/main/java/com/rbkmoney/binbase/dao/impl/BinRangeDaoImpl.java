package com.rbkmoney.binbase.dao.impl;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.dao.BinRangeDao;
import com.rbkmoney.binbase.domain.BinRange;
import com.rbkmoney.binbase.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class BinRangeDaoImpl extends NamedParameterJdbcDaoSupport implements BinRangeDao {

    private final RowMapper<BinRange> binRangeRowMapper = (rs, i) -> new BinRange(
            rs.getLong("lower"),
            rs.getLong("upper"),
            rs.getLong("version_id"),
            rs.getLong("bin_data_id")
    );

    @Autowired
    public BinRangeDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public BinRange getBinRangeByCardPan(long pan) throws DaoException {
        try {
            String namedSql = "SELECT lower(range) as lower, upper(range) as upper, version_id, bin_data_id FROM binbase.bin_range WHERE range @> :pan ORDER BY version_id DESC LIMIT 1";
            return getNamedParameterJdbcTemplate()
                    .queryForObject(
                            namedSql,
                            new MapSqlParameterSource("pan", pan),
                            binRangeRowMapper
                    );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (NestedRuntimeException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public BinRange getBinRangeByCardPanAndVersion(long pan, int versionId) throws DaoException {
        try {
            String namedSql = "SELECT lower(range) as lower, upper(range) AS upper, version_id, bin_data_id FROM binbase.bin_range WHERE range @> :pan AND version_id = :version_id";
            return getNamedParameterJdbcTemplate()
                    .queryForObject(
                            namedSql,
                            new MapSqlParameterSource()
                                    .addValue("pan", pan)
                                    .addValue("version_id", versionId),
                            binRangeRowMapper
                    );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (NestedRuntimeException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public List<BinRange> getIntersectionRanges(Range<Long> range) throws DaoException {
        try {
            String namedSql = "SELECT lower(range) as lower, upper(range) as upper, version_id, bin_data_id FROM binbase.bin_range WHERE range && int8range(:lower, :upper, '[)')";
            return getNamedParameterJdbcTemplate()
                    .query(
                            namedSql,
                            new MapSqlParameterSource()
                                    .addValue("lower", range.lowerEndpoint())
                                    .addValue("upper", range.upperEndpoint()),
                            binRangeRowMapper
                    );
        } catch (NestedRuntimeException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public long save(BinRange binRange) throws DaoException {
        try {
            String namedSql = "INSERT INTO binbase.bin_range (range, version_id, bin_data_id) VALUES (int8range(:lower, :upper, '[)'), :version_id, :bin_data_id) RETURNING id";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = getNamedParameterJdbcTemplate()
                    .update(
                            namedSql,
                            new MapSqlParameterSource()
                                    .addValue("lower", binRange.getRange().lowerEndpoint())
                                    .addValue("upper", binRange.getRange().upperEndpoint())
                                    .addValue("version_id", binRange.getVersionId())
                                    .addValue("bin_data_id", binRange.getBinDataId()),
                            keyHolder
                    );
            if (rowsAffected != 1) {
                throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(namedSql, 1, rowsAffected);
            }
            return keyHolder.getKey().intValue();
        } catch (NestedRuntimeException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void save(List<BinRange> binRanges) throws DaoException {
        try {
            String namedSql = "INSERT INTO binbase.bin_range (range, version_id, bin_data_id) VALUES (int8range(?, ?, '[)'), ?, ?)";
            int[] updateCounts = getJdbcTemplate().batchUpdate(
                    namedSql,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            BinRange binRange = binRanges.get(i);
                            ps.setLong(1, binRange.getRange().lowerEndpoint());
                            ps.setLong(2, binRange.getRange().upperEndpoint());
                            ps.setLong(3, binRange.getVersionId());
                            ps.setLong(4, binRange.getBinDataId());
                        }

                        @Override
                        public int getBatchSize() {
                            return binRanges.size();
                        }
                    });

            for (int rowsAffected : updateCounts) {
                if (rowsAffected != 1) {
                    throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(namedSql, 1, rowsAffected);
                }
            }
        } catch (NestedRuntimeException ex) {
            throw new DaoException(ex);
        }
    }
}

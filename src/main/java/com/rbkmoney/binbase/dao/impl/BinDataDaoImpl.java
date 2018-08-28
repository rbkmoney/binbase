package com.rbkmoney.binbase.dao.impl;

import com.rbkmoney.binbase.dao.BinDataDao;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.CardType;
import com.rbkmoney.binbase.domain.CountryCode;
import com.rbkmoney.binbase.exception.DaoException;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

@Component
public class BinDataDaoImpl extends NamedParameterJdbcDaoSupport implements BinDataDao {

    private final RowMapper<BinData> binDataRowMapper = (rs, i) -> new BinData(
            rs.getString("payment_system"),
            StringUtils.hasText(rs.getString("bank_name")) ? rs.getString("bank_name") : null,
            CountryCode.getByNumericCode(rs.getString("iso_country_code")),
            StringUtils.hasText(rs.getString("card_type")) ? TypeUtil.toEnumField(rs.getString("card_type"), CardType.class) : null
    );

    @Autowired
    public BinDataDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public Map.Entry<Long, BinData> getBinDataByCardPan(long pan) throws DaoException {
        try {
            String namedSql = "SELECT payment_system, bank_name, iso_country_code, card_type, version_id FROM binbase.bin_range JOIN binbase.bin_data ON (range @> :pan AND bin_range.bin_data_id = bin_data.id) ORDER BY version_id DESC LIMIT 1";
            return getNamedParameterJdbcTemplate()
                    .queryForObject(
                            namedSql,
                            new MapSqlParameterSource("pan", pan),
                            (rs, i) -> new AbstractMap.SimpleEntry<>(rs.getLong("version_id"), binDataRowMapper.mapRow(rs, i))
                    );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (NestedRuntimeException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Map.Entry<Long, BinData> getBinDataByCardPanAndVersion(long pan, long versionId) throws DaoException {
        try {
            String namedSql = "SELECT payment_system, bank_name, iso_country_code, card_type, version_id FROM binbase.bin_range JOIN binbase.bin_data ON (range @> :pan AND version_id = :version_id AND bin_range.bin_data_id = bin_data.id)";
            return getNamedParameterJdbcTemplate()
                    .queryForObject(
                            namedSql,
                            new MapSqlParameterSource()
                                    .addValue("pan", pan)
                                    .addValue("version_id", versionId),
                            (rs, i) -> new AbstractMap.SimpleEntry<>(rs.getLong("version_id"), binDataRowMapper.mapRow(rs, i))
                    );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (NestedRuntimeException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public BinData get(long id) throws DaoException {
        try {
            String namedSql = "SELECT payment_system, bank_name, iso_country_code, card_type FROM binbase.bin_data WHERE id = :id";
            return getNamedParameterJdbcTemplate()
                    .queryForObject(
                            namedSql,
                            new MapSqlParameterSource()
                                    .addValue("id", id),
                            binDataRowMapper
                    );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (NestedRuntimeException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public long save(BinData binData) throws DaoException {
        try {
            String namedSql = "INSERT INTO binbase.bin_data (payment_system, bank_name, card_type, iso_country_code) VALUES (coalesce(:payment_system, ''), coalesce(:bank_name, ''), coalesce(:card_type, ''), coalesce(:iso_country_code, '')) ON CONFLICT (payment_system, bank_name, card_type, iso_country_code) DO UPDATE SET (payment_system, bank_name, card_type, iso_country_code) = (:payment_system, coalesce(:bank_name, ''), coalesce(:card_type, ''), coalesce(:iso_country_code, '')) RETURNING id";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = getNamedParameterJdbcTemplate()
                    .update(
                            namedSql,
                            new MapSqlParameterSource()
                                    .addValue("payment_system", binData.getPaymentSystem())
                                    .addValue("bank_name", binData.getBankName())
                                    .addValue(
                                            "card_type",
                                            Optional.ofNullable(binData.getCardType())
                                                    .map(cardType -> cardType.toString())
                                                    .orElse(null)
                                    )
                                    .addValue("iso_country_code",
                                            Optional.ofNullable(binData.getIsoCountryCode())
                                                    .map(countryCode -> countryCode.getNumeric())
                                                    .orElse(null)),
                            keyHolder
                    );
            if (rowsAffected != 1) {
                throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(namedSql, 1, rowsAffected);
            }
            return keyHolder.getKey().longValue();
        } catch (NestedRuntimeException ex) {
            throw new DaoException(ex);
        }
    }
}

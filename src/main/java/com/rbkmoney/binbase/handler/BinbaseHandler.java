package com.rbkmoney.binbase.handler;

import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.domain.CountryCode;
import com.rbkmoney.binbase.exception.BinNotFoundException;
import com.rbkmoney.binbase.service.BinbaseService;
import com.rbkmoney.damsel.binbase.*;
import com.rbkmoney.damsel.binbase.msgpack.Value;
import com.rbkmoney.geck.common.util.TypeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static com.rbkmoney.binbase.util.PanUtil.formatPan;

@Component
@Slf4j
@RequiredArgsConstructor
public class BinbaseHandler implements BinbaseSrv.Iface {

    private final BinbaseService binbaseService;

    @Override
    public ResponseData lookup(String pan, Reference reference) throws BinNotFound, TException {
        log.info("Lookup bin data, pan='{}', reference='{}'", formatPan(pan), reference);
        try {
            Map.Entry<Long, BinData> binDataWithVersion = reference.isSetLast()
                    ? binbaseService.getBinDataByCardPan(pan)
                    : binbaseService.getBinDataByCardPanAndVersion(pan, reference.getVersion());

            ResponseData responseData = toResponseData(binDataWithVersion);
            log.info("Lookup result: {}, pan='{}', reference='{}'", responseData, formatPan(pan), reference);
            return responseData;
        } catch (BinNotFoundException | IllegalArgumentException ex) {
            log.info("Cannot lookup bin data, pan='{}', reference='{}'", formatPan(pan), reference, ex);
            throw new BinNotFound();
        }
    }

    @Override
    public ResponseData getByBinDataId(Value binDataId) throws BinNotFound, TException{
        log.info("Get bin data, binDataId='{}'", binDataId);
        try {
            Map.Entry<Long, BinData> binDataWithVersion = binbaseService.getBinDataByBinDataId(binDataId.getI());
            ResponseData responseData = toResponseData(binDataWithVersion);
            log.info("Result: {}, binDataId='{}'", responseData, binDataId);
            return responseData;
        } catch (BinNotFoundException | IllegalArgumentException ex) {
            log.info("Cannot get bin data, binDataId='{}'", binDataId, ex);
            throw new BinNotFound();
        }
    }

    @Override
    public ResponseData getByCardToken(String cardToken) throws BinNotFound, TException{
        log.info("Get bin data, cardToken='{}'", cardToken);
        try {
            Map.Entry<Long, BinData> binDataWithVersion = binbaseService.getBinDataByCardToken(cardToken);
            ResponseData responseData = toResponseData(binDataWithVersion);
            log.info("Result: {}, cardToken='{}'", responseData, cardToken);
            return responseData;
        } catch (BinNotFoundException | IllegalArgumentException ex) {
            log.info("Cannot get bin data, cardToken='{}'", cardToken, ex);
            throw new BinNotFound();
        }
    }

    private ResponseData toResponseData(Map.Entry<Long, BinData> binDataWithVersion) {
        long versionId = binDataWithVersion.getKey();
        BinData binData = binDataWithVersion.getValue();

        com.rbkmoney.damsel.binbase.BinData damselBinData = new com.rbkmoney.damsel.binbase.BinData();
        damselBinData.setBinDataId(Value.i(binData.getId()));
        damselBinData.setPaymentSystem(binData.getPaymentSystem());
        damselBinData.setBankName(binData.getBankName());
        damselBinData.setCardType(
                TypeUtil.toEnumField(
                        Optional.ofNullable(binData.getCardType())
                                .map(Enum::toString)
                                .orElse(null),
                        CardType.class));
        damselBinData.setIsoCountryCode(
                Optional.ofNullable(binData.getIsoCountryCode())
                        .map(CountryCode::getAlpha3)
                        .orElse(null));
        damselBinData.setCategory(binData.getCategory());

        return new ResponseData(damselBinData, versionId);
    }

}

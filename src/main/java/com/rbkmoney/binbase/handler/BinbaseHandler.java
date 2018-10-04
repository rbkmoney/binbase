package com.rbkmoney.binbase.handler;

import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.exception.BinNotFoundException;
import com.rbkmoney.binbase.service.BinbaseService;
import com.rbkmoney.damsel.binbase.*;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static com.rbkmoney.binbase.util.PanUtil.formatPan;

@Component
public class BinbaseHandler implements BinbaseSrv.Iface {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BinbaseService binbaseService;

    @Autowired
    public BinbaseHandler(BinbaseService binbaseService) {
        this.binbaseService = binbaseService;
    }

    @Override
    public ResponseData lookup(String pan, Reference reference) throws BinNotFound, TException {
        log.info("Lookup bin data, pan='{}', reference='{}'", formatPan(pan), reference);
        try {
            Map.Entry<Long, BinData> binDataWithVersion;
            if (reference.isSetLast()) {
                binDataWithVersion = binbaseService.getBinDataByCardPan(pan);
            } else {
                binDataWithVersion = binbaseService.getBinDataByCardPanAndVersion(pan, reference.getVersion());
            }
            ResponseData responseData = toResponseData(binDataWithVersion);
            log.info("Lookup result: {}, pan='{}', reference='{}'", responseData, formatPan(pan), reference);
            return responseData;
        } catch (BinNotFoundException | IllegalArgumentException ex) {
            log.info("Cannot lookup bin data, pan='{}', reference='{}'", formatPan(pan), reference, ex);
            throw new BinNotFound();
        }
    }

    private ResponseData toResponseData(Map.Entry<Long, BinData> binDataWithVersion) {
        long versionId = binDataWithVersion.getKey();
        BinData binData = binDataWithVersion.getValue();

        com.rbkmoney.damsel.binbase.BinData damselBinData = new com.rbkmoney.damsel.binbase.BinData();
        damselBinData.setPaymentSystem(binData.getPaymentSystem());
        damselBinData.setBankName(binData.getBankName());
        damselBinData.setCardType(
                TypeUtil.toEnumField(
                        Optional.ofNullable(binData.getCardType())
                                .map(cardType -> cardType.toString())
                                .orElse(null),
                        CardType.class
                )
        );
        damselBinData.setIsoCountryCode(
                Optional.ofNullable(binData.getIsoCountryCode())
                        .map(countryCode -> countryCode.getAlpha3())
                        .orElse(null)
        );
        return new ResponseData(damselBinData, versionId);
    }

}

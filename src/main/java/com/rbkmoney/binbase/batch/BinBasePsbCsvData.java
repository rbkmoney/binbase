package com.rbkmoney.binbase.batch;

import com.rbkmoney.binbase.util.BinBaseDataType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BinBasePsbCsvData extends BinBaseData {

    private String country;
    private String paymentSystem;
    private String bank;
    private String type;
    private String startBin;
    private String endBin;

    @Override
    public BinBaseDataType getDataType() {
        return BinBaseDataType.CSV;
    }
}

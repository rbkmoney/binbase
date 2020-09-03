package com.rbkmoney.binbase.batch;

import com.rbkmoney.binbase.util.BinBaseDataType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BinBaseCsvData extends BinBaseData {

    private String binLength;
    private String affiliation;
    private String mark;

    @Override
    public BinBaseDataType getDataType() {
        return BinBaseDataType.CSV;
    }
}

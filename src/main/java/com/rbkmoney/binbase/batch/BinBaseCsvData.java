package com.rbkmoney.binbase.batch;

import com.rbkmoney.binbase.util.BinBaseDataType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BinBaseCsvData extends BinBaseData {

    private String bin;
    private String brand;
    private String bank;
    private String type;
    private String category;
    private String isoname;
    private String isoa2;
    private String isoa3;
    private String isonumber;
    private String url;
    private String phone;
    private String binLength;
    private String affiliation;
    private String mark;

    @Override
    public BinBaseDataType getDataType() {
        return BinBaseDataType.CSV;
    }
}

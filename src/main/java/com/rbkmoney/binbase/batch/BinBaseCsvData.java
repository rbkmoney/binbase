package com.rbkmoney.binbase.batch;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BinBaseCsvData {

    private String country;
    private String paymentSystem;
    private String bank;
    private String type;
    private String startBin;
    private String endBin;

}

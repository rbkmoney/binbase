package com.rbkmoney.binbase.batch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "record")
public class BinBaseXmlData {

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
}

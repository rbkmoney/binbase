package com.rbkmoney.binbase.batch;

import com.rbkmoney.binbase.AbstractIntegrationTest;
import com.rbkmoney.binbase.domain.CountryCode;
import com.rbkmoney.damsel.binbase.*;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;


@TestPropertySource(properties = "batch.file_path=classpath:/data/binbase/case1")
public class BatchUploadTest extends AbstractIntegrationTest {

    private BinbaseSrv.Iface binbaseClient;

    @Before
    public void setup() throws URISyntaxException {
        binbaseClient = new THSpawnClientBuilder()
                .withAddress(new URI("http://localhost:" + port + "/v1/binbase"))
                .withNetworkTimeout(0)
                .build(BinbaseSrv.Iface.class);
    }

    @Test
    public void testLookupData() throws TException {
        ResponseData responseData = binbaseClient.lookup("100001", Reference.last(new Last()));
        assertEquals(3L, responseData.getVersion());
        assertEquals(CountryCode.US, CountryCode.getByNumericCode(responseData.getBinData().getIsoCountryCode()));
        assertEquals(CardType.credit, responseData.getBinData().getCardType());
        responseData = binbaseClient.lookup("100001", Reference.version(2L));
        assertEquals(2L, responseData.getVersion());
        assertEquals(CountryCode.US, CountryCode.getByNumericCode(responseData.getBinData().getIsoCountryCode()));
        assertEquals(CardType.credit_or_debit, responseData.getBinData().getCardType());
        responseData = binbaseClient.lookup("100001", Reference.version(1L));
        assertEquals(1L, responseData.getVersion());
        assertEquals(CountryCode.CA, CountryCode.getByNumericCode(responseData.getBinData().getIsoCountryCode()));
        assertNull(responseData.getBinData().getCardType());

        responseData = binbaseClient.lookup("100115", Reference.last(new Last()));
        assertEquals(1L, responseData.getVersion());
        assertNull(responseData.getBinData().getIsoCountryCode());

        responseData = binbaseClient.lookup("100117", Reference.last(new Last()));
        assertEquals(1L, responseData.getVersion());
    }

    @Test(expected = BinNotFound.class)
    public void testBinNotFound() throws TException {
        binbaseClient.lookup("999000", Reference.last(new Last()));
    }

    @Test
    public void testWhenWhenPanInWrongFormat() throws TException {
        try {
            binbaseClient.lookup("999999#Q", Reference.last(new Last()));
            fail();
        } catch (BinNotFound ex) {

        }
        try {
            binbaseClient.lookup("99999", Reference.last(new Last()));
            fail();
        } catch (BinNotFound ex) {

        }
        try {
            binbaseClient.lookup("99999999999999999999999999999", Reference.last(new Last()));
            fail();
        } catch (BinNotFound ex) {

        }
    }

    @Test
    public void testMaxValuePan() throws TException {
        ResponseData responseData = binbaseClient.lookup("9999999999999999999", Reference.last(new Last()));
        assertNotNull(responseData);
    }

}

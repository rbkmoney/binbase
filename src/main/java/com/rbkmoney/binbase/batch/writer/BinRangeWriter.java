package com.rbkmoney.binbase.batch.writer;

import com.google.common.collect.Range;
import com.rbkmoney.binbase.domain.BinData;
import com.rbkmoney.binbase.service.BinbaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BinRangeWriter implements ItemWriter<Map.Entry<BinData, Range<Long>>> {

    private final BinbaseService binbaseService;

    @Override
    public void write(List<? extends Map.Entry<BinData, Range<Long>>> binDataRanges) throws Exception {
        binDataRanges.forEach(binDataRange -> binbaseService.saveRange(binDataRange.getKey(), binDataRange.getValue()));
    }

}

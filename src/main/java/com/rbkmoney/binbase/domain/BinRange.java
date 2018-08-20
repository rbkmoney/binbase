package com.rbkmoney.binbase.domain;

import com.google.common.collect.Range;

import java.util.Objects;

public class BinRange {

    private Range<Long> range;

    private Long versionId;

    private Long binDataId;

    public BinRange() {
    }

    public BinRange(Long lowerEndpoint, Long upperEndpoint, Long versionId, Long binDataId) {
        this(Range.openClosed(lowerEndpoint, upperEndpoint), versionId, binDataId);
    }

    public BinRange(Range<Long> range, Long versionId, Long binDataId) {
        this.range = range;
        this.versionId = versionId;
        this.binDataId = binDataId;
    }

    public Range<Long> getRange() {
        return range;
    }

    public void setRange(Range<Long> range) {
        this.range = range;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getBinDataId() {
        return binDataId;
    }

    public void setBinDataId(Long binDataId) {
        this.binDataId = binDataId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinRange binRange = (BinRange) o;
        return Objects.equals(range, binRange.range) &&
                Objects.equals(versionId, binRange.versionId) &&
                Objects.equals(binDataId, binRange.binDataId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(range, versionId, binDataId);
    }

    @Override
    public String toString() {
        return "BinRange{" +
                "range=" + range +
                ", versionId=" + versionId +
                ", binDataId=" + binDataId +
                '}';
    }
}

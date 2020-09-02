package com.rbkmoney.binbase.util;

import com.rbkmoney.binbase.domain.CardType;

public class CardTypeConverter {

    public static CardType convertToCardType(String type) {
        switch (type) {
            case "DEBIT":
                return CardType.debit;
            case "CHARGE CARD":
                return CardType.charge_card;
            case "CREDIT":
                return CardType.credit;
            case "DEBIT OR CREDIT":
                return CardType.credit_or_debit;
            default:
                return null;
        }
    }
}

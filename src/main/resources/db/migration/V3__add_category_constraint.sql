ALTER TABLE binbase.bin_data
    DROP CONSTRAINT bin_data_ukey,
    ADD CONSTRAINT bin_data_ukey UNIQUE (payment_system, bank_name, iso_country_code, card_type, category);

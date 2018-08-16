CREATE EXTENSION IF NOT EXISTS btree_gist;
CREATE SCHEMA IF NOT EXISTS binbase;

CREATE TABLE binbase.bin_data (
  id               SERIAL            NOT NULL,
  payment_system   CHARACTER VARYING NOT NULL,
  bank_name        CHARACTER VARYING NOT NULL,
  iso_country_code CHARACTER VARYING NOT NULL,
  card_type        CHARACTER VARYING NOT NULL,
  CONSTRAINT bin_data_pkey PRIMARY KEY (id),
  CONSTRAINT bin_data_ukey UNIQUE (payment_system, bank_name, iso_country_code, card_type)
);

CREATE TABLE binbase.bin_range (
  id          BIGSERIAL NOT NULL,
  range       int8range NOT NULL,
  version_id  INT       NOT NULL,
  bin_data_id INT       NOT NULL REFERENCES binbase.bin_data,
  CONSTRAINT bin_range_pkey PRIMARY KEY (id),
  EXCLUDE USING gist (range WITH &&, version_id WITH =)
);

CREATE INDEX bin_range_idx
  ON binbase.bin_range
  USING gist (range);
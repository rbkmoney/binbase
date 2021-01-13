CREATE AGGREGATE range_merge(anyrange)
    (
    sfunc = range_merge,
    stype = anyrange
    );

CREATE OR REPLACE FUNCTION merge_adjacent()
RETURNS TRIGGER AS $$

  BEGIN
    WITH matching AS (
      DELETE FROM binbase.bin_range
          WHERE bin_range.bin_data_id = NEW.bin_data_id
              AND bin_range.version_id = NEW.version_id
              AND (bin_range.range -|- NEW.range OR bin_range.range && NEW.range)
        RETURNING range
    )
    SELECT INTO NEW.range (
      SELECT range_merge(range) FROM (
        SELECT NEW.range
         UNION ALL
        SELECT range FROM matching
        ORDER BY range
    ) _all
    );
    RETURN NEW;
  END;

  $$ LANGUAGE plpgsql STRICT;

CREATE TRIGGER merge_adjacent
    BEFORE INSERT OR UPDATE ON binbase.bin_range
    FOR EACH ROW EXECUTE PROCEDURE merge_adjacent();
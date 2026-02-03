package tr.mbt.couponscheduler.config.job;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;

import static tr.mbt.coupon.coupondata.constants.UploadedFileConstants.*;

public class CouponCsvReader {

    public static FlatFileItemReader<CouponCsvRow> reader(Resource resource) {
        FlatFileItemReader<CouponCsvRow> reader = new FlatFileItemReader<>();
        reader.setResource(resource);
        reader.setLinesToSkip(1); // header

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(EXPECTED_HEADERS);
        tokenizer.setStrict(true);

        DefaultLineMapper<CouponCsvRow> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSet -> {
            CouponCsvRow row = new CouponCsvRow();
            row.setCode(fieldSet.readString(CODE));
            row.setType(fieldSet.readString(TYPE));
            row.setDiscountType(fieldSet.readString(DISCOUNT_TYPE));
            row.setDiscountAmount(fieldSet.readString(DISCOUNT_AMOUNT));
            row.setExpiryDate(fieldSet.readString(EXPIRY_DATE));
            row.setMaxUsages(fieldSet.readString(MAX_USAGE));
            return row;
        });

        reader.setLineMapper(lineMapper);
        return reader;
    }
}

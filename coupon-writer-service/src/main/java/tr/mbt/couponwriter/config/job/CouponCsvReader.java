package tr.mbt.couponwriter.config.job;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;
import tr.mbt.couponwriter.data.CouponType;

import java.time.LocalDate;

public class CouponCsvReader {

    public static FlatFileItemReader<CouponCsvRow> reader(Resource resource) {
        FlatFileItemReader<CouponCsvRow> reader = new FlatFileItemReader<>();
        reader.setResource(resource);
        reader.setLinesToSkip(1); // header

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(
                "code",
                "type",
                "discountType",
                "discountAmount",
                "expiryDate",
                "maxUsages"
        );

        DefaultLineMapper<CouponCsvRow> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSet -> {
            CouponCsvRow row = new CouponCsvRow();
            row.setCode(fieldSet.readString("code"));
            row.setType(fieldSet.readString("type"));
            row.setDiscountType(fieldSet.readString("discountType"));
            row.setDiscountAmount(fieldSet.readBigDecimal("discountAmount"));
            row.setExpiryDate(LocalDate.parse(fieldSet.readString("expiryDate")));
            row.setMaxUsages(fieldSet.readInt("maxUsages"));
            return row;
        });

        reader.setLineMapper(lineMapper);
        return reader;
    }
}

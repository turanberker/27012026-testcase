package tr.mbt.coupon.coupondata.constants;

public class UploadedFileConstants {
    private UploadedFileConstants() {
        super();
    }


    public static final String CODE = "code";
    public static final String TYPE = "type";
    public static final String DISCOUNT_TYPE = "discountType";
    public static final String DISCOUNT_AMOUNT = "discountAmount";
    public static final String EXPIRY_DATE = "expiryDate";
    public static final String MAX_USAGE = "maxUsages";
    public static final String[] EXPECTED_HEADERS = {CODE, TYPE, DISCOUNT_TYPE, DISCOUNT_AMOUNT, EXPIRY_DATE, MAX_USAGE
    };


}

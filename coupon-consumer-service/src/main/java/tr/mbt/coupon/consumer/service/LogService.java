package tr.mbt.coupon.consumer.service;

import tr.mtb.coupon.logdata.MethodLogEvent;

public interface LogService {

    void saveLog(MethodLogEvent logEvent);
}

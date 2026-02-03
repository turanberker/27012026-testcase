package tr.mbt.coupon.commandservice.util;

import tr.mbt.coupon.commandservice.dto.HasUserId;

public interface UserGetter {

    String getUserId(HasUserId hasUserId);
}

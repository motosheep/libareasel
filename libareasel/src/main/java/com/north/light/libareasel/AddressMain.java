package com.north.light.libareasel;

import java.io.Serializable;

/**
 * author:li
 * date:2020/6/21
 * desc:
 */
public class AddressMain implements Serializable {
    private static final class SingleHolder {
        private static final AddressMain mInstance = new AddressMain();
    }

    public static AddressMain getInstance() {
        return SingleHolder.mInstance;
    }


}

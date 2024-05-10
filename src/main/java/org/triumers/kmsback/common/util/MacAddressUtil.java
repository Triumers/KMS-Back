package org.triumers.kmsback.common.util;

import java.net.NetworkInterface;
import java.util.Enumeration;

public class MacAddressUtil {

    public static String getMacAddress() throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = networkInterfaces.nextElement();
            byte[] hardwareAddress = ni.getHardwareAddress();
            if (hardwareAddress != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < hardwareAddress.length; i++) {
                    sb.append(String.format("%02X%s", hardwareAddress[i], (i < hardwareAddress.length - 1) ? "-" : ""));
                }
                return sb.toString();
            }
        }
        return null;
    }

}

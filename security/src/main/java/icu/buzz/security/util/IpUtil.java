package icu.buzz.security.util;

import icu.buzz.security.constant.CommonConstant;
import icu.buzz.security.constant.HttpHeaderConstant;
import icu.buzz.security.exception.ServerInternalException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@Component
public class IpUtil {
    private HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
       this.request = request;
    }

    public String getIpAddr() {
        String ipAddr = null;
        boolean flag = false;
        for (String header : HttpHeaderConstant.IP_HEADERS) {
            ipAddr = request.getHeader(header);
            if (ipAddr != null && !ipAddr.isEmpty() && !ipAddr.equals(HttpHeaderConstant.UNKNOWN)) {
                flag = true;
                break;
            }
        }

        if (flag) {
            ipAddr = ipAddr.split(",")[0];
        } else {
            ipAddr = request.getRemoteAddr();
            if (ipAddr.equals(CommonConstant.LOCAL_IP)) {
                try {
                    ipAddr = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    throw new ServerInternalException(Map.of("internal error", "cannot get ip"));
                }
            }
        }

        return ipAddr;
    }
}
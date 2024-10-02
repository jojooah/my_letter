package com.jojo.my_letter.useragent;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.junit.jupiter.api.Test;

import static com.jojo.my_letter.utils.Utils.toJson;

public class UserAgentDeviceTests {

    UserAgentAnalyzer uaa = UserAgentAnalyzer
        .newBuilder()
        .withFields("DeviceClass", "DeviceName", "OperatingSystemName", "OperatingSystemVersion")
//            .withAllFields()
        .withCache(25000)
        .build();

    @Test
    public void testGetDeviceInfoFromUserAgent() throws Exception {

//        String userAgent = "Mozilla/5.0 (Linux; Android 13; SM-G991B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Mobile Safari/537.36";
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36";
        UserAgent agent = uaa.parse(userAgent);
        System.out.println("info: " + toJson(agent));
        System.out.println("DeviceClass: " + agent.getValue("DeviceClass"));
        System.out.println("DeviceName: " + agent.getValue("DeviceName"));
        System.out.println("OperatingSystemName: " + agent.getValue("OperatingSystemName"));
        System.out.println("OperatingSystemVersion: " + agent.getValue("OperatingSystemVersion"));
    }

}

package com.yc.damai.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.sun.org.apache.regexp.internal.RE;

public final class AlipayUtil {
    private final static String APP_ID = "2016102900777362";
    private final static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpXIbf49LkHb5o84QuoEFpvKi0q30c5y/UDOTypgkPKqzajBsJOla+1Ac9cdlisFcE7pEHOAVMH4GdeLX09295+05nR3jZuSZapy11WfoIFa+azm8cL8VN5OMJvy8RBWQL6VF550Rr+d0oJipxwgAjfrCct1ClgPMt6Ld1L3LXwj4yIH0wTeLPH5UYnM4/CgBFBM8o4ZbKyDRqg371Tl7eDBClqtALrOC4RL1J0BYrgngc2VtElvI2xSBGbs5Wj+P5KThwXbabYI1A49OfwZM208661uztthd+w7kNUWSVpnrztHJlGZ+KWiN7CasBrEoInrvR4oGKNeY1qgZmzO/fAgMBAAECggEANrUu1E/RpeJ9yYUwI6ecub05efMTXSzI5GSt6Dv+HSoZ4Bb1/MsdjlMLa6vFHv37fhpaOsy72VUsF2QQ0urC1dJ6Bx6tSRdn7kymgIvqGoZ2zTj151L/FyRiuplJaOQm5zodGZW9Imv6Jqf9efXC+bRI9La6lFvNMf1EldQX8t3B5QnbYrHdqv5Ds7gUbOn/DV4SxNy8N4i4Aw3RRH0jOijMygbY+pgiZNWRbCgkqh5Dj1XAaAlr/9tH2I158GluHY+P2YCDF9lkRqUIsKFoSSp6XJUWCKtfKMwV7UPfd/qK2l52Lapd99LAcRjhWNLmknmPssweZp6gBthALANs0QKBgQD0B4zZeVpxWj/IvLJDnBHLO/KNBOwigxwoqPD4ujxmVRomX9jivRG7QZqNZ2rK9u3zjB9kh48tusYT6o5F1Xxu5cCimv8IUWS/UEosjiG13pRS4cRNFK+rOPfPumruCv2KTEne4R4Ckhkx8r5lcx4iovohzVQLwdKol6B6AqSdFQKBgQCxq1E/AD22IcyJ2ZvwITYGXaIcLsBaz0qJbM4vbS1Nsw0QSCVtG7T9A6P+FyYWhuUt4/rndLgBIBPBHvBLxs85fupeug93mhpr3llHnTImTe+U6kZr7NcFpyalOetdH/Rvo6bWzBJ/K9srSdT6B7hQ4dUK3IhBEwjs6veK1xceIwKBgGq4ZpOxK6mI834hb8B7n5iG5gNgFLjfJ9BsWb4dDE8ghqgjoKFAWGHRRfrzMFQE+3lYXV5Enow8M2YAXSoTPDDvj3hNWp1To0C7p0Sm//0ekzVQAN5I3q594npZJ2xlKA50YJX1U3+CTzx10WhimNpC9isUNd5SbINIlGjngQAlAoGAaf0BYswKBHT1R+cBF6c4S2PgKV/acFHo7UJzXeeLdyaxTF09tnEo7ycU1aeJmTav0UiHjTwb6M/x+Osz/oCDbpE/z3K/d2H/EQp7yDUGjcNAgV2g1RFc1Ip6i2aZ+dvuPgsqpEjpX+QRwykwXFfupJGUPf6BahCo6mzhb3oQ2HECgYEA0nxqNIJtvYkvMdQQHH2+PZKKC7JBqbABAzJl00ZAFsT5k+DIIzrB5/NFqnNstInRfG1nHAHdCxcJJf53fupEy0/3h2Noar1QzdxDTx5806dTN8U+N6Mt9/B+7H4k8KwohH5d0dOMtc78opvRCiarU4rbShuXkKWY8TpDC18EDgo=";
    private final static String CHARSET = "utf8";
    private final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm9ba5GvhNpG1Mgn2jlmMF+HRomQtdAq+tBgGAlrZzmHG+Um5/+Bhpyl51GYlMOhH9BpLmJgnaabFFY5li07tRtfr1eEJvxLSpks59ig1+Q1XyS8bJTdwwDjogZHLfDdeyIlVmB3YbbeycG3ignEBzR6OL+lkK1/wVmChQQ+jsFbIMLt8D1o38v/DuQaXORa0Ih+VhHo8q6Hu1eUwY5SZSD7JGVQEFD4uUIX3/B20ncQiv0idTLLy191q6Vq9XSDPLFWGoTTz368NY21uB+FJ6oXEkRJWAeZa2HnI3fmt/DmuKetrzPhMLjmLzP0CTj/hkeQ1YGRac6vORi+FmZZECwIDAQAB";
    private final static String SIGN_TYPE = "RSA2";
    private final static String RETURN_URL = "http://127.0.0.1:8080/c85-s3-Pllz-damai/olist.html";
    private static final AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);

    public static String createWebPay(String outTradeNo, String amount, String sellerid) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();


        model.setSubject("web支付测试Java");
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(amount);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);

//        request.setNotifyUrl(RETURN_URL);
        request.setReturnUrl(RETURN_URL);

        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradePagePayResponse response = AlipayUtil.alipayClient.pageExecute(request);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}

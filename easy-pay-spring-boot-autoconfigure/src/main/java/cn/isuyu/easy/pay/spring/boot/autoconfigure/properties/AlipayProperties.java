package cn.isuyu.easy.pay.spring.boot.autoconfigure.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/11 8:36 上午
 */
@Data
@ConfigurationProperties(prefix = AlipayProperties.ALIPAY_PROPERTIES_PREFIX)
@ToString
public class AlipayProperties {

    public static final String ALIPAY_PROPERTIES_PREFIX = "easy.pay.alipay";

    private String appId;

    private String privateKey;

    private String publicKey;

    private String notifyUrl;

    private String returnUrl;

    private String signType = "RSA2";

    private String charset = "utf-8";

    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    private String logPath = "/tmp/";

    @PostConstruct
    public void chkParams() {
        System.out.println("支付宝参数校验");
    }

}

package cn.isuyu.easy.pay.spring.boot.autoconfigure.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

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

    /** 应用ID **/
    private String appId;

    /** 商户私钥 **/
    private String privateKey;

    /** 支付宝公钥 **/
    private String publicKey;

    /** 异步回调地址 **/
    private String notifyUrl;

    /** 同步回调页面 **/
    private String returnUrl;

    private String signType = "RSA2";

    private String charset = "utf-8";

    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    private String logPath = "/tmp/";

//    @PostConstruct
//    public void chkParams() {
//        ChkParamsUtils.requireNonNull(this,"appId,privateKey,publicKey,returnUrl","alipay");
//    }

}

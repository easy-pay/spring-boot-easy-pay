package cn.isuyu.easy.pay.spring.boot.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/11 8:37 上午
 */
@Data
@ConfigurationProperties(prefix = WxPayProperties.WECHAT_PROPERTIES_PREFIX)
public class WxPayProperties {

    public static final String WECHAT_PROPERTIES_PREFIX = "easy.pay.wxpay";

    /** 公众号appid **/
    private String appId;

    /** 商户id **/
    private String mchId;

    /** 支付api安全密钥 **/
    private String mchKey;

    private String tradeType = "NATIVE";

    /** 支付结果回调地址 **/
    private String payNotify;

    /** 退款回调地址 **/
    private String refundNotify;

    /** 退款证书名称 **/
    private String certName;

//    @PostConstruct
//    public void chkParams() {
//        ChkParamsUtils.requireNonNull(this,"appId,mchId,mchKey,payNotify","wxpay");
//    }
}

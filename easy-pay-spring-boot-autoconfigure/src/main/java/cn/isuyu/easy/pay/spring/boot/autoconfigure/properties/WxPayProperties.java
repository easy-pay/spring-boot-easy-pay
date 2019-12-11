package cn.isuyu.easy.pay.spring.boot.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

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

    private String appId;

    private String mchId;

    private String mchKey;

    private String tradeType = "NATIVE";

    private String payNotify;

    private String refundNotify;

    private String certName;


    @PostConstruct
    public void chkParams() {
        System.out.println("微信参数校验");
    }


}

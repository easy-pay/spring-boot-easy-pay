package cn.isuyu.easy.pay.spring.boot.autoconfigure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-08-26 11:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AliPayH5PayDTO implements Serializable {
    /**
     * 交易的具体描述信息
     */
    private String subject;

    /**
     * 商户网站唯一订单号
     */
    private String outTradeNo;

    /**
     * 订单总金额，单位为元
     */
    private String totalAmount;

    /**
     * 用户付款中途退出返回商户网站的地址
     */
    private String quitUrl;

    /**
     * 销售产品码，商家和支付宝签约的产品码
     */
    private String productCode = "QUICK_WAP_WAY";
}

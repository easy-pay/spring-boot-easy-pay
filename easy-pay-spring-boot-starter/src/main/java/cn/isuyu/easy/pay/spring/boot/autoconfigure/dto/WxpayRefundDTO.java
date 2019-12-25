package cn.isuyu.easy.pay.spring.boot.autoconfigure.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午2:38
 * 微信
 */
@XStreamAlias("xml")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WxpayRefundDTO extends WxpayBaseDTO {

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("out_refund_no")
    private String outRefundNo;

    @XStreamAlias("total_fee")
    private String totalFee;

    @XStreamAlias("refund_fee")
    private String refundFee;

    @XStreamAlias("notify_url")
    private String refundNotifyUrl;

}

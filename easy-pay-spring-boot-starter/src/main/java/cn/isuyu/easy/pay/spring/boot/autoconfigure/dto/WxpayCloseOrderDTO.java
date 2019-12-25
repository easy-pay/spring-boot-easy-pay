package cn.isuyu.easy.pay.spring.boot.autoconfigure.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午2:14
 */
@Data
@XStreamAlias("xml")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WxpayCloseOrderDTO extends WxpayBaseDTO {

    /**
     * 商户交易订单号
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;
}

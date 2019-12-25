package cn.isuyu.easy.pay.spring.boot.autoconfigure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/27 下午3:55
 * 支付宝PC端下单所需参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AlipayPcPayDTO implements Serializable {

    /**
     * 交易流水号
     */
    private String outTradeNo;

    /**
     * 订单总金额
     */
    private Double totalAmount;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 订单描述
     */
    private String body;

    /**
     * 固定值 FAST_INSTANT_TRADE_PAY
     */
    private String productCode;
}

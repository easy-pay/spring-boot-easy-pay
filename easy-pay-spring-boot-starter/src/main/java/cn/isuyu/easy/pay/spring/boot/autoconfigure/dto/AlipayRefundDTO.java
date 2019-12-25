package cn.isuyu.easy.pay.spring.boot.autoconfigure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/27 上午11:37
 * 退款的参数类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AlipayRefundDTO implements Serializable {

    /**
     * 退款金额
     */
    private Double refundAmount;

    /**
     * 退款订单号
     */
    private String outTradeNo;

    /**
     * 退款理由
     */
    private String refundReason;
}

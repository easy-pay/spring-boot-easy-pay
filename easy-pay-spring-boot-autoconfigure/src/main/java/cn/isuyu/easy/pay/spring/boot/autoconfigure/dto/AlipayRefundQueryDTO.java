package cn.isuyu.easy.pay.spring.boot.autoconfigure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/11 下午2:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AlipayRefundQueryDTO implements Serializable {
    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 退款申请的单号
     */
    private String outRequestNo;
}

package cn.isuyu.easy.pay.spring.boot.autoconfigure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/15 下午2:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AlipayCloseOrderDTO implements Serializable {

    /**
     * 交易流水号，不可重复
     */
    private String outTradeNo;

    /**
     * 卖家端自定义的的操作员 ID
     */
    private String operatorId;
}

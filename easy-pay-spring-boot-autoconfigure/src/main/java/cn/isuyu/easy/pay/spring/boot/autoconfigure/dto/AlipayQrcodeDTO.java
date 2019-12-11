package cn.isuyu.easy.pay.spring.boot.autoconfigure.dto;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/26 下午1:51
 * 生成二维码参数类
 */
@Data
@Accessors(chain = true)
public class AlipayQrcodeDTO {
    /**
     * 订单金额
     */
    private Double totalAmount;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 过期时间
     */
    private String timeoutExpress = "5m";

    /**
     * 交易流水号，不可重复
     */
    private String outTradeNo;
}

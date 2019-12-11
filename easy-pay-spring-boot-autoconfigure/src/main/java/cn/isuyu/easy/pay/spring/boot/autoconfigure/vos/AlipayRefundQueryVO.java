package cn.isuyu.easy.pay.spring.boot.autoconfigure.vos;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/11 下午2:31
 */
@Data
public class AlipayRefundQueryVO implements Serializable {

    /**
     * 状态码
     */
    private String code;

    /**
     * 提醒信息
     */
    private String msg;

    /**
     * 商户交易流水号
     */
    private String out_trade_no;

    /**
     * 订单总金额
     */
    private String total_amount;

    /**
     * 退款金额
     */
    private String refund_amount;

    /**
     * 支付宝交易流水号
     */
    private String trade_no;

    /**
     * 商户退款请求流水号
     */
    private String out_request_no;

    /**
     * 错误码
     */
    private String sub_code;

    /**
     * 错误信息
     */
    private String sub_msg;

}

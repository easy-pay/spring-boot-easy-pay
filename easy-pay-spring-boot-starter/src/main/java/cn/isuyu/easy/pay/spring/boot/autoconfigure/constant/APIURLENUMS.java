package cn.isuyu.easy.pay.spring.boot.autoconfigure.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/11 10:50 上午
 */
@AllArgsConstructor
@Getter
public enum  APIURLENUMS {
    //统一下单
    API_URL_QRCODE("https://api.mch.weixin.qq.com/pay/unifiedorder"),
    //退款接口
    API_URL_REFUND("https://api.mch.weixin.qq.com/secapi/pay/refund"),
    //退款查询
    API_URL_REFUND_QUERY("https://api.mch.weixin.qq.com/pay/refundquery"),
    //关闭订单
    API_URL_CLOSE_ORDER("https://api.mch.weixin.qq.com/pay/closeorder"),
    ;
    private String url;
}

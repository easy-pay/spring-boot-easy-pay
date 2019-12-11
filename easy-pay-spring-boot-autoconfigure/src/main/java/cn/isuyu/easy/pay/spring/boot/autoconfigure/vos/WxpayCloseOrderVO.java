package cn.isuyu.easy.pay.spring.boot.autoconfigure.vos;

import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/30 下午2:16
 */
@XStreamAlias("xml")
public class WxpayCloseOrderVO extends BaseWxPayResult {

}

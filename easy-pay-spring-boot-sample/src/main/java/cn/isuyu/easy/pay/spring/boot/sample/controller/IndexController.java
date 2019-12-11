package cn.isuyu.easy.pay.spring.boot.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/9 上午9:30
 */
@RequestMapping(value = "/")
@Controller
public class IndexController {

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "index")
    public String index() {
        return "index";
    }

    /**
     * 支付宝二维码支付页面
     * @return
     */
    @RequestMapping(value = "aliqrcode")
    public String aliQrcodeUI() {
        return "alipay/aliqrcode";
    }

    /**
     * 支付包退款页面
     * @return
     */
    @RequestMapping(value = "alirefund")
    public String aliRefund() {
        return "alipay/alirefund";
    }

    /**
     * 支付包网页支付
     * @return
     */
    @RequestMapping(value = "alipcpay")
    public String aliPcPay(){
        return "alipay/alipcpay";
    }

    /**
     * 支付宝回调页面
     * @return
     */
    @RequestMapping(value = "success")
    public String aliReturnUrl(){
        return "alipay/success";
    }

    /**
     * 微信二维码支付页面
     * @return
     */
    @RequestMapping(value = "wxqrcode")
    public String wxQrcode() {

        return "wx/wxqrcode";
    }

    /**
     * 微信关闭订单接口
     * @return
     */
    @RequestMapping(value = "wxcloseorder")
    public String closeOrder() {
        return "wx/closeOrder";
    }

    /**
     * 微信退款页面
     * @return
     */
    @RequestMapping(value = "wxrefund")
    public String wxRefund() {
        return "wx/wxrefund";
    }

    /**
     * 退款查询
     * @return
     */
    @RequestMapping(value = "wxrefundquery")
    public String wxRefunQuery() {
        return "wx/refundQuery";
    }


}

package cn.isuyu.easy.pay.spring.boot.sample.controller;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.*;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.service.AlipayService;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.vos.*;
import cn.isuyu.easy.pay.spring.boot.sample.service.WebSocketService;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/9 上午10:57
 * 支付宝支付Controller
 */
@RestController
@RequestMapping(value = "alipay")
@Slf4j
public class AliPayController {

    /**
     * 支付成功返回的状态值
     */
    private static final String SUCCESS_PAY_STATUS = "TRADE_SUCCESS";

    @Autowired
    private AlipayService alipayService;

    /**
     * 生成二维码
     * @param qrcodeDTO
     * @return
     */
    @RequestMapping(value = "qrcode")
    public AlipayQrcodeVO qrcode(AlipayQrcodeDTO qrcodeDTO) throws AlipayApiException {

        return alipayService.qrcode(qrcodeDTO);
    }

    /**
     * 支付宝pc端支付
     */
    @RequestMapping(value = "pcpay")
    public String pcPay(AlipayPcPayDTO pcPayDTO) throws AlipayApiException {
        return alipayService.pcPay(pcPayDTO);
    }

    /**
     * h5支付
     * @param aliPayH5PayDTO
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping(value = "h5pay")
    public String h5pay(AliPayH5PayDTO aliPayH5PayDTO) throws AlipayApiException {

        return alipayService.h5pay(aliPayH5PayDTO);
    }


    /**
     * 订单关闭
     * @param alipayCloseOrderDTO
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping(value = "close")
    public AlipayCloseOrderVO close(AlipayCloseOrderDTO alipayCloseOrderDTO) throws AlipayApiException {

        return alipayService.closeOrder(alipayCloseOrderDTO);
    }

    /**
     * 支付回调
     * @param request
     */
    @RequestMapping(value = "callback")
    public String payCallBack(HttpServletRequest request) throws AlipayApiException {
        AlipayCallBackVO aliPayCallBackVO = alipayService.callBack();

        log.info(aliPayCallBackVO.getOut_trade_no() + "-----" + aliPayCallBackVO.getTrade_status());

        //支付成功通过websocket将回调结果返回给前端，我们生产环境需要判断是否回调结果状态并改变数据库中订单的值
        if(aliPayCallBackVO.getTrade_status().equals(SUCCESS_PAY_STATUS)) {
            WebSocketService.sendMessage(JSON.toJSONString(aliPayCallBackVO),aliPayCallBackVO.getOut_trade_no());

        }
        //返回给支付宝回调的接口已经封装好了，不管成功时候都是返回SUCCESS
        return aliPayCallBackVO.getShouldResonse();
    }

    /**
     * 支付宝退款
     */
    @RequestMapping(value = "refund")
    public AlipayRefundVO refund(AlipayRefundDTO refundDTO) throws AlipayApiException {
        return alipayService.refund(refundDTO);
    }

    /**
     * 支付宝退款查询
     * @param refundQueryDTO
     * @return
     */
    @RequestMapping(value = "refundQuery")
    public AlipayRefundQueryVO refundQuery(AlipayRefundQueryDTO refundQueryDTO) throws AlipayApiException {
        return alipayService.refundQuery(refundQueryDTO);
    }
}

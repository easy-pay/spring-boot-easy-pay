package cn.isuyu.easy.pay.spring.boot.sample.controller;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.WxpayCloseOrderDTO;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.WxpayQrcodeDTO;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.WxpayRefundDTO;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.WxpayRefundQueryDTO;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.service.WxPayService;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.utils.IpUtils;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.vos.*;
import cn.isuyu.easy.pay.spring.boot.sample.service.WebSocketService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/10 上午8:25
 */
@RestController
@RequestMapping(value = "wx")
@Slf4j
public class WxpayController {

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 微信二维码支付
     * @param qrcodeDTO
     * @return
     */
    @RequestMapping(value = "qrcode")
    public WxpayQrcodeVO wxQrcode(WxpayQrcodeDTO qrcodeDTO) throws Exception {

        return wxPayService.qrcode(qrcodeDTO);
    }

    /**
     * h5支付
     * @param wxQrcodeDTO
     * @return
     * @throws Exception
     */
    public WxpayH5CreateOrderVO h5pay(WxpayQrcodeDTO wxQrcodeDTO) throws Exception {
        wxQrcodeDTO.setSpbillCreateIp(IpUtils.getIpAddress(request));
        return wxPayService.h5Pay(wxQrcodeDTO);
    }

    /**
     * 微信关闭订单
     * @return
     */
    @RequestMapping(value = "closeOrder")
    public WxpayCloseOrderVO closeOrder(WxpayCloseOrderDTO closeOrderDTO) throws Exception {
        return wxPayService.closeOrder(closeOrderDTO);
    }

    /**
     * 微信支付回调
     * @return
     */
    @RequestMapping(value = "callback")
    public String payCallBack() {

        WxpayCallBackVO wxpayCallBackVO = wxPayService.callback();
        log.info(wxpayCallBackVO.getOutTradeNo() +"-----"+ wxpayCallBackVO.getResultCode());
        //判断验签是否通过并且支付结果是不是成功
        if (wxpayCallBackVO.getSignResult() && wxpayCallBackVO.getResultCode().equals("SUCCESS")) {
            WebSocketService.sendMessage(JSON.toJSONString(wxpayCallBackVO),wxpayCallBackVO.getOutTradeNo());
            return "SUCCESS";
        }
        return "FAILER";
    }

    /**
     * 微信退款
     * @param wxpayRefundDTO
     * @return
     */
    @RequestMapping(value = "refund")
    public WxpayRefundVO wxRefund(WxpayRefundDTO wxpayRefundDTO) throws Exception {
        return wxPayService.refund(wxpayRefundDTO);
    }

    /**
     * 微信退款查询
     * @param refundQueryDTO
     * @return
     */
    @RequestMapping(value = "refundQuery")
    public WxpayRefundQueryVO refundQuery(WxpayRefundQueryDTO refundQueryDTO) throws Exception {
        return wxPayService.refundQuery(refundQueryDTO);
    }
}

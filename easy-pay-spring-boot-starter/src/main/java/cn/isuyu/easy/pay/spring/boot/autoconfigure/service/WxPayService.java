package cn.isuyu.easy.pay.spring.boot.autoconfigure.service;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.constant.APIURLENUMS;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.WxpayCloseOrderDTO;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.WxpayQrcodeDTO;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.WxpayRefundDTO;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.WxpayRefundQueryDTO;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.properties.WxPayProperties;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.utils.HttpUtils;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.utils.IpUtils;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.utils.XmlUtils;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.vos.*;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/11 8:54 上午
 */
@Slf4j
public class WxPayService {

    @Autowired
    private WxPayProperties wxPayProperties;

    @Autowired
    private HttpServletRequest request;

    /**
     * 支付成功的值
     */
    private static final String SUCCESS = "SUCCESS";

    /**
     * 二维码生成订单
     * @param wxQrcodeDTO
     * @return
     * @throws Exception
     */
    public WxpayQrcodeVO qrcode(WxpayQrcodeDTO wxQrcodeDTO) throws Exception {

        wxQrcodeDTO.setAppId(wxPayProperties.getAppId());
        wxQrcodeDTO.setMchId(wxPayProperties.getMchId());
        wxQrcodeDTO.setTradeType(wxPayProperties.getTradeType());
        wxQrcodeDTO.setNotifyUrl(wxPayProperties.getPayNotify());
        wxQrcodeDTO.setTotalFee(String.valueOf(new BigDecimal(wxQrcodeDTO.getTotalFee()).multiply(new BigDecimal("100")).intValue()));
        wxQrcodeDTO.setNonceStr(wxQrcodeDTO.getOutTradeNo());
        wxQrcodeDTO.setSpbillCreateIp(IpUtils.getIpAddress(request));
        wxQrcodeDTO.setSign(null);
        wxQrcodeDTO.setSign(SignUtils.createSign(wxQrcodeDTO,"MD5", wxPayProperties.getMchKey(), new String[0]));

        String xml = XmlUtils.toXML(wxQrcodeDTO);
        log.debug("微信二维码下单请求参数：{}", xml);
        String responseContent = HttpUtils.doPost(APIURLENUMS.API_URL_QRCODE.getUrl(),xml);
        log.debug("微信二维码下单返回参数：{}", responseContent);
        return WxpayQrcodeVO.fromXML(responseContent,WxpayQrcodeVO.class);
    }

    /**
     * h5下单
     * @param wxQrcodeDTO
     * @return
     * @throws Exception
     */
    public WxpayH5CreateOrderVO h5Pay(WxpayQrcodeDTO wxQrcodeDTO) throws Exception {
            wxQrcodeDTO.setAppId(wxPayProperties.getAppId());
            wxQrcodeDTO.setMchId(wxPayProperties.getMchId());
            wxQrcodeDTO.setTradeType("MWEB");
            wxQrcodeDTO.setNotifyUrl(wxPayProperties.getPayNotify());
            wxQrcodeDTO.setTotalFee(String.valueOf(new BigDecimal(wxQrcodeDTO.getTotalFee()).multiply(new BigDecimal("100")).intValue()));
            wxQrcodeDTO.setNonceStr(wxQrcodeDTO.getOutTradeNo());
            wxQrcodeDTO.setSign(null);
            wxQrcodeDTO.setSign(SignUtils.createSign(wxQrcodeDTO,"MD5", wxPayProperties.getMchKey(), new String[0]));

            String xml = XmlUtils.toXML(wxQrcodeDTO);
            log.debug("微信H5支付下单请求参数：{}", xml);
            String responseContent = HttpUtils.doPost(APIURLENUMS.API_URL_QRCODE.getUrl(),xml);
            log.debug("微信H5支付下单返回参数：{}", responseContent);
            return WxpayH5CreateOrderVO.fromXML(responseContent,WxpayH5CreateOrderVO.class);
    }

    /**
     * 关闭订单
     * @param wxCloseOrderDTO
     * @return
     * @throws Exception
     */
    public WxpayCloseOrderVO closeOrder(WxpayCloseOrderDTO wxCloseOrderDTO) throws Exception {
        log.debug("微信关闭订单号：{}", wxCloseOrderDTO.getOutTradeNo());
        wxCloseOrderDTO.setAppId(wxPayProperties.getAppId());
        wxCloseOrderDTO.setMchId(wxPayProperties.getMchId());
        wxCloseOrderDTO.setNonceStr(wxCloseOrderDTO.getOutTradeNo());
        wxCloseOrderDTO.setSign(null);
        wxCloseOrderDTO.setSign(SignUtils.createSign(wxCloseOrderDTO, "MD5", wxPayProperties.getMchKey(), new String[0]));
        String xml = XmlUtils.toXML(wxCloseOrderDTO);
        String responseContent = HttpUtils.doPost(APIURLENUMS.API_URL_CLOSE_ORDER.getUrl(),xml);
        log.debug("微信关闭订单返回参数：{}", responseContent);
        return WxpayCloseOrderVO.fromXML(responseContent,WxpayCloseOrderVO.class);
    }

    /**
     * 退款
     * @param wxRefundDTO
     * @return
     * @throws Exception
     */
    public WxpayRefundVO refund(WxpayRefundDTO wxRefundDTO) throws Exception {
        log.debug("微信退款商户订单号：{},微信退款商户退款单号：{}", wxRefundDTO.getOutTradeNo(),wxRefundDTO.getOutRefundNo());

        wxRefundDTO.setAppId(wxPayProperties.getAppId());
        wxRefundDTO.setMchId(wxPayProperties.getMchId());
        wxRefundDTO.setNonceStr(wxRefundDTO.getOutTradeNo());
        wxRefundDTO.setRefundNotifyUrl(wxPayProperties.getRefundNotify());
        //微信支付提交的金额是不能带小数点的，且是以分为单位，所以我们系统如果是以元为单位要处理下金额，即先乘以100，再去小数点
        wxRefundDTO.setRefundFee(String.valueOf(new BigDecimal(wxRefundDTO.getRefundFee()).multiply(new BigDecimal("100")).intValue()));
        wxRefundDTO.setTotalFee(String.valueOf(new BigDecimal(wxRefundDTO.getTotalFee()).multiply(new BigDecimal("100")).intValue()));
        wxRefundDTO.setSign(SignUtils.createSign(wxRefundDTO, "MD5", wxPayProperties.getMchKey(), new String[0]));
        String xml = XmlUtils.toXML(wxRefundDTO);
        log.debug(xml);
        String responseContent = HttpUtils.doRefund(APIURLENUMS.API_URL_REFUND.getUrl(),xml,wxPayProperties);
        log.debug("微信退款申请返回参数：{}", responseContent);
        WxpayRefundVO wxpayRefundVO =  WxpayRefundVO.fromXML(responseContent,WxpayRefundVO.class);
        //因为微信返回的是分，这里我们将分转换成元
        if (wxpayRefundVO.getResultCode().equals(SUCCESS)) {
            wxpayRefundVO.setRefundFee(String.valueOf(new BigDecimal(wxpayRefundVO.getRefundFee()).divide(new BigDecimal("100")).floatValue()));
            wxpayRefundVO.setTotalFee(String.valueOf(new BigDecimal(wxpayRefundVO.getTotalFee()).divide(new BigDecimal("100")).floatValue()));
        }
        return wxpayRefundVO;
    }

    /**
     * 微信支付回调
     * @return
     */
    public WxpayCallBackVO callback() {

        String xmlData = getRequestXml(request);
        log.debug(xmlData);
        WxPayOrderNotifyResult wxPayOrderNotifyResult =
                WxPayOrderNotifyResult.fromXML(xmlData,WxPayOrderNotifyResult.class);

        WxpayCallBackVO wxCallBackVO = new WxpayCallBackVO();
        wxCallBackVO.setAppId(wxPayOrderNotifyResult.getAppid());
        wxCallBackVO.setOutTradeNo(wxPayOrderNotifyResult.getOutTradeNo());
        wxCallBackVO.setTransactionId(wxPayOrderNotifyResult.getTransactionId());
        wxCallBackVO.setNonceStr(wxPayOrderNotifyResult.getNonceStr());
        wxCallBackVO.setMchId(wxPayOrderNotifyResult.getMchId());
        wxCallBackVO.setResultCode(wxPayOrderNotifyResult.getResultCode());
        //金额是分，这里做了处理将分转换成了元
        wxCallBackVO.setTotalFee(String.valueOf(wxPayOrderNotifyResult.getTotalFee().floatValue()/100));
        wxCallBackVO.setTimeEnd(wxPayOrderNotifyResult.getTimeEnd());

        boolean result = SignUtils.checkSign(wxPayOrderNotifyResult,"MD5",wxPayProperties.getMchKey());
        wxCallBackVO.setSignResult(result);

        return wxCallBackVO;
    }

    /**
     * 退款查询
     * @param wxRefundQueryDTO
     * @return
     * @throws Exception
     */
    public WxpayRefundQueryVO refundQuery(WxpayRefundQueryDTO wxRefundQueryDTO) throws Exception {
        log.debug("微信退款查询订单号：{}", wxRefundQueryDTO.getOutTradeNo());
        wxRefundQueryDTO.setAppId(wxPayProperties.getAppId());
        wxRefundQueryDTO.setMchId(wxPayProperties.getMchId());
        wxRefundQueryDTO.setNonceStr(wxRefundQueryDTO.getOutTradeNo());
        wxRefundQueryDTO.setSign(SignUtils.createSign(wxRefundQueryDTO, "MD5", wxPayProperties.getMchKey(), new String[0]));
        String xml = XmlUtils.toXML(wxRefundQueryDTO);
        log.debug(xml);
        String responseContent = null;
        responseContent = HttpUtils.doPost(APIURLENUMS.API_URL_REFUND_QUERY.getUrl(),xml);
        log.debug("微信退款查询返回参数：{}", responseContent);
        WxpayRefundQueryVO wxpayRefundQueryVO = WxpayRefundQueryVO.fromXML(responseContent,WxpayRefundQueryVO.class);
        if (wxpayRefundQueryVO.getResultCode().equals(SUCCESS)) {
            wxpayRefundQueryVO.setRefundFee(new BigDecimal(wxpayRefundQueryVO.getRefundFee()).divide(new BigDecimal("100")).floatValue()+"");
        }
        return wxpayRefundQueryVO;
    }

    /**
     * 获取支付和退款回调参数
     * @return
     */
    private String getRequestXml(HttpServletRequest request) {
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            return  new String(outSteam.toByteArray(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

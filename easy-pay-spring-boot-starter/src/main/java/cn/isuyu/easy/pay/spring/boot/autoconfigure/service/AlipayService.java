package cn.isuyu.easy.pay.spring.boot.autoconfigure.service;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.*;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.properties.AlipayProperties;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.utils.JsonUtils;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.vos.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/11 8:47 上午
 */
@Slf4j
public class AlipayService {

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private HttpServletRequest request;

    /**
     * PC端电脑支付固定值
     */
    private final static String FAST_INSTANT_TRADE_PAY = "FAST_INSTANT_TRADE_PAY";

    /**
     * 获取支付二维码
     * @param alipayQrcodeDTO
     * @return
     * @throws AlipayApiException
     */
    public AlipayQrcodeVO qrcode(AlipayQrcodeDTO alipayQrcodeDTO) throws AlipayApiException {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(JsonUtils.jsonFormat(alipayQrcodeDTO));
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        log.debug(JSON.toJSONString(request));
        AlipayTradePrecreateResponse alipayTradePrecreateResponse = alipayClient.execute(request);
        log.debug(JSON.toJSONString(alipayTradePrecreateResponse));
        JSONObject qrcodeResponse = JSON.parseObject(alipayTradePrecreateResponse.getBody());

        AlipayQrcodeVO qrcodeVO = qrcodeResponse.getObject("alipay_trade_precreate_response",AlipayQrcodeVO.class);

        return qrcodeVO;
    }

    /**
     * 支付回调 这里不管什么都全部返回SUCCESS
     * 当状态为WAIT_BUYER_PAY 也会对调一次
     * 如果不返回 支付宝会一直会回调
     * @return
     * @throws AlipayApiException
     */
    public AlipayCallBackVO callBack() throws AlipayApiException {
        Map<String, String> map = JsonUtils.requestToMap(request);
        //验签
        boolean flag = AlipaySignature.rsaCheckV1(map,alipayProperties.getPublicKey() ,alipayProperties.getCharset(),
                alipayProperties.getSignType());
        String json = JSON.toJSONString(map);
        log.debug(json);
        if (!flag) {
            throw new RuntimeException("alipay payment callback sign check failed");
        }
        AlipayCallBackVO  aliPayCallBackVO = JSON.parseObject(json,AlipayCallBackVO.class);

        return aliPayCallBackVO;
    }

    /**
     * 订单关闭
     * @param alipayCloseOrderDTO
     * @return
     * @throws AlipayApiException
     */
    public AlipayCloseOrderVO closeOrder(AlipayCloseOrderDTO alipayCloseOrderDTO) throws AlipayApiException {

        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizContent(JsonUtils.jsonFormat(alipayCloseOrderDTO));
        log.debug(JSON.toJSONString(request));
        AlipayTradeCloseResponse tradeCloseResponse = alipayClient.execute(request);
        log.debug(JSON.toJSONString(tradeCloseResponse));

        JSONObject qrcodeResponse = JSON.parseObject(tradeCloseResponse.getBody());

        AlipayCloseOrderVO alipayCloseOrderVO = qrcodeResponse.getObject("alipay_trade_close_response",AlipayCloseOrderVO.class);
        return alipayCloseOrderVO;
    }

    /**
     * Pc端支付
     * @param alipayPcPayDTO
     * @return
     * @throws AlipayApiException
     */
    public String pcPay(AlipayPcPayDTO alipayPcPayDTO) throws AlipayApiException {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //设置回调页面
        alipayRequest.setReturnUrl(alipayProperties.getReturnUrl());
        //回调参数
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        //官网支付固定值
        alipayPcPayDTO.setProductCode(FAST_INSTANT_TRADE_PAY);
        alipayRequest.setBizContent(JsonUtils.jsonFormat(alipayPcPayDTO));
        log.debug(JSON.toJSONString(alipayRequest));
        String form = alipayClient.pageExecute(alipayRequest).getBody();
        log.debug(form);
        return form;
    }


    /**
     * h5支付
     * @param aliPayH5PayDTO
     * @return
     * @throws AlipayApiException
     */
    public String h5pay(AliPayH5PayDTO aliPayH5PayDTO) throws AlipayApiException {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizContent(JsonUtils.jsonFormat(aliPayH5PayDTO));

        log.debug(JSON.toJSONString(request));
        AlipayTradeWapPayResponse tradeWapPayResponse = alipayClient.pageExecute(request);
        log.debug(JSON.toJSONString(tradeWapPayResponse));
        return tradeWapPayResponse.getBody();
    }

    /**
     * 退款
     * @param alipayRefundDTO
     * @return
     * @throws AlipayApiException
     */
    public AlipayRefundVO refund(AlipayRefundDTO alipayRefundDTO) throws AlipayApiException {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JsonUtils.jsonFormat(alipayRefundDTO));
        log.debug(JSON.toJSONString(request));
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        log.debug(JSON.toJSONString(response));
        JSONObject refundResponse = JSON.parseObject(response.getBody());
        AlipayRefundVO refundVO = refundResponse.getObject("alipay_trade_refund_response",AlipayRefundVO.class);
        return refundVO;
    }

    /**
     * 退款查询
     * @param alipayRefundQueryDTO
     * @return
     * @throws AlipayApiException
     */
    public AlipayRefundQueryVO refundQuery(AlipayRefundQueryDTO alipayRefundQueryDTO) throws AlipayApiException {
        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        //如果退款的时候商户退款单号并没有填写，则默认和商户交易流水号一样
        if(alipayRefundQueryDTO.getOutRequestNo() == null || "".equals(alipayRefundQueryDTO.getOutRequestNo())) {
            alipayRefundQueryDTO.setOutRequestNo(alipayRefundQueryDTO.getOutTradeNo());
        }
        request.setBizContent(JsonUtils.jsonFormat(alipayRefundQueryDTO));
        log.debug(JSON.toJSONString(request));
        AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
        log.debug(JSON.toJSONString(response));
        JSONObject refundResponse = JSON.parseObject(response.getBody());

        return refundResponse.getObject("alipay_trade_fastpay_refund_query_response",AlipayRefundQueryVO.class);
    }


    /**
     * 获取AlipayClient对象
     * @return
     */
    public AlipayClient getAlipayClient() {

        return new DefaultAlipayClient(alipayProperties.getGatewayUrl(), alipayProperties.getAppId(), alipayProperties.getPrivateKey(),
                        "JSON", alipayProperties.getCharset(), alipayProperties.getPublicKey(), alipayProperties.getSignType());
    }
}

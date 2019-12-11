package cn.isuyu.easy.pay.spring.boot.sample;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.dto.AlipayQrcodeDTO;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.properties.AlipayProperties;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.service.AlipayService;
import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/11 9:04 上午
 */
@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private AlipayService alipayService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @RequestMapping(value = "/")
    public Object test() throws AlipayApiException {
        AlipayQrcodeDTO alipayQrcodeDTO = new AlipayQrcodeDTO()
                .setSubject("ceshi")
                .setTotalAmount(0.01)
                .setOutTradeNo("1234569856");

        return  alipayService.qrcode(alipayQrcodeDTO);
    }

}

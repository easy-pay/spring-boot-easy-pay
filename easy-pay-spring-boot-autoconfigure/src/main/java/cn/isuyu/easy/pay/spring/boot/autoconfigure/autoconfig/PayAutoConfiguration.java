package cn.isuyu.easy.pay.spring.boot.autoconfigure.autoconfig;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.properties.AlipayProperties;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.properties.WxPayProperties;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.service.AlipayService;
import cn.isuyu.easy.pay.spring.boot.autoconfigure.service.WxPayService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019/12/11 8:35 上午
 */
@Configuration
@ConditionalOnClass({AlipayService.class, WxPayProperties.class})
@EnableConfigurationProperties({AlipayProperties.class,WxPayProperties.class})
public class PayAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AlipayService alipayService() {
        return new AlipayService();
    }

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService() {
        return new WxPayService();
    }
}

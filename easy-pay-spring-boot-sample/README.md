### spring-boot-easy-pay-demo

> 这是一个Easy-Pay与springboot框架来实现支付宝和微信支付的使用Demo


<img width="850" height="500" src="https://github.com/easy-pay/spring-boot-easy-pay/blob/master/docs/demo.png"/>

#### 事先准备

> 要想本地调试的话请使用`ngrok`做一个内网穿透，这里推荐一个Mac环境的内网穿透教程

https://www.jianshu.com/p/5c9d77d7a8f9

```yaml
easy:
  pay:
    #支付宝支付参数配置
    alipay:
      #应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
      appId:
      #商户私钥，您的PKCS8格式RSA2私钥
      privateKey:
      publicKey:
      #服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
      notifyUrl: http://0b228e36.ngrok.io/alipay/callback
      #页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
      returnUrl: http://0b228e36.ngrok.io/
    #微信支付参数配置
    wxpay:
      #公众号appid
      appId:
      #商户id
      mchId:
      #支付api安全密钥
      mchKey:
      #支付结果回调地址
      payNotify: http://0b228e36.ngrok.io/wx/callback
      #退款结果回调(该值暂时还未使用到，因为退款我并没有做回调，待以后完善吧)
      refundNotify:
      #项目根目录根目录下的证书名称(退款需要用到证书)
      certName: wx_pay_cert.p12

```
#### 启动项目

`Application.java`该类有个`Main`方法，直接启动`Main`方法就将项目启动起来了

#### 访问项目

```html
127.0.0.1:9999/index
```

  



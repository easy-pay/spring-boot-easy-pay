package cn.isuyu.easy.pay.spring.boot.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/9 上午11:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

    private static final long serialVersionUID = 8957107006902627635L;

    private String userName;

    private Session session;
}
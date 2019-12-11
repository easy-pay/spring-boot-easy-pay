package cn.isuyu.easy.pay.spring.boot.sample.service;

import cn.isuyu.easy.pay.spring.boot.sample.entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/9 上午11:33
 */
@ServerEndpoint(value = "/easypay/{orderId}")
@Component
@Slf4j
public class WebSocketService {
    /**
     *
     * 用线程安全的CopyOnWriteArraySet来存放客户端连接的信息
     */
    private static CopyOnWriteArraySet<Client> socketServers = new CopyOnWriteArraySet<Client>();

    /**
     *
     * websocket封装的session,信息推送，就是通过它来信息推送
     */
    private Session session;


    /**
     *
     * 用户连接时触发，我们将其添加到
     * 保存客户端连接信息的socketServers中
     *
     * @param session
     * @param orderId
     */
    @OnOpen
    public void open(Session session, @PathParam(value="orderId")String orderId){

        this.session = session;
        socketServers.add(new Client(orderId,session));

        log.info("客户端:【{}】连接成功",orderId);

    }

    /**
     *
     * 连接关闭触发，通过sessionId来移除
     * socketServers中客户端连接信息
     */
    @OnClose
    public void onClose(){
        socketServers.forEach(client ->{
            if (client.getSession().getId().equals(session.getId())) {

                log.info("客户端:【{}】断开连接",client.getUserName());
                socketServers.remove(client);

            }
        });
    }

    /**
     *
     * 信息发送的方法，通过客户端的userName
     * 拿到其对应的session，调用信息推送的方法
     * @param message
     * @param orderId
     */
    public synchronized static void sendMessage(String message,String orderId) {

        socketServers.forEach(client ->{
            if (orderId.equals(client.getUserName())) {
                try {
                    client.getSession().getBasicRemote().sendText(message);

                    log.info("服务端推送给客户端 :【{}】",client.getUserName(),message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     *
     * 获取在线用户名，前端界面需要用到
     * @return
     */
    public synchronized static List<String> getOnlineUsers(){

        List<String> onlineUsers = socketServers.stream()
                .map(client -> client.getUserName())
                .collect(Collectors.toList());

        return onlineUsers;
    }
}

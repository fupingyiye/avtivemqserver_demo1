package com.icss;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class Sender {

    public static void main(String[] args) throws Exception{
        //第一步：建立ConnectionFactory工厂对象，需要用户名，密码，以及连接的activemq
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(
                "lmz","lmz",
               // ActiveMQConnectionFactory.DEFAULT_USER,
               // ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        //第二步，获得连接
        Connection connection=connectionFactory.createConnection();
        //第三步：获得会话（上下文环境对象）
        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        //第四步，通过Session创建Destination对象，指的是客户端消费消息的目的的,即队列名字
        Destination destination=session.createQueue("first");
        //第五步：通过session创建消息的生产者
        MessageProducer producer=session.createProducer(null);
        //第六步，我们可以使用MessageProducer的setDeliveryMode方法为其设置持久化特性和非持久化特性
        //producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //第七步：使用Jms规范的TextMessage形式创建数据

        for(int i=0; i<10;i++){
            TextMessage msg=session.createTextMessage("我是消息内容第"+i+"条");
            //第一个参数 目标地址
            //第二个参数 具体的数据信息
            //第三个参数 传送数据的模式
            //第四个参数 优先级
            //第五个参数 消息的过期时间
            producer.send(destination,msg);
            //TimeUnit.SECONDS.sleep(1);
        }

        //支持事务，必须提交
        session.commit();

        if(connection!=null){
            connection.close();
        }




    }
}

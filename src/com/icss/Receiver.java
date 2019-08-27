package com.icss;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver {
    public static void main(String[] args) throws  Exception {
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(

//                ActiveMQConnectionFactory.DEFAULT_USER,
//                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "lmz","lmz",
                "tcp://localhost:61616"
        );
        //创建连接
        Connection connection=connectionFactory.createConnection();
        connection.start();

        //获得session,用于接收消息，参数1为是否启用事务，参数2为签收模式，一般自动签收
       Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

        //指定消息来源
        Destination destination=session.createQueue("first");

        //创建MessageConsumer
        MessageConsumer consumer = session.createConsumer(destination);

        while (true){
            TextMessage msg = (TextMessage) consumer.receive();

            System.out.println("消费数据："+ msg.getText());
        }
    }

}

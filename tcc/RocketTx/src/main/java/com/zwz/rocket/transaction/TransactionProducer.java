package com.zwz.rocket.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @date : 2020/5/31 11:25
 * @author: zwz
 */
public class TransactionProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, InterruptedException {
        TransactionMQProducer producer = new TransactionMQProducer("text_tx_producer_group_name");
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 5, 100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("text_tx_producer_group_name" + "-thread");
                return thread;
            }
        });

        producer.setNamesrvAddr(Const.NAMESRV_ADDR_MASTER_SLAVE);
        producer.setExecutorService(pool);

        //1.异步执行本地事务
        //2.做消息回查
        TransactionListener transactionListener = new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.err.println("-----执行本地事务-----");
                String callArg = (String) arg;
                System.err.println("callArg:" + callArg);
                System.err.println("msg:" + msg);

                // tx.begin

                // 数据库落库操作

                // tx.commit
                return LocalTransactionState.UNKNOW;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.err.println("----回调消息检查----msg:" + msg);
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        };

        producer.setTransactionListener(transactionListener);
        producer.start();

        Message message = new Message("test_tx_topic0", "tagA", "key",
                ("hello rocketmq 4 tx!").getBytes(RemotingHelper.DEFAULT_CHARSET));

        producer.sendMessageInTransaction(message, "我是回调的参数");

        Thread.sleep(10000);
        producer.shutdown();
    }
}
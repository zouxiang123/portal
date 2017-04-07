/**   
 * @Title: KafkaProducerUtil.java 
 * @Package com.lt.platform.util.kafka
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年8月11日 下午6:18:40 
 *
 */
package com.xtt.platform.util.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.xtt.platform.util.config.PropertiesUtil;

/**
 * kafka producer util
 * 
 * @ClassName: KafkaProducerUtil
 * @date: 2016年8月11日 下午6:19:48
 * @version: V1.0
 *
 */
public class KafkaProducerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger("MQLogger");
    private static final String DEFAULT_STRATEGY = (String) PropertiesUtil.getContextProperty("MQStrategy");;
    private static final String STRATEGY_NORMAL = "NORMAL";
    private static final String STRATEGY_CALLBACK = "CALLBACK";

    private static KafkaTemplate<Integer, String> kafkaTemplate;

    public static ListenableFuture<SendResult<Integer, String>> sendDefault(String msg) {
        // just normal strategy need send
        if (STRATEGY_NORMAL.equals(DEFAULT_STRATEGY)) {
            return kafkaTemplate.sendDefault(msg);
        }
        return null;
    }

    public static ListenableFuture<SendResult<Integer, String>> send(String topic, String msg) {
        // just normal strategy need send
        if (STRATEGY_NORMAL.equals(DEFAULT_STRATEGY)) {
            return kafkaTemplate.send(topic, msg);
        }
        return null;
    }

    /**
     * send message with callback
     * 
     * @Title: send
     * @param topic
     * @param msg
     * @param callback
     *
     */
    public static void send(String topic, String msg, KafkaExceptionCallback callback) {
        if (STRATEGY_CALLBACK.equals(DEFAULT_STRATEGY)) {// if use call back strategy,do not send
            if (callback != null) {
                callback.callBack();
            }
            return;
        }
        ListenableFuture<SendResult<Integer, String>> listenter = kafkaTemplate.send(topic, msg);
        listenter.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onSuccess(SendResult<Integer, String> result) {

            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.warn("failed to send message{topic:" + topic + ",content:" + msg + " }, ", ex.getCause());
                if (callback != null) {
                    callback.callBack();
                }
            }
        });
    }

    public KafkaTemplate<Integer, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public void setKafkaTemplate(KafkaTemplate<Integer, String> kafkaTemplate) {
        KafkaProducerUtil.kafkaTemplate = kafkaTemplate;
    }

}

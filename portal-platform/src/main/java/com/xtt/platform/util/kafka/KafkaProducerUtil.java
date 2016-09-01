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

	public static final String TOPIC_SYS_LOG = "sysLog";
	public static final String TOPIC_SECRETARY = "secretary";
	public static final String TOPIC_PROCESSHIST = "processHist";
	public static final String TOPIC_CHARGE = "charge";

	private static KafkaTemplate<Integer, String> kafkaTemplate;

	public static ListenableFuture<SendResult<Integer, String>> sendDefault(String msg) {
		return kafkaTemplate.sendDefault(msg);
	}

	public static ListenableFuture<SendResult<Integer, String>> send(String topic, String msg) {
		return kafkaTemplate.send(topic, msg);
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
		ListenableFuture<SendResult<Integer, String>> listenter = kafkaTemplate.send(topic, msg);
		listenter.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
			@Override
			public void onSuccess(SendResult<Integer, String> result) {

			}

			@Override
			public void onFailure(Throwable ex) {
				LOGGER.warn("send massage{topic:" + topic + ",content:" + msg + " } failed,callback", ex.getMessage());
				if (callback != null) {
					callback.onException();
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

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

/**
 * kafka producer util
 * 
 * @ClassName: KafkaProducerUtil
 * @date: 2016年8月11日 下午6:19:48
 * @version: V1.0
 *
 */
public class KafkaProducerUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerUtil.class);
	/** 最大尝试次数 */
	private static final Integer MAX_TRY_COUNT = 1;

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
		if (callback != null) {
			send(topic, msg, callback, 1);
		} else {
			send(topic, msg);
		}
	}

	private static void send(String topic, String msg, KafkaExceptionCallback callback, int times) {
		ListenableFuture<SendResult<Integer, String>> listenter = kafkaTemplate.send(topic, msg);
		times++;
		if (listenter.isDone()) {
			try {
				listenter.get();
			} catch (Exception e) {
				if (times <= MAX_TRY_COUNT) {
					send(topic, msg, callback, times);
				} else {
					LOGGER.warn("send massage{topic:" + topic + ",content:" + msg + " } failed,callback");
					callback.onException();
				}
			}
		}
	}

	public KafkaTemplate<Integer, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public void setKafkaTemplate(KafkaTemplate<Integer, String> kafkaTemplate) {
		KafkaProducerUtil.kafkaTemplate = kafkaTemplate;
	}

}

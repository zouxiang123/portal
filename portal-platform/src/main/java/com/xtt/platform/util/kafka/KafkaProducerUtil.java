/**   
 * @Title: KafkaProducerUtil.java 
 * @Package com.lt.platform.util.kafka
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2016年8月11日 下午6:18:40 
 *
 */
package com.xtt.platform.util.kafka;

import org.springframework.kafka.core.KafkaTemplate;

/**
 * kafka producer util
 * 
 * @ClassName: KafkaProducerUtil
 * @date: 2016年8月11日 下午6:19:48
 * @version: V1.0
 *
 */
public class KafkaProducerUtil {
	public static final String TOPIC_SYS_LOG = "sysLog";
	public static final String TOPIC_SECRETARY = "secretary";
	public static final String TOPIC_PROCESSHIST = "processHist";
	public static final String TOPIC_CHARGE = "charge";

	private static KafkaTemplate<Integer, String> kafkaTemplate;

	public static void sendDefault(String msg) {
		kafkaTemplate.sendDefault(msg);
	}

	public static void send(String topic, String msg) {
		kafkaTemplate.send(topic, msg);
	}

	public KafkaTemplate<Integer, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public void setKafkaTemplate(KafkaTemplate<Integer, String> kafkaTemplate) {
		KafkaProducerUtil.kafkaTemplate = kafkaTemplate;
	}

}

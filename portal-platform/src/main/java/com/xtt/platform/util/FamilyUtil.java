package com.xtt.platform.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取姓氏首字母
 * @author Cyw
 *
 */
public class FamilyUtil {
	
	
	/**
	 * 首字母为A的姓氏
	 */
   static final Map<String, Object> map = new HashMap<>();
   static{
	   /**
		* 首字母为A的姓氏
		*/
	   map.put("艾", "A");
	   map.put("安", "A"); 
	   map.put("敖", "A");
	   /**
		* 首字母为B的姓氏
		*/
	   map.put("巴", "B");
	   map.put("百", "B");
	   map.put("柏", "B");
	   map.put("班", "B");
	   map.put("包", "B");
	   map.put("鲍", "B");
	   map.put("暴", "B");
	   map.put("贝", "B");
	   map.put("贲", "B");
	   map.put("毕", "B");
	   map.put("边", "B");
	   map.put("卞", "B");
	   map.put("别", "B");
	   map.put("邴", "B");
	   map.put("伯", "B");
	   map.put("薄", "B");
	   map.put("卜", "B");
	   map.put("步", "B");
	   /**
		* 首字母为C的姓氏
		*/
	   map.put("蔡", "C");
	   map.put("苍", "C");
	   map.put("曹", "C");
	   map.put("岑", "C");
	   map.put("柴", "C");
	   map.put("昌", "C");
	   map.put("常", "C");
	   map.put("晁", "C");
	   map.put("巢", "C");
	   map.put("车", "C");
	   map.put("陈", "C");
	   map.put("成", "C");
	   map.put("程", "C");
	   map.put("池", "C");
	   map.put("充", "C");
	   map.put("储", "C");
	   map.put("褚", "C");
	   map.put("淳", "C");
	   map.put("从", "C");
	   map.put("单", "C");
	   /**
		* 首字母为D的姓氏
		*/
	   map.put("戴", "D");
	   map.put("党", "D");
	   map.put("邓", "D");
	   map.put("狄", "D");
	   map.put("刁", "D");
	   map.put("丁", "D");
	   map.put("东", "D");
	   map.put("董", "D");
	   map.put("都", "D");
	   map.put("窦", "D");
	   map.put("堵", "D");
	   map.put("杜", "D");
	   map.put("端", "D");
	   map.put("段", "D");
	   /**
		* 首字母为E的姓氏
		*/
	   map.put("鄂", "E");
	   /**
		* 首字母为F的姓氏
		*/
	   map.put("封", "F");
	   map.put("符", "F");
	   map.put("富", "F");
	   map.put("傅", "F");
	   map.put("丰", "F");
	   map.put("房", "F");
	   map.put("扶", "F");
	   map.put("酆", "F");
	   map.put("范", "F");
	   map.put("方", "F");
	   map.put("凤", "F");
	   map.put("冯", "F");
	   map.put("费", "F");
	   map.put("伏", "F");
	   map.put("樊", "F");
	   /**
		* 首字母为G的姓氏
		*/
	   map.put("盖", "G");
	   map.put("干", "G");
	   map.put("甘", "G"); 
	   map.put("高", "G"); 
	   map.put("郜", "G"); 
	   map.put("戈", "G"); 
	   map.put("葛", "G"); 
	   map.put("耿", "G");
	   map.put("弓", "G");
	   map.put("公", "G");
	   map.put("宫", "G"); 
	   map.put("龚", "G"); 
	   map.put("巩", "G"); 
	   map.put("贡", "G"); 
	   map.put("勾", "G"); 
	   map.put("缑", "G");
	   map.put("古", "G");
	   map.put("谷", "G");
	   map.put("顾", "G"); 
	   map.put("关", "G"); 
	   map.put("广", "G"); 
	   map.put("归", "G");
	   map.put("桂", "G"); 
	   map.put("郭", "G");
	   map.put("国", "G");
	   /**
		* 首字母为H的姓氏
		*/
	   map.put("黄", "H");
	   map.put("胡", "H");
	   map.put("洪", "H");
	   map.put("怀", "H");
	   map.put("滑", "H");
	   map.put("弘", "H");
	   map.put("后", "H");
	   map.put("宦", "H");
	   map.put("侯", "H");
	   map.put("惠", "H");
	   map.put("红", "H");
	   map.put("花", "H");
	   map.put("杭", "H");
	   map.put("郝", "H");
	   map.put("和", "H");
	   map.put("贺", "H");
	   map.put("霍", "H");
	   map.put("华", "H");
	   map.put("何", "H");
	   map.put("衡", "H");
	   map.put("韩", "H");
	   map.put("桓", "H");
	   map.put("赫", "H");
	   map.put("呼", "H");
	   map.put("皇", "H");
	   /**
		* 首字母为J的姓氏
		*/
	   map.put("吉", "J");
	   map.put("景", "J");
	   map.put("季", "J");
	   map.put("暨", "J");
	   map.put("嵇", "J");
	   map.put("居", "J");
	   map.put("焦", "J");
	   map.put("姬", "J");
	   map.put("家", "J");
	   map.put("计", "J");
	   map.put("夹", "J");
	   map.put("蒋", "J");
	   map.put("贾", "J");
	   map.put("纪", "J");
	   map.put("经", "J");
	   map.put("江", "J");
	   map.put("姜", "J");
	   map.put("靳", "J");
	   map.put("井", "J");
	   map.put("简", "J");
	   map.put("鞠", "J");
	   map.put("蓟", "J");
	   map.put("郏", "J");
	   map.put("荆", "J");
	   map.put("冀", "J");
	   map.put("金", "J");
	   /**
		* 首字母为K的姓氏
		*/
	   map.put("康", "K");
	   map.put("匡", "K");
	   map.put("况", "K");
	   map.put("柯", "K");
	   map.put("空", "K");
	   map.put("寇", "K");
	   map.put("隗", "K");
	   map.put("夔", "K");
	   map.put("阚", "K");
	   map.put("孔", "K");
	   /**
		* 首字母为L的姓氏
		*/
	   map.put("赖", "L");
	   map.put("蓝", "L");
	   map.put("郎", "L");
	   map.put("劳", "L");
	   map.put("雷", "L");
	   map.put("冷", "L");
	   map.put("黎", "L");
	   map.put("李", "L");
	   map.put("厉", "L");
	   map.put("郦", "L");
	   map.put("连", "L");
	   map.put("廉", "L");
	   map.put("梁", "L");
	   map.put("廖", "L");
	   map.put("蔺", "L");
	   map.put("凌", "L");
	   map.put("令", "L");
	   map.put("刘", "L");
	   map.put("柳", "L");
	   map.put("龙", "L");
	   map.put("隆", "L");
	   map.put("娄", "L");
	   map.put("卢", "L");
	   map.put("鲁", "L");
	   map.put("陆", "L");
	   map.put("逯", "L");
	   map.put("禄", "L");
	   map.put("路", "L");
	   map.put("闾", "L");
	   map.put("吕", "L");
	   map.put("栾", "L");
	   map.put("罗", "L");
	   map.put("骆", "L");
	   /**
		* 首字母为M的姓氏
		*/
	   map.put("麻", "M");
	   map.put("马", "M");
	   map.put("满", "M");
	   map.put("茅", "M");
	   map.put("梅", "M");
	   map.put("蒙", "M");
	   map.put("糜", "M");
	   map.put("米", "M");
	   map.put("宓", "M");
	   map.put("苗", "M");
	   map.put("闵", "M");
	   map.put("明", "M");
	   map.put("缪", "M");
	   map.put("莫", "M");
	   map.put("墨", "M");
	   map.put("牧", "M");
	   map.put("慕", "M");
	   map.put("穆", "M");
	   /**
		* 首字母为N的姓氏
		*/
	   map.put("那", "N");
	   map.put("南", "N");
	   map.put("能", "N");
	   map.put("倪", "N");
	   map.put("年", "N");
	   map.put("聂", "N");
	   map.put("乜", "N");
	   map.put("宁", "N");
	   map.put("牛", "N");
	   map.put("农", "N");
	   /**
		* 首字母为O的姓氏
		*/
	   map.put("欧", "O");
	   /**
		* 首字母为P的姓氏
		*/
	   map.put("潘", "P");
	   map.put("庞", "P");
	   map.put("彭", "P");
	   map.put("蓬", "P");
	   map.put("皮", "P");
	   map.put("平", "P");
	   map.put("蒲", "P");
	   map.put("濮", "P");
	   map.put("浦", "P");
	   map.put("裴", "P");
	   /**
		* 首字母为Q的姓氏
		*/
	   map.put("戚", "Q");
	   map.put("漆", "Q");
	   map.put("亓", "Q");
	   map.put("祁", "Q");
	   map.put("齐", "Q");
	   map.put("钱", "Q");
	   map.put("强", "Q");
	   map.put("乔", "Q");
	   map.put("谯", "Q");
	   map.put("秦", "Q");
	   map.put("邱", "Q");
	   map.put("秋", "Q");
	   map.put("仇", "Q");
	   map.put("裘", "Q");
	   map.put("曲", "Q");
	   map.put("屈", "Q");
	   map.put("璩", "Q");
	   map.put("全", "Q");
	   map.put("权", "Q");
	   map.put("阙", "Q");
	   /**
		* 首字母为R的姓氏
		*/
	   map.put("冉", "R");
	   map.put("壤", "R");
	   map.put("饶", "R");
	   map.put("任", "R");
	   map.put("戎", "R");
	   map.put("荣", "R");
	   map.put("容", "R");
	   map.put("融", "R");
	   map.put("茹", "R");
	   map.put("汝", "R");
	   map.put("阮", "R");
	   map.put("芮", "R");
	   /**
		* 首字母为S的姓氏
		*/
	   map.put("桑", "S");
	   map.put("沙", "S");
	   map.put("山", "S");
	   map.put("单", "S");
	   map.put("商", "S");
	   map.put("上", "S");
	   map.put("尚", "S");
	   map.put("韶", "S");
	   map.put("邵", "S");
	   map.put("佘", "S");
	   map.put("厍", "S");
	   map.put("申", "S");
	   map.put("莘", "S");
	   map.put("沈", "S");
	   map.put("慎", "S");
	   map.put("盛", "S");
	   map.put("师", "S");
	   map.put("施", "S");
	   map.put("石", "S");
	   map.put("时", "S");
	   map.put("史", "S");
	   map.put("寿", "S");
	   map.put("殳", "S");
	   map.put("舒", "S");
	   map.put("束", "S");
	   map.put("双", "S");
	   map.put("水", "S");
	   map.put("司", "S");
	   map.put("松", "S");
	   map.put("宋", "S");
	   map.put("苏", "S");
	   map.put("宿", "S");
	   map.put("孙", "S");
	   map.put("索", "S");
	   /**
		* 首字母为T的姓氏
		*/
	   map.put("唐", "T");
	   map.put("台", "T");
	   map.put("太", "T");
	   map.put("谈", "T");
	   map.put("谭", "T");
	   map.put("澹", "T");
	   map.put("汤", "T");
	   map.put("陶", "T");
	   map.put("滕", "T");
	   map.put("田", "T");
	   map.put("通", "T");
	   map.put("钭", "T");
	   map.put("涂", "T");
	   map.put("屠", "T");
	   map.put("拓", "T");
	   /**
		* 首字母为W的姓氏
		*/
	   map.put("万", "W");
	   map.put("汪", "W");
	   map.put("王", "W");
	   map.put("危", "W");
	   map.put("微", "W");
	   map.put("韦", "W");
	   map.put("卫", "W");
	   map.put("蔚", "W");
	   map.put("魏", "W");
	   map.put("温", "W");
	   map.put("文", "W");
	   map.put("闻", "W");
	   map.put("翁", "W");
	   map.put("沃", "W");
	   map.put("乌", "W");
	   map.put("邬", "W");
	   map.put("巫", "W");
	   map.put("吴", "W");
	   map.put("伍", "W");
	   map.put("武", "W");
	   /**
		* 首字母为X的姓氏
		*/
	   map.put("郗", "X");
	   map.put("奚", "X");
	   map.put("西", "X");
	   map.put("习", "X");
	   map.put("席", "X");
	   map.put("夏", "X");
	   map.put("鲜", "X");
	   map.put("咸", "X");
	   map.put("相", "X");
	   map.put("向", "X");
	   map.put("项", "X");
	   map.put("萧", "X");
	   map.put("谢", "X");
	   map.put("解", "X");
	   map.put("幸", "X");
	   map.put("邢", "X");
	   map.put("熊", "X");
	   map.put("胥", "X");
	   map.put("须", "X");
	   map.put("徐", "X");
	   map.put("许", "X");
	   map.put("轩", "X");
	   map.put("宣", "X");
	   map.put("薛", "X");
	   map.put("荀", "X");
	   /**
		* 首字母为Y的姓氏
		*/
	   map.put("闫", "Y");
	   map.put("严", "Y");
	   map.put("阎", "Y");
	   map.put("颜", "Y");
	   map.put("晏", "Y");
	   map.put("燕", "Y");
	   map.put("羊", "Y");
	   map.put("阳", "Y");
	   map.put("杨", "Y");
	   map.put("仰", "Y");
	   map.put("养", "Y");
	   map.put("姚", "Y");
	   map.put("叶", "Y");
	   map.put("伊", "Y");
	   map.put("易", "Y");
	   map.put("益", "Y");
	   map.put("羿", "Y");
	   map.put("阴", "Y");
	   map.put("殷", "Y");
	   map.put("尹", "Y");
	   map.put("印", "Y");
	   map.put("应", "Y");
	   map.put("雍", "Y");
	   map.put("尤", "Y");
	   map.put("游", "Y");
	   map.put("有", "Y");
	   map.put("于", "Y");
	   map.put("余", "Y");
	   map.put("於", "Y");
	   map.put("鱼", "Y");
	   map.put("俞", "Y");
	   map.put("喻", "Y");
	   map.put("虞", "Y");
	   map.put("宇", "Y");
	   map.put("禹", "Y");
	   map.put("郁", "Y");
	   map.put("尉", "Y");
	   map.put("元", "Y");
	   map.put("袁", "Y");
	   map.put("岳", "Y");
	   map.put("越", "Y");
	   map.put("乐", "Y");
	   map.put("云", "Y");
	   map.put("原", "Y");
	   /**
		* 首字母为Z的姓氏
		*/
	   map.put("赵", "Z");
	   map.put("宰", "Z");
	   map.put("昝", "Z");
	   map.put("臧", "Z");
	   map.put("曾", "Z");
	   map.put("翟", "Z");
	   map.put("詹", "Z");
	   map.put("湛", "Z");
	   map.put("张", "Z");
	   map.put("章", "Z");
	   map.put("仉", "Z");
	   map.put("查", "Z");
	   map.put("长", "Z");
	   map.put("甄", "Z");
	   map.put("郑", "Z");
	   map.put("支", "Z");
	   map.put("终", "Z");
	   map.put("钟", "Z");
	   map.put("钟", "Z");
	   map.put("仲", "Z");
	   map.put("周", "Z");
	   map.put("朱", "Z");
	   map.put("诸", "Z");
	   map.put("竺", "Z");
	   map.put("祝", "Z");
	   map.put("颛", "Z");
	   map.put("庄", "Z");
	   map.put("卓", "Z");
	   map.put("子", "Z");
	   map.put("訾", "Z");
	   map.put("宗", "Z");
	   map.put("邹", "Z");
	   map.put("祖", "Z");
	   map.put("左", "Z");
   }
   
   /**
    * 根据姓获取去姓的首字母
    * @param name
    * @return
    */
   public static String getInitial(String name){
	   Map<String, Object> resultMap = map;
	   if(name == null || name.equals("")){
		   return "";
	   }
	   if(resultMap.get(name) == null || resultMap.get(name).equals("")){
		   return "";
	   }
	   return (String) resultMap.get(name);
   }
}

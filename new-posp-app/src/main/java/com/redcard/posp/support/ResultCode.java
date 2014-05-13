package com.redcard.posp.support;

public enum ResultCode {

	RESULT_CODE_00("00", "承兑或交易成功"),
	/*RESULT_CODE_01("01", "查发卡行"),
	RESULT_CODE_02("03", "无效商户"),
	RESULT_CODE_04("04", "没收卡"),
	RESULT_CODE_05("05", "身份认证失败"),
	RESULT_CODE_10("10", "部分承兑"),
	RESULT_CODE_11("11", "重要人物批准"),
	RESULT_CODE_12("12", "无效的关联交易"),
	RESULT_CODE_13("13", "无效的金额"),
	RESULT_CODE_14("14", "无效的卡号"),
	RESULT_CODE_15("15", "无此发卡方"),
	RESULT_CODE_21("21", "卡未初始化"),
	RESULT_CODE_22("22", "故障怀疑，关联交易错误"),
	RESULT_CODE_25("25", "找不到原始交易"),
	RESULT_CODE_30("30", "报文格式错误"),
	RESULT_CODE_34("34", "有作弊嫌疑"),
	RESULT_CODE_38("38", "超过允许的PIN试输入"),
	RESULT_CODE_40("40", "请求的功能尚不支持"),
	RESULT_CODE_41("41", "挂失卡"),
	RESULT_CODE_43("43", "被窃卡"),*/
	RESULT_CODE_51("51", "余额不足"),
	RESULT_CODE_52("52", "积分不足"),
	RESULT_CODE_53("53", "发卡方无此商户"),
	RESULT_CODE_54("54", "发卡方无此终端号"),
	RESULT_CODE_55("55", "密码错请重输"),
	RESULT_CODE_56("56", "发卡方终端未签到"),
	RESULT_CODE_57("57", "交易未能处理"),
	RESULT_CODE_58("58", "非法交易"),
	RESULT_CODE_59("59", "商户状态错"),
	RESULT_CODE_60("60", "非本系统卡"),
	RESULT_CODE_61("61", "过期卡"),
	RESULT_CODE_62("62", "没收卡"),
	RESULT_CODE_63("63", "无效卡号"),
	RESULT_CODE_64("64", "非本地卡"),
	RESULT_CODE_65("65", "卡号校验错"),
	RESULT_CODE_66("66", "该卡不存在"),
	RESULT_CODE_67("67", "止付卡"),
	RESULT_CODE_68("68", "该卡已被换"),
	RESULT_CODE_70("70", "交易记录未找到"),
	RESULT_CODE_71("71", "不可退货"),
	RESULT_CODE_72("72", "授权号错"),
	RESULT_CODE_73("73", "金额不匹配"),
	RESULT_CODE_74("74", "无效金额"),
	RESULT_CODE_75("75", "流水号错"),
	RESULT_CODE_76("76", "原交易已撤销"),
	RESULT_CODE_77("77", "发卡方报文格式错"),
	RESULT_CODE_78("78", "批次号错"),
	RESULT_CODE_79("79", "发卡方终端无此交易权限"),
	RESULT_CODE_81("81", "该卡不支持此功能"),
	RESULT_CODE_82("82", "密码错误次数超限"),
	RESULT_CODE_90("90", "数据库操作失败"),
	RESULT_CODE_91("91", "系统忙，请稍后再试"),
	RESULT_CODE_92("92", "加密机系统故障"),
	RESULT_CODE_93("93", "发卡方主机连接失败"),
	RESULT_CODE_94("94", "系统故障"),
	RESULT_CODE_95("95", "其它故障"),
	/*RESULT_CODE_96("96", "银联处理中心系统异常,失效"),
	RESULT_CODE_97("97", "POS终端号找不到"),
	RESULT_CODE_98("98", "银联处理中心收不到发卡方应答"),
	RESULT_CODE_99("99", "PIN格式错"),
	RESULT_CODE_A0("A0", "MAC鉴别失败"),
	RESULT_CODE_A1("A1", "转账货币不一致"),
	RESULT_CODE_A2("A2", "有缺陷的成功"),
	RESULT_CODE_A3("A3", "资金到账行无此账户"),
	RESULT_CODE_A4("A4", "有缺陷的成功"),
	RESULT_CODE_A5("A5", "有缺陷的成功"),
	RESULT_CODE_A6("A6", "有缺陷的成功"),
	RESULT_CODE_A7("A7", "完全处理失败"),*/
	
	
	POSP_RESULT_CODE_0000("0000","成功"),
	POSP_RESULT_CODE_9996("9996","未在配置文件配置交易类型"),
	POSP_RESULT_CODE_9997("9997","载入配置文件出错"),
	POSP_RESULT_CODE_9998("9998","系统未知状态"),
	POSP_RESULT_CODE_9999("9999","系统错误");
	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private ResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}

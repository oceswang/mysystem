package com.github.event.constants;

public enum EventStatus
{
	NEW, //Producer方新建
	PUBLISHED, //已发布到broker
	RECEIVED, //Consumer已接收
	IGNORED, //无响应业务时忽略消息
	PROCESSED //消息已被处理
}

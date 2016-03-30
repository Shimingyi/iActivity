package com.me.Activity.dto;

public class Message {

	private Integer errcode;
	private String errmsg;

	public Message() {

	}

	public Message(int code, String msg) {
		errcode = code;
		errmsg = msg;
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}

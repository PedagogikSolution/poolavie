package com.pedagogiksolution.model;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.datastorebeans.Pool;

public class TradeModel {

	HttpServletRequest req;
	Pool mBeanPool;
	
	
	public TradeModel(HttpServletRequest req, Pool mBean) {
		this.req=req;
		this.mBeanPool=mBean;
		
	}


	public Boolean getTradeOffer() {
		return null;
		// TODO Auto-generated method stub
		
	}


	public Boolean checkIfTradeOpen() {
		// TODO Auto-generated method stub
		return null;
	}

}

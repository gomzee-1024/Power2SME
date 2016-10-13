package com.power2sme.android.sections.chat;

import java.util.List;

public class CardsResponse {
	private int totalRecords;
	private List<CardsInfo> cardsInfo;
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<CardsInfo> getCardsInfo() {
		return cardsInfo;
	}
	public void setCardsInfo(List<CardsInfo> cardsInfo) {
		this.cardsInfo = cardsInfo;
	}
}

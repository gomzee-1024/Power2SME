package com.power2sme.android.sections.chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardsResponse {
	private int totalRecords;
	private List<CardsInfo> cardsInfo;
	private Set<String> processedSkus;
	
	public CardsResponse(){
		totalRecords=0;
		cardsInfo=new ArrayList<>();
		processedSkus=new HashSet<>();
	}
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
	/**
	 * @return the processedSkus
	 */
	public Set<String> getProcessedSkus() {
		return processedSkus;
	}
	/**
	 * @param processedSkus the processedSkus to set
	 */
	public void setProcessedSkus(Set<String> processedSkus) {
		this.processedSkus = processedSkus;
	}
}

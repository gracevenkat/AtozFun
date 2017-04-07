package com.alphabet.atozfun;

import java.io.Serializable;

class MyBean implements Serializable {
	int title;
	int image_resource;
	int freeImage;
	int premiumImage;
	int forFree;
	int forPremium;
	
	public MyBean(int title, int image_resource, int freeImage, int premiumImage, int forFree, int forPremium) {
		super();
		this.title = title;
		this.image_resource = image_resource;
		this.freeImage = freeImage;
		this.premiumImage = premiumImage;
		this.forFree = forFree;
		this.forPremium = forPremium;
	}
}

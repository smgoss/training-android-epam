package com.epam.android.demo.common.http.cookie;

import org.apache.http.cookie.Cookie;

public class SavedCookies {

	private Cookie cookie;
	
	public SavedCookies(Cookie cookie){
		this.cookie = cookie;
	}
	
	public Cookie getCookie(){
		return cookie;
	}
	
	public void setCookie(Cookie cookie){
		this.cookie = cookie;
	}
}

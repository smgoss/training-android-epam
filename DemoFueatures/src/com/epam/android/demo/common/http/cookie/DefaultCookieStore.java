package com.epam.android.demo.common.http.cookie;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class DefaultCookieStore implements CookieStore{

	private CookieManager cookieManager;
	
	public DefaultCookieStore(CookieManager cookieManager){
		this.cookieManager = cookieManager;
	}

	@Override
	public void addCookie(Cookie cookie) {
		SavedCookies pCookie = cookieManager.getCookie(cookie.getName());
		
		if(pCookie == null){
			cookieManager.addCookie(new SavedCookies(cookie));
		}
		else{
			pCookie.setCookie(cookie);
			cookieManager.updateCookie(pCookie);
		}
		
		
	}

	@Override
	public void clear() {
		cookieManager.clear();
		
	}

	@Override
	public boolean clearExpired(Date date) {
		if (cookieManager.clearExpired(date) == 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Cookie> getCookies() {
		List<SavedCookies> savedCookies = cookieManager.getCookies();
		List<Cookie> resultCookies = new ArrayList<Cookie>();
		if (savedCookies != null) {
			Iterator<SavedCookies> iterator = savedCookies.iterator();
			while(iterator.hasNext()){
				resultCookies.add(iterator.next().getCookie());
			}
		}
		
		return resultCookies;
	}
	
	


}

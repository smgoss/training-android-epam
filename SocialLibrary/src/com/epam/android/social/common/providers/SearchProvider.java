package com.epam.android.social.common.providers;

import android.content.SearchRecentSuggestionsProvider;

public class SearchProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = SearchProvider.class.getName();
	
	public final static int MODE = DATABASE_MODE_QUERIES;
	

	public SearchProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}
	
	
}

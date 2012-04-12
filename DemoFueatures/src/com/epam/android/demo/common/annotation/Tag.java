package com.epam.android.demo.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
public @interface Tag {
	String[] keys();
	JSON [] types();
}

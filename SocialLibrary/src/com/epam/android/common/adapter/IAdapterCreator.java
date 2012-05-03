package com.epam.android.common.adapter;

import java.util.List;

import android.content.Context;

import com.epam.android.common.model.BaseModel;

public interface IAdapterCreator<B> {
	B create(Context c, int pItemResource, List<? extends BaseModel> pList);
}

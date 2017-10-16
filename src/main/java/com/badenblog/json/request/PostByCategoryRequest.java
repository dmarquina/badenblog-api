package com.badenblog.json.request;

import java.util.List;

public class PostByCategoryRequest {
	List<Integer> idsCategories;

	public List<Integer> getIdsCategories() {
		return idsCategories;
	}

	public void setIdsCategories(List<Integer> idsCategories) {
		this.idsCategories = idsCategories;
	}

}

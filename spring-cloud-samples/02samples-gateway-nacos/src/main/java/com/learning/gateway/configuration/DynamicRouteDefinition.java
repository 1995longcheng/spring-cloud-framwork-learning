package com.learning.gateway.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 由于FASTJSON无法直接转换复杂对象，因此需要自定义简单对象
 * 
 * @author xiongchaoqun
 * @date 2019年7月24日
 */
public class DynamicRouteDefinition {
	private String id;
	private List<DynamicPredicateDefinition> predicates = new ArrayList<>();
	private List<DynamicFilterDefinition> filters = new ArrayList<>();
	private String uri;
	private int order = 0;
	
	public List<DynamicPredicateDefinition> getPredicates() {
		return predicates;
	}
	public void setPredicates(List<DynamicPredicateDefinition> predicates) {
		this.predicates = predicates;
	}
	public List<DynamicFilterDefinition> getFilters() {
		return filters;
	}
	public void setFilters(List<DynamicFilterDefinition> filters) {
		this.filters = filters;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}

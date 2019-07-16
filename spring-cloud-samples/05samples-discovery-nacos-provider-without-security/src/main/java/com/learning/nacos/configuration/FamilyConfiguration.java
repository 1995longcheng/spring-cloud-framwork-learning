package com.learning.nacos.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="family")
public class FamilyConfiguration {
	private Person father;
	private Person mother;
	private List<Person> children;

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}
	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
	}

	public List<Person> getChildren() {
		return children;
	}

	public void setChildren(List<Person> children) {
		this.children = children;
	}

	public static class Person {
		private String name;
		private int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}
}

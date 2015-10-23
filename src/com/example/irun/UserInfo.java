package com.example.irun;

//用户信息
public class UserInfo {
	
	private static String id;//账户
	private static float step;//步数
	private static float distance;//距离
	private static float calories;//热量
	
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		UserInfo.id = id;
	}
	public static float getStep() {
		return step;
	}
	public static void setStep(float step) {
		UserInfo.step = step;
	}
	public static float getDistance() {
		return distance;
	}
	public static void setDistance(float distance) {
		UserInfo.distance = distance;
	}
	public static float getCalories() {
		return calories;
	}
	public static void setCalories(float calories) {
		UserInfo.calories = calories;
	}
}

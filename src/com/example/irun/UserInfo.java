package com.example.irun;

//�û���Ϣ
public class UserInfo {
	
	private static String id;//�˻�
	private static float step;//����
	private static float distance;//����
	private static float calories;//����
	
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

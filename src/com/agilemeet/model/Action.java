package com.agilemeet.model;

public class Action {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Action(int id, String task, int completed) {
		super();
		this.id = id;
		this.task = task;
		this.completed = completed;
	}
	private String task;
	private int completed = 0;
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	@Override
	public String toString() {
		return "Action [task=" + task + ", completed=" + completed + "]";
	}
	public Action(String task, int completed) {
		super();
		this.task = task;
		this.completed = completed;
	}
	
}

package com.agilemeet.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailThread implements Runnable {
	private String task;
	private String title;
	private String emailId;
    private String replyMail;
    private Logger logger = LoggerFactory.getLogger(MailThread.class);
    
    public MailThread(String task, String title, String emailId, String replyMail){
        this.task = task;
        this.title = title;
        this.emailId = emailId;
        this.replyMail = replyMail;
    }

    @Override
    public void run() {
        logger.info(Thread.currentThread().getName()+" Started");
        processCommand();
        logger.info(Thread.currentThread().getName()+" Ended");
    }

    private void processCommand() {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("Hi,\n  You have been assigned a task in Agile Meet application. PLease find details below.\n\n");
            builder.append("Task : " + task.toString());
            builder.append("\nMeeting : " + title.toString());
            builder.append("\nReply to : " + replyMail.toString());
            builder.append("\n\nRegards,\nTeam AgileMeet");
            System.out.println(builder.toString());
            SendMail.sendMail(this.emailId, builder.toString());
        } catch (Exception e) {
            logger.error("MAIL NOT SENT : ", e.getCause());
        }
    }

	@Override
	public String toString() {
		return "MailThread [task=" + task + ", title=" + title + ", emailId=" + emailId + "]";
	}

}

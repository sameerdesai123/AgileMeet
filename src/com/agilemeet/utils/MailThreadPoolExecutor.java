package com.agilemeet.utils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailThreadPoolExecutor {
	
	public static void runPool(String title,String orgMail, List<String> emails, List<String> tasks) {
		Logger logger = LoggerFactory.getLogger(MailThreadPoolExecutor.class);
		try{
			System.out.println("Emails:" + emails.toString());
        	ExecutorService executor = Executors.newFixedThreadPool(emails.size());
            for (int i = 0; i < emails.size(); i++) {
                Runnable worker = new MailThread(tasks.get(i), title, emails.get(i), orgMail);
                executor.execute(worker);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            logger.info("Sent All Mails");
        }catch(Exception e){
        	logger.error("Not All Mails were sent : ", e.getCause());
        }
    }

	
}

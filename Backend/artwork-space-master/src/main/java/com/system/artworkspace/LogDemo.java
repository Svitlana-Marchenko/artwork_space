package com.system.artworkspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDemo {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LogDemo.class);
        logger.info("Це приклад логування одного разу.");

        String currentWorkingDirectory = System.getProperty("user.dir");
        System.out.println("Поточна робоча директорія: " + currentWorkingDirectory);

    }
}


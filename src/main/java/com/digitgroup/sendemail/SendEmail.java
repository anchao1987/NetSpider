/**
 * 
 */
package com.digitgroup.sendemail;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailConstants;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;

/**
 * @author Moking
 *
 */
public class SendEmail {
	/**
	 * Log
	 */
	private static final Logger log = Logger.getLogger(SendEmail.class);

	/**
	 * 发送带附件的邮件
	 * 
	 * @author Moking
	 * @since 1.0
	 */
	public void sendAttachment(EmailAttachment attachment, Configuration config) {

		try {
			MultiPartEmail email = new MultiPartEmail();
			email.setHostName(config.getString("hostName"));
			email.setSslSmtpPort(config.getString("sslSmtpPort"));
			// 是否启用ssl
			// email.setSSLOnConnect(config.getBoolean("ssl"));
			email.setFrom(config.getString("sendFrom"));
			email.addTo(config.getStringArray("addTo"));
			email.setAuthentication(config.getString("userName"),
					config.getString("password"));
			email.attach(attachment);
			email.setDebug(true);
			email.send();
			log.debug("send Email Success!");
		} catch (EmailException e) {
			log.error(e.toString() + e.getCause());
		}
	}
}

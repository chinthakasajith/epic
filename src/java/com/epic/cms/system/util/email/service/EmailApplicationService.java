/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.email.service;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AlertBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic.AlertMgtManager;
import com.epic.cms.admin.templatemgt.emailtemplate.bean.EmailBean;
import com.epic.cms.admin.templatemgt.emailtemplate.businesslogic.EmailConfMgtManager;
import com.epic.cms.system.util.email.bean.MSGSchedulerBean;
import com.epic.cms.system.util.email.businesslogic.MSGSchedulerManager;
import com.epic.cms.system.util.variable.EmailVarList;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author sajith_g
 */
public class EmailApplicationService {

    AlertBean alertBean = null;
    EmailBean emailBean = null;
    EmailBodyTransformService emailBodyTransformService = null;

    public Message setEmailReceipient(String recepientEmail, String emailTempalateCode) throws MessagingException, Exception {
        Session session;
        session = Session.getInstance(this.getEmailConf(emailTempalateCode),
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(alertBean.getSender(), alertBean.getPassword());
                    }
                });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(alertBean.getSender()));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepientEmail));
        return msg;

    }

    /**
     * to set the email configuration properties
     *
     * @param emailTemplateCode default : "Email"
     * @return Email Properties
     * @throws Exception
     */
    public Properties getEmailConf(String emailTemplateCode) throws Exception {

        AlertMgtManager alertMgtManager = new AlertMgtManager();
        alertBean = alertMgtManager.viewSelectedAlert(emailTemplateCode);
        Properties properties = new Properties();

        properties.put("mail.smtp.host", alertBean.getIp());
        properties.put("mail.smtp.socketFactory.port", alertBean.getPort());
        //properties.put("mail.smtp.protocol.","smtp");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", alertBean.getPort());
        //properties.put("mail.smtp.socketFactory.fallback", "false");

        return properties;
    }

    public Message setEmailTemplate(Message configuredMsg, String alertCode, String emailTemplateCode, String delimeter, Map<String, String> parameters) throws Exception {
        EmailConfMgtManager emailConfMgtManager = new EmailConfMgtManager();
        emailBean = emailConfMgtManager.viewSelectedEmailConf(emailTemplateCode);

        emailBodyTransformService = new EmailBodyTransformService();

        //set the message body
        if (delimeter != null && delimeter != "" && parameters != null) {

            String parameterAddedMessageBody = emailBodyTransformService.transformEmailBodyTokenToParamer(emailBean.getBody(), delimeter, parameters);
            configuredMsg.setContent(parameterAddedMessageBody, "text/html; charset=utf-8");

        } else {

            configuredMsg.setContent(emailBean.getBody(), "text/html; charset=utf-8");

        }

        //adding email subject
        configuredMsg.setSubject(emailBean.getSubject());

        return configuredMsg;

    }

    /**
     * this method used to send e mails
     *
     * @param recepientEmail Receivers email address
     * @param emailTemplateCode email template use to send email
     * @param delimeter use to tokenize the message body
     * @param parameters use to append value to email body parameters
     * @param emailCreator use to get last updated user for msgScheduler
     * @return if mail sent sucessfully then return true
     * @throws MessagingException
     * @throws Exception
     */
    public boolean sendEmail(String recepientEmail, String emailTemplateCode, String delimeter, Map<String, String> parameters, String emailCreator) throws Exception {

        int isAdd = 0;

        //commentted since email sending is now done by message notification module
        //Message recepientAddedMsg = this.setEmailReceipient(recepientEmail, "Email");
        //Message templateAddedMsg = this.setEmailTemplate(recepientAddedMsg, "Email", emailTemplateCode, delimeter, parameters);
        //sending the email
        //Transport.send(templateAddedMsg);
        EmailConfMgtManager emailConfMgtManager = new EmailConfMgtManager();
        emailBean = emailConfMgtManager.viewSelectedEmailConf(emailTemplateCode);

        emailBodyTransformService = new EmailBodyTransformService();

        String emailMessageBody = null;

        //set the message body
        if (delimeter != null && delimeter != "" && parameters != null) {

            String parameterAddedMessageBody = emailBodyTransformService.transformEmailBodyTokenToParamer(emailBean.getBody(), delimeter, parameters);
            emailMessageBody = parameterAddedMessageBody;

        } else {

            emailMessageBody = emailBean.getBody();

        }

        MSGSchedulerBean msgSchedulerBean = new MSGSchedulerBean();
        msgSchedulerBean.setStatus(EmailVarList.EMAIL_NOT_SENT);
        msgSchedulerBean.setAlertType("Email");
        msgSchedulerBean.setLastUpdatedUser(emailCreator);
        msgSchedulerBean.setMessageBody(emailMessageBody);
        msgSchedulerBean.setMessageTitle(emailBean.getSubject());
        msgSchedulerBean.setReceiverEmail(recepientEmail);

        //send email request to msg scheduller
        MSGSchedulerManager msgSchedulerManager = new MSGSchedulerManager();
        isAdd = msgSchedulerManager.insertMSGScheduler(msgSchedulerBean);

        if (isAdd == 1) {
            return true;
        } else {
            return false;
        }

    }

}

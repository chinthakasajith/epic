package com.epic.cms.backoffice.eodlettergeneration.bean;

/**
 *
 * @author jeevan
 */
public class LetterTemplateBean {
    private String templateCode;
    private String description;
    private String templateFormat;
    private String processCode;
    
    private String body;
    private String title;
    
    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplateFormat() {
        return templateFormat;
    }

    public void setTemplateFormat(String templateFormat) {
        this.templateFormat = templateFormat;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}

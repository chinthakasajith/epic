/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.bean;

/**
 *this class use to create bean that relate to score card
 * @author ayesh
 */
public class CardScoreBean {

    private String scoreCardCode;
    private String scoreCardName;
    private String product;
    private String[] rules;
    private String minScoreCard;
    private String maxScoreCard;
    private String status;
    private String lastUpdateUser;
    private String lastUpdateDate;
    private String createDate;
    private String cardTypeDes;
    private String cardType;

    private String statusCode;
    
    public String getCardTypeDes() {
        return cardTypeDes;
    }

    public void setCardTypeDes(String cardTypeDes) {
        this.cardTypeDes = cardTypeDes;
    }

    /**
     * get maximum score card 
     * @return - String
     */
    public String getMaxScoreCard() {
        return maxScoreCard;
    }

    /**
     * set maximum score card
     * @param maxScoreCard -String
     */
    public void setMaxScoreCard(String maxScoreCard) {
        this.maxScoreCard = maxScoreCard;
    }

    /**
     * get minimum score card 
     * @return String
     */
    public String getMinScoreCard() {
        return minScoreCard;
    }

    /**
     * set minimum score card
     * @param minScoreCard 
     */
    public void setMinScoreCard(String minScoreCard) {
        this.minScoreCard = minScoreCard;
    }

    /**
     * get product
     * @return String
     */
    public String getProduct() {
        return product;
    }

    /**
     * set product
     * @param product 
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * get rules
     * @return String[]
     */
    public String[] getRules() {
        return rules;
    }

    /**
     * set rules
     * @param rules-String[] 
     */
    public void setRules(String[] rules) {
        this.rules = rules;
    }

    /**
     * get score card code
     * @return -String
     */
    public String getScoreCardCode() {
        return scoreCardCode;
    }

    /**
     * set score card code
     * @param scoreCardCode 
     */
    public void setScoreCardCode(String scoreCardCode) {
        this.scoreCardCode = scoreCardCode;
    }

    /**
     * get score card name
     * @return String
     */
    public String getScoreCardName() {
        return scoreCardName;
    }

    /**
     * set score card name
     * @param scoreCardName 
     */
    public void setScoreCardName(String scoreCardName) {
        this.scoreCardName = scoreCardName;
    }

    /**
     * get status
     * @return - String
     */
    public String getStatus() {
        return status;
    }

    /**
     * set status
     * @param status 
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * get create date
     * @return - String 
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * set create date
     * @param createDate 
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * get last update date
     * @return 
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * set last update date
     * @param lastUpdateDate 
     */
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * get last update user
     * @return 
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * set last update user
     * @param lastUpdateUser 
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    
    
    
}

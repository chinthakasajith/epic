/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.bean;

/**
 *this bean class use to store already assigned rules
 * @author ayesh
 */
public class ScoreCardAssignRuleBean {

    private String scoreCardCode;
    private String ruleCode;

    /**
     * get rule code
     * @return  String
     */
    public String getRuleCode() {
        return ruleCode;
    }

    /**
     * set rule code
     * @param ruleCode 
     */
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    /**
     * get score card code
     * @return  String
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
}

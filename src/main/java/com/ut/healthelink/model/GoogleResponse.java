/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ut.healthelink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author chadmccue
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "success",
    "score", 
    "action",
    "challenge_ts",
    "hostname",
    "error-codes"
})
public class GoogleResponse {
    
    @JsonProperty("success")
    private boolean success;
     
    @JsonProperty("challenge_ts")
    private String challengeTs;
     
    @JsonProperty("hostname")
    private String hostname;
     
    @JsonProperty("score")
    private float score;
    
    @JsonProperty("action")
    private String action;

    public boolean isSuccess() {
	return success;
    }

    public void setSuccess(boolean success) {
	this.success = success;
    }

    public String getChallengeTs() {
	return challengeTs;
    }

    public void setChallengeTs(String challengeTs) {
	this.challengeTs = challengeTs;
    }

    public String getHostname() {
	return hostname;
    }

    public void setHostname(String hostname) {
	this.hostname = hostname;
    }

    public float getScore() {
	return score;
    }

    public void setScore(float score) {
	this.score = score;
    }

    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }
    
    
    
}

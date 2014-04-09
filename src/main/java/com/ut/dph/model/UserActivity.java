/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ut.dph.model;

import com.ut.dph.validator.NoHtml;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "userActivity")
public class UserActivity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "userId", nullable = false)
    private int userId = 0;
	
	@NoHtml
    @Column(name = "batchId", nullable = true)
    private Integer batchId;
	
	@NoHtml
	@Column(name = "accessMethod", nullable = true)
    private String accessMethod;
	
	@NoHtml
	@Column(name = "pageAccess", nullable = true)
    private String pageAccess;

    
	@NoHtml
	@Column(name = "transactionInIds", nullable = true)
    private String transactionInIds;
    
    @NoHtml
    @Column(name = "transactionTargetIds", nullable = true)
    private String transactionTargetIds;
    
    @NoHtml
    @Column(name = "activity", nullable = true)
    private String activity;
    
    @NoHtml
    @Column(name = "activityDesc", nullable = true)
    private String activityDesc;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = false)
    private Date dateCreated = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAccessMethod() {
		return accessMethod;
	}

	public void setAccessMethod(String accessMethod) {
		this.accessMethod = accessMethod;
	}

	public String getPageAccess() {
		return pageAccess;
	}

	public void setPageAccess(String pageAccess) {
		this.pageAccess = pageAccess;
	}

	public String getTransactionInIds() {
		return transactionInIds;
	}

	public void setTransactionInIds(String transactionInIds) {
		this.transactionInIds = transactionInIds;
	}

	public String getTransactionTargetIds() {
		return transactionTargetIds;
	}

	public void setTransactionTargetIds(String transactionTargetIds) {
		this.transactionTargetIds = transactionTargetIds;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	
    
}

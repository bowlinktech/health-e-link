/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ut.dph.model;

import java.util.List;

/**
 *
 * @author chadmccue
 */
public class transactionRecords {
    
    private String fieldValue = null;
    private String saveToTable = null;
    private String saveToTableCol = null;
    private int fieldNo;
    private boolean required = true;
    private String validation = null;
    private String fieldLabel = null;
    private int transactionId; 
   
    public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	private List<fieldSelectOptions> fieldSelectOptions = null;
    
    public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getfieldValue() {
        return fieldValue;
    }
    
    public void setfieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
    
    public String getsaveToTable() {
        return saveToTable;
    }
    
    public void setsaveToTable(String saveToTable) {
        this.saveToTable = saveToTable;
    }
    
    public String getsaveToTableCol() {
        return saveToTableCol;
    }
    
    public void setsaveToTableCol(String saveToTableCol) {
        this.saveToTableCol = saveToTableCol;
    }
    
    public int getfieldNo() {
        return fieldNo;
    }
    
    public void setfieldNo(int fieldNo) {
        this.fieldNo = fieldNo;
    }
    
    public boolean getrequired() {
        return required;
    }
    
    public void setrequired(boolean required) {
        this.required = required;
    }
    
    public void setvalidation(String validation) {
        this.validation = validation;
    }
    
    public String getvalidation() {
        return validation;
    }
    
    public void setfieldSelectOptions(List<fieldSelectOptions> fieldSelectOptions) {
        this.fieldSelectOptions = fieldSelectOptions;
    }
    
    public List<fieldSelectOptions> getfieldSelectOptions() {
       return fieldSelectOptions;
    }
    
    public void setfieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }
    
    public String getfieldLabel() {
        return fieldLabel;
    }
    
}

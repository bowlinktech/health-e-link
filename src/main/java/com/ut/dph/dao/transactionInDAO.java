/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ut.dph.dao;

import com.ut.dph.model.batchUploads;
import com.ut.dph.model.fieldSelectOptions;
import com.ut.dph.model.transactionIn;
import com.ut.dph.model.transactionInRecords;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface transactionInDAO {
    
    String getFieldValue(String tableName, String tableCol, int idValue);
    
    List<fieldSelectOptions> getFieldSelectOptions(int fieldId, int configId);
    
    Integer submitBatchUpload(batchUploads batchUpload);
    
    Integer submitTransactionIn(transactionIn transactionIn);
    
    Integer submitTransactionInRecords(transactionInRecords records);
    
    void submitTransactionTranslatedInRecords(int transactionRecordId);
    
    List<transactionIn> getpendingTransactions(int orgId);
    
    batchUploads getUploadBatch(int batchId);
    
}

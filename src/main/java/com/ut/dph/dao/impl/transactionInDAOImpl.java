/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ut.dph.dao.impl;

import com.ut.dph.dao.transactionInDAO;
import com.ut.dph.model.batchUploadSummary;
import com.ut.dph.model.batchUploads;
import com.ut.dph.model.configuration;
import com.ut.dph.model.configurationConnection;
import com.ut.dph.model.configurationConnectionSenders;
import com.ut.dph.model.fieldSelectOptions;
import com.ut.dph.model.transactionAttachment;
import com.ut.dph.model.transactionIn;
import com.ut.dph.model.transactionInRecords;
import com.ut.dph.model.transactionTarget;
import com.ut.dph.model.custom.ConfigForInsert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class transactionInDAOImpl implements transactionInDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private String schemaName = "universalTranslator";
    
    /**
     * The 'getFieldValue' function will return the value saved for the passed in tableName, tableCol and idValue.
     *
     * @param	tableName	The name of the table to query
     * @param tableCol The column name of to return
     * @param idValue The id value of the row to search
     *
     * @return The function will return a String
     */
    @Override
    public String getFieldValue(String tableName, String tableCol, int idValue) {

        String sql = ("select " + tableCol + " from " + tableName + " where id = :id");

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", idValue);

        String tableValue = (String) query.uniqueResult();

        return tableValue;

    }

    /**
     * The 'getFieldSelectOptions' function will return a list of values to populate the field select box.
     *
     * @param fieldId The fieldId to search on
     * @param configId The configuration Id to search on
     *
     * @return The function will return a list of select box options
     */
    @Override
    public List<fieldSelectOptions> getFieldSelectOptions(int fieldId, int configId) {

        List<fieldSelectOptions> fieldSelectOptions = new ArrayList<fieldSelectOptions>();

        Query findCrosswalks = sessionFactory.getCurrentSession().createSQLQuery("SELECT crosswalkId, id FROM configurationDataTranslations where configId = :configId and fieldId = :fieldId");
        findCrosswalks.setParameter("configId", configId);
        findCrosswalks.setParameter("fieldId", fieldId);
        List crosswalks = findCrosswalks.list();

        Iterator it = crosswalks.iterator();
        int crosswalkId;
        String optionDesc;
        String optionValue;
        fieldSelectOptions fieldOptions = null;

        while (it.hasNext()) {
            Object row[] = (Object[]) it.next();
            crosswalkId = (Integer) row[0];

            Query crosswalkData = sessionFactory.getCurrentSession().createSQLQuery("SELECT sourceValue, descValue FROM rel_crosswalkData where crosswalkId = :crosswalkId");
            crosswalkData.setParameter("crosswalkId", crosswalkId);
            List crosswalkDataList = crosswalkData.list();

            Iterator cwDataIt = crosswalkDataList.iterator();
            while (cwDataIt.hasNext()) {
                Object cwDatarow[] = (Object[]) cwDataIt.next();
                optionDesc = (String) cwDatarow[1];
                optionValue = (String) cwDatarow[0];

                fieldOptions = new fieldSelectOptions();
                fieldOptions.setoptionDesc(optionDesc);
                fieldOptions.setoptionValue(optionValue);
                fieldSelectOptions.add(fieldOptions);
            }

        }

        return fieldSelectOptions;
    }

    /**
     * The 'submitBatchUpload' function will submit the new batch.
     *
     * @param batchUpload The object that will hold the new batch info
     *
     * @table batchUploads
     *
     * @return This function returns the batchId for the newly inserted batch
     */
    @Override
    @Transactional
    public Integer submitBatchUpload(batchUploads batchUpload) {

        Integer batchId = null;

        batchId = (Integer) sessionFactory.getCurrentSession().save(batchUpload);

        return batchId;

    }

    /**
     * The 'submitBatchUploadSummary' function will submit an entry that will contain specific information for transactions within the submitted batch. This will be used when trying to find out which batches a user has access to when logged into the ERG.
     *
     * @param summary The object that will hold the batch summary information
     *
     * @table batchUploadSummary
     *
     * @return This function does not return anything.
     */
    @Override
    @Transactional
    public void submitBatchUploadSummary(batchUploadSummary summary) {
        sessionFactory.getCurrentSession().save(summary);
    }

    /**
     * The 'submitBatchUploadChanges' function will submit the batch changes.
     *
     * @param batchUpload The object that will hold the new batch info
     *
     * @table batchUploads
     *
     * @return This function does not return anything
     */
    @Override
    @Transactional
    public void submitBatchUploadChanges(batchUploads batchUpload) {
        sessionFactory.getCurrentSession().update(batchUpload);
    }

    /**
     * The 'submitTransactionIn' function will submit the new transaction for the batch.
     *
     * @param transactionIn The object that will hold the new transaction info
     *
     * @table transactionIn
     *
     * @return This function returns the transactioInId for the newly inserted transaction
     */
    @Override
    @Transactional
    public Integer submitTransactionIn(transactionIn transactionIn) {
        Integer transactioInId = null;

        transactioInId = (Integer) sessionFactory.getCurrentSession().save(transactionIn);

        return transactioInId;
    }

    /**
     * The 'submitTransactionInChanges' function will submit the transaction changes for the batch.
     *
     * @param transactionIn The object that will hold the new transaction info
     *
     * @table transactionIn
     *
     * @return This function does not return anything
     */
    @Override
    @Transactional
    public void submitTransactionInChanges(transactionIn transactionIn) {
        sessionFactory.getCurrentSession().update(transactionIn);
    }

    /**
     * The 'submitTransactionInRecords' function will submit the transaction records
     *
     * @param records The object that will hold the transaction record info
     *
     * @table transactionInRecords
     *
     * @return This function will return the generated transaction record id
     */
    @Override
    @Transactional
    public Integer submitTransactionInRecords(transactionInRecords records) {
        Integer transactioInRecordId = null;

        transactioInRecordId = (Integer) sessionFactory.getCurrentSession().save(records);

        return transactioInRecordId;
    }

    /**
     * The 'submitTransactionInRecordsUpdates' function will submit the transaction record changes for the batch
     *
     * @param records The object that will hold the transaction record info
     *
     * @table transactionInRecords
     *
     * @return This function does not return anything
     */
    @Override
    @Transactional
    public void submitTransactionInRecordsUpdates(transactionInRecords records) {
        sessionFactory.getCurrentSession().update(records);
    }

    /**
     * The 'submitTransactionTranslatedInRecords' function will copy the records from the transactionInRecords table to the transactionTranslatedIn table so the processing can begin on the field values.
     *
     * @param transactionRecordId The id of the inserted record in the transactionInRecords table
     *
     * @return This function does not return anything.
     */
    @Override
    @Transactional
    public void submitTransactionTranslatedInRecords(int transactionInId, int transactionRecordId, int configId) {

        /* Always clear this table out for the passed in transactionId */
        Query clearRecords = sessionFactory.getCurrentSession().createSQLQuery("DELETE from transactionTranslatedIn where transactionInId = :transactionInId");
        clearRecords.setParameter("transactionInId", transactionInId);
        clearRecords.executeUpdate();

        Query query = sessionFactory.getCurrentSession().createSQLQuery("INSERT INTO transactionTranslatedIn ("
                + "transactionInId, configId, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22, f23, f24, f25, f26, f27, f28, f29, f30, f31,"
                + "f32, f33, f34, f35, f36, f37, f38, f39, f40, f41, f42, f43, f44, f45, f46, f47, f48, f49, f50, f51, f52, f53, f54, f55, f56, f57, f58, f59, f60, f61, f62, f63, f64,"
                + "f65, f66, f67, f68, f69, f70, f71, f72, f73, f74, f75, f76, f77, f78, f79, f80, f81, f82, f83, f84, f85, f86, f87, f88, f89, f90, f91, f92, f93, f94, f95, f96, f97, f98,"
                + "f99, f100, f101, f102, f103, f104, f105, f106, f107, f108, f109, f110, f111, f112, f113, f114, f115, f116, f117, f118, f119, f120, f121, f122, f123, f124, f125, f126, f127, f128, f129,"
                + "f130, f131, f132, f133, f134, f135, f136, f137, f138, f139, f140, f141, f142, f143, f144, f145, f146, f147, f148, f149, f150, f151, f152, f153, f154, f155, f156, f157, f158, f159,"
                + "f160, f161, f162, f163, f164, f165, f166, f167, f168, f169, f170, f171, f172, f173, f174, f175, f176, f177, f178, f179, f180, f181, f182, f183, f184, f185, f186, f187, f188, f189,"
                + "f190, f191, f192, f193, f194, f195, f196, f197, f198, f199, f200, f201, f202, f203, f204, f205, f206, f207, f208, f209, f210, f211, f212, f213, f214, f215, f216, f217, f218, f219,"
                + "f220, f221, f222, f223, f224, f225, f226, f227, f228, f229, f230, f231, f232, f233, f234, f235, f236, f237, f238, f239, f240, f241, f242, f243, f244, f245, f246, f247, f248, f249,"
                + "f250, f251, f252, f253, f254, f255) "
                + "SELECT transactionInId, :configId, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22, f23, f24, f25, f26, f27, f28, f29, f30, f31,"
                + "f32, f33, f34, f35, f36, f37, f38, f39, f40, f41, f42, f43, f44, f45, f46, f47, f48, f49, f50, f51, f52, f53, f54, f55, f56, f57, f58, f59, f60, f61, f62, f63, f64,"
                + "f65, f66, f67, f68, f69, f70, f71, f72, f73, f74, f75, f76, f77, f78, f79, f80, f81, f82, f83, f84, f85, f86, f87, f88, f89, f90, f91, f92, f93, f94, f95, f96, f97, f98,"
                + "f99, f100, f101, f102, f103, f104, f105, f106, f107, f108, f109, f110, f111, f112, f113, f114, f115, f116, f117, f118, f119, f120, f121, f122, f123, f124, f125, f126, f127, f128, f129,"
                + "f130, f131, f132, f133, f134, f135, f136, f137, f138, f139, f140, f141, f142, f143, f144, f145, f146, f147, f148, f149, f150, f151, f152, f153, f154, f155, f156, f157, f158, f159,"
                + "f160, f161, f162, f163, f164, f165, f166, f167, f168, f169, f170, f171, f172, f173, f174, f175, f176, f177, f178, f179, f180, f181, f182, f183, f184, f185, f186, f187, f188, f189,"
                + "f190, f191, f192, f193, f194, f195, f196, f197, f198, f199, f200, f201, f202, f203, f204, f205, f206, f207, f208, f209, f210, f211, f212, f213, f214, f215, f216, f217, f218, f219,"
                + "f220, f221, f222, f223, f224, f225, f226, f227, f228, f229, f230, f231, f232, f233, f234, f235, f236, f237, f238, f239, f240, f241, f242, f243, f244, f245, f246, f247, f248, f249,"
                + "f250, f251, f252, f253, f254, f255 "
                + "FROM transactionInRecords where id = :id");
        query.setParameter("id", transactionRecordId);
        query.setParameter("configId", configId);
        query.executeUpdate();
    }

    /**
     * The 'getpendingBatches' function will return a list of batches that have a status of "Error" or "Release Pending". The batches returned will only be the ones the current user has access to viewing.
     *
     * @param userId The id of the logged in user trying to view pending batches
     * @param orgId The id of the organization the user belongs to
     *
     * @return The function will return a list of pending batches
     */
    @Override
    @Transactional
    @SuppressWarnings("UnusedAssignment")
    public List<batchUploads> getpendingBatches(int userId, int orgId) {

        /* Get a list of connections the user has access to */
        Criteria connections = sessionFactory.getCurrentSession().createCriteria(configurationConnectionSenders.class);
        connections.add(Restrictions.eq("userId", userId));
        List<configurationConnectionSenders> userConnections = connections.list();

        List<Integer> messageTypeList = new ArrayList<Integer>();
        List<Integer> targetOrgList = new ArrayList<Integer>();

        if (userConnections.isEmpty()) {
            messageTypeList.add(0);
            targetOrgList.add(0);
        } else {

            for (configurationConnectionSenders userConnection : userConnections) {
                Criteria connection = sessionFactory.getCurrentSession().createCriteria(configurationConnection.class);
                connection.add(Restrictions.eq("id", userConnection.getconnectionId()));

                configurationConnection connectionInfo = (configurationConnection) connection.uniqueResult();

                /* Get the message type for the configuration */
                Criteria sourceconfigurationQuery = sessionFactory.getCurrentSession().createCriteria(configuration.class);
                sourceconfigurationQuery.add(Restrictions.eq("id", connectionInfo.getsourceConfigId()));

                configuration configDetails = (configuration) sourceconfigurationQuery.uniqueResult();

                /* Add the message type to the message type list */
                messageTypeList.add(configDetails.getMessageTypeId());

                /* Get the list of target orgs */
                Criteria targetconfigurationQuery = sessionFactory.getCurrentSession().createCriteria(configuration.class);
                targetconfigurationQuery.add(Restrictions.eq("id", connectionInfo.gettargetConfigId()));
                configuration targetconfigDetails = (configuration) targetconfigurationQuery.uniqueResult();

                /* Add the target org to the target organization list */
                targetOrgList.add(targetconfigDetails.getorgId());
            }
        }

        /* Get a list of available batches */
        Criteria batchSummaries = sessionFactory.getCurrentSession().createCriteria(batchUploadSummary.class);
        batchSummaries.add(Restrictions.eq("sourceOrgId", orgId));
        batchSummaries.add(Restrictions.in("messageTypeId", messageTypeList));
        batchSummaries.add(Restrictions.in("targetOrgId", targetOrgList));
        List<batchUploadSummary> batchUploadSummaryList = batchSummaries.list();

        List<Integer> batchIdList = new ArrayList<Integer>();

        if (batchUploadSummaryList.isEmpty()) {
            batchIdList.add(0);
        } else {

            for (batchUploadSummary summary : batchUploadSummaryList) {
                batchIdList.add(summary.getbatchId());
            }

        }

        Criteria findBatches = sessionFactory.getCurrentSession().createCriteria(batchUploads.class);
        findBatches.add(Restrictions.in("id", batchIdList));
        findBatches.add(Restrictions.or(
                Restrictions.eq("statusId", 8),
                Restrictions.eq("statusId", 5)
        )
        );
        findBatches.addOrder(Order.desc("dateSubmitted"));

        return findBatches.list();
    }

    /**
     * The 'getBatchTransactions' function will return a list of transactions within a batch. The list of transactions will only be the ones the passed in user has access to.
     *
     * @param batchId The id of the selected batch
     * @param userId The id of the logged in user
     *
     * @return The function will return a list of transactionIn objects
     */
    @Override
    @Transactional
    public List<transactionIn> getBatchTransactions(int batchId, int userId) {
        /* Get a list of connections the user has access to */
        Criteria connections = sessionFactory.getCurrentSession().createCriteria(configurationConnectionSenders.class);
        connections.add(Restrictions.eq("userId", userId));
        List<configurationConnectionSenders> userConnections = connections.list();

        List<Integer> messageTypeList = new ArrayList<Integer>();
        List<Integer> targetOrgList = new ArrayList<Integer>();

        if (userConnections.isEmpty()) {
            messageTypeList.add(0);
            targetOrgList.add(0);
        } else {

            for (configurationConnectionSenders userConnection : userConnections) {
                Criteria connection = sessionFactory.getCurrentSession().createCriteria(configurationConnection.class);
                connection.add(Restrictions.eq("id", userConnection.getconnectionId()));

                configurationConnection connectionInfo = (configurationConnection) connection.uniqueResult();

                /* Get the message type for the configuration */
                Criteria sourceconfigurationQuery = sessionFactory.getCurrentSession().createCriteria(configuration.class);
                sourceconfigurationQuery.add(Restrictions.eq("id", connectionInfo.getsourceConfigId()));
                configuration configDetails = (configuration) sourceconfigurationQuery.uniqueResult();

                /* Add the message type to the message type list */
                messageTypeList.add(configDetails.getMessageTypeId());

                /* Get the list of target orgs */
                Criteria targetconfigurationQuery = sessionFactory.getCurrentSession().createCriteria(configuration.class);
                targetconfigurationQuery.add(Restrictions.eq("id", connectionInfo.gettargetConfigId()));
                configuration targetconfigDetails = (configuration) targetconfigurationQuery.uniqueResult();

                /* Add the target org to the target organization list */
                targetOrgList.add(targetconfigDetails.getorgId());
            }
        }

        /* Get a list of available batches */
        Criteria batchSummaries = sessionFactory.getCurrentSession().createCriteria(batchUploadSummary.class);
        batchSummaries.add(Restrictions.eq("batchId", batchId));
        batchSummaries.add(Restrictions.in("messageTypeId", messageTypeList));
        batchSummaries.add(Restrictions.in("targetOrgId", targetOrgList));
        List<batchUploadSummary> batchUploadSummaryList = batchSummaries.list();

        List<Integer> transactionInIdList = new ArrayList<Integer>();

        if (batchUploadSummaryList.isEmpty()) {
            transactionInIdList.add(0);
        } else {

            for (batchUploadSummary summary : batchUploadSummaryList) {
                transactionInIdList.add(summary.gettransactionInId());
            }

        }

        Criteria findTransactions = sessionFactory.getCurrentSession().createCriteria(transactionIn.class);
        findTransactions.add(Restrictions.in("id", transactionInIdList));
        findTransactions.addOrder(Order.desc("dateCreated"));

        return findTransactions.list();
    }

    /**
     * The 'getsentTransactions' function will return a list of sent transactions for the organization passed in.
     *
     * @param orgId The organization Id to find pending transactions for.
     *
     * @return The function will return a list of sent transactions
     */
    @Override
    @Transactional
    public List<transactionIn> getsentTransactions(int orgId) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(transactionIn.class);
        criteria.add(Restrictions.eq("statusId", 17));

        List<Integer> batchList = new ArrayList<Integer>();
        Criteria findBatches = sessionFactory.getCurrentSession().createCriteria(batchUploads.class);
        findBatches.add(Restrictions.eq("orgId", orgId));
        findBatches.add(Restrictions.eq("statusId", 2));

        List<batchUploads> batches = findBatches.list();

        for (batchUploads batch : batches) {
            batchList.add(batch.getId());
        }

        if (batchList.isEmpty()) {
            batchList.add(0);
        }

        criteria.add(Restrictions.in("batchId", batchList));

        criteria.addOrder(Order.desc("dateCreated"));

        return criteria.list();
    }

    /**
     * The 'getBatchDetails' function will return the batch details for the passed in batch id.
     *
     * @param batchId The id of the batch to return.
     */
    @Override
    @Transactional
    public batchUploads getBatchDetails(int batchId) {
        return (batchUploads) sessionFactory.getCurrentSession().get(batchUploads.class, batchId);

    }

    /**
     * The 'getTransactionDetails' function will return the transaction IN details for the passed in transactionId.
     *
     * @param transactionId The id of the transaction to return
     *
     */
    @Override
    @Transactional
    public transactionIn getTransactionDetails(int transactionId) {
        return (transactionIn) sessionFactory.getCurrentSession().get(transactionIn.class, transactionId);
    }

    /**
     * The 'getTransactionRecords' function will return the transaction IN records for the passed in transactionId.
     *
     * @param transactionId The id of the transaction records to return
     *
     */
    @Override
    @Transactional
    public transactionInRecords getTransactionRecords(int transactionId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from transactionInRecords where transactionInId = :transactionId");
        query.setParameter("transactionId", transactionId);

        transactionInRecords records = (transactionInRecords) query.uniqueResult();

        return records;
    }

    /**
     * The 'getTransactionRecord' function will return the transaction IN record for the passed in recordId.
     *
     * @param recordId The id of the records to return
     *
     */
    @Override
    @Transactional
    public transactionInRecords getTransactionRecord(int recordId) {
        return (transactionInRecords) sessionFactory.getCurrentSession().get(transactionInRecords.class, recordId);
    }

    /**
     * The 'submitTransactionTarget' function will submit the transaction target
     *
     * @param transactionTarget The object that will hold the transaction target info
     *
     * @table transactionTarget
     *
     * @return This function will return the generated transaction target id
     */
    @Override
    @Transactional
    public void submitTransactionTarget(transactionTarget transactionTarget) {
        sessionFactory.getCurrentSession().save(transactionTarget);
    }

    /**
     * The 'getTransactionTargetDetails' function will return the transaction TARGET details for the passed in transactionTargetId.
     *
     * @param transactionTargetId The id of the transaction target to return
     *
     * @table transactionTarget
     *
     */
    @Override
    @Transactional
    public transactionTarget getTransactionTargetDetails(int transactionTargetId) {
        return (transactionTarget) sessionFactory.getCurrentSession().get(transactionTarget.class, transactionTargetId);
    }

    /**
     * The 'submitTransactionTargetChanges' function will submit the transaction target changes
     *
     * @param transactionTarget The object that will hold the transaction target info
     *
     * @table transactionTarget
     *
     * @return This function will not return anything
     */
    @Override
    @Transactional
    public void submitTransactionTargetChanges(transactionTarget transactionTarget) {
        sessionFactory.getCurrentSession().update(transactionTarget);
    }

    /**
     * The 'getTransactionTarget' function will return the transaction TARGET records for the passed in transactionId and batchId.
     *
     * @param batchUploadId The id of the transaction batch to return
     * @param transactionId The id of the transaction to return
     *
     * @table transactionTarget
     *
     */
    @Override
    @Transactional
    public transactionTarget getTransactionTarget(int batchUploadId, int transactionInId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from transactionTarget where batchUploadId = :batchUploadId and transactionInId = :transactionId");
        query.setParameter("batchUploadId", batchUploadId);
        query.setParameter("transactionId", transactionInId);

        transactionTarget target = (transactionTarget) query.uniqueResult();

        return target;
    }

    /**
     * The 'submitAttachment' will save the attachment to the database
     *
     * @param attachment The object that will hold the attachment details
     *
     * @return THis function will return the id of the saved attachment.
     */
    @Override
    @Transactional
    public Integer submitAttachment(transactionAttachment attachment) {
        Integer attachmentId = null;

        attachmentId = (Integer) sessionFactory.getCurrentSession().save(attachment);

        return attachmentId;
    }

    /**
     * The 'getAttachmentById' function will return the details of an attachment for the passed in Id.
     *
     * @param attachmentId The id of the attachment to get the details for
     *
     * @return This function will return a transactionAttachment object
     */
    @Override
    @Transactional
    public transactionAttachment getAttachmentById(int attachmentId) {
        return (transactionAttachment) sessionFactory.getCurrentSession().get(transactionAttachment.class, attachmentId);
    }

    /**
     * The 'submitAttachmentChanges' function will update the attachment details for the passed in attachment.
     *
     * @param attachment The attachment object that contains the details
     *
     * @return This function does not return anything.
     */
    @Override
    @Transactional
    public void submitAttachmentChanges(transactionAttachment attachment) {
        sessionFactory.getCurrentSession().update(attachment);
    }

    /**
     * The 'getAttachmentsByTransactionId' function will return a list of attachments for the passed in transaction.
     *
     * @param transactionInId The id of the transaction to search on
     *
     * @return This function will return a list of transactionAttachment objects
     */
    @Override
    @Transactional
    public List<transactionAttachment> getAttachmentsByTransactionId(int transactionInId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(transactionAttachment.class);
        criteria.add(Restrictions.eq("transactionInId", transactionInId));

        return criteria.list();
    }

    /**
     * The 'removeAttachmentById' function will remove the attachment from the Database.
     *
     * @param attachmentId The id of the attachment to be removed
     *
     * @table transactionAttachments
     *
     * @return This function will not return anything.
     */
    @Override
    @Transactional
    public void removeAttachmentById(int attachmentId) {

        Query deletAttachment = sessionFactory.getCurrentSession().createQuery("delete from transactionAttachment where id = :attachmentId");
        deletAttachment.setParameter("attachmentId", attachmentId);
        deletAttachment.executeUpdate();

    }

    /**
     * The 'getConfigIdsForBatch' function will return a list of configurations for a batch
     *
     * @param batchUploadId The id of the attachment to be removed
     *
     * @table transactionTranslatedIn
     *
     * @return This function will return a list of configIds (Integer)
     */
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Integer> getConfigIdsForBatch(int batchUploadId) {

        String sql = ("select distinct configId from transactionTranslatedIn where transactionInId in (select transactionInId from transactionIn where batchId = :id);");

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", batchUploadId);

        List<Integer> configIds = query.list();

        return configIds;
	}
    

    @Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List <ConfigForInsert> setConfigForInsert(int configId, int batchUploadId) {
		String sql = ("call setSqlForConfig(:id, :batchUploadId);");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
        		.addScalar("saveToTableName", StandardBasicTypes.STRING)
				.addScalar("saveToTableCol", StandardBasicTypes.STRING)
				.addScalar("batchUploadId", StandardBasicTypes.INTEGER)
				.addScalar("configId", StandardBasicTypes.INTEGER)
				.addScalar("singleValueFields", StandardBasicTypes.STRING)
				.addScalar("splitFields", StandardBasicTypes.STRING)
				.addScalar("checkForDelim", StandardBasicTypes.STRING)
				.setResultTransformer(
				Transformers.aliasToBean(ConfigForInsert.class))
				.setParameter("id", configId)
				.setParameter("batchUploadId", batchUploadId);
        
        List <ConfigForInsert> configListForInsert = query.list();
		
		return configListForInsert;
	}

    @Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Integer> getTransWithMultiValues(ConfigForInsert config) {
		
			String sql = ("select transactionInId from "
					+ " transactionTranslatedIn where (" + config.getCheckForDelim() 
					+ ") and transactionInId in (select id from transactionIn where statusId = 10 "
					+ " and batchId = :batchUploadId"
					+ " and configId = :configId); ");
			
	        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
	        query.setParameter("configId", config.getConfigId());
	        query.setParameter("batchUploadId", config.getBatchUploadId());

	        List<Integer> transId = query.list();

	        return transId;
		
	}

    
	@Override
	@Transactional
	public boolean insertSingleToMessageTables(ConfigForInsert config) {
		
			//TODO need to handle mappings but there are no values - etc best time to call / not require / we do not need to insert blank row
		
			String sql  = "insert into " + config.getSaveToTableName() 
					+ " (transactionInId, " + config.getSaveToTableCol()
					+ ") select transactionInId, " 
					+ config.getSingleValueFields() 
					+ " from transactionTranslatedIn where "  
					+ " transactionInId in (select id from transactionIn where batchId = :batchId"
					+ " and configId = :configId and statusId = 10 ";
			if (config.getLoopTransIds().size() > 0) {
				sql = sql + " and id not in (" + config.getLoopTransIds().toString().substring(1, config.getLoopTransIds().toString().length()-1) + ")";
			}
			if (config.getBlankValueTransId().size() > 0) {
				sql = sql + " and id not in (" + config.getBlankValueTransId().toString().substring(1, config.getBlankValueTransId().toString().length()-1) + ")";
			}
			
			sql = sql + ");";
			
			Query insertData = sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("batchId", config.getBatchUploadId())
					.setParameter("configId", config.getConfigId());
			
			try {
				insertData.executeUpdate();
				return true;
			} catch (Throwable ex) {
	            System.err.println("insertSingleToMessageTables." + ex);
	            return false;
			}
	}

	/** this call sp to loop through values and insert **/
	@Override
	public boolean insertMultiToMessageTables(ConfigForInsert configForInsert) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public boolean clearMessageTableForBatch(int batchId, String mt) {
		String sql  = "delete from " + mt + " where transactionInId in "
				+ " (select id from transactionIn where batchId = :id)"
				+ ";";
		Query deleteTable = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("id",StandardBasicTypes.INTEGER ).setParameter("id", batchId);
		try {
			deleteTable.executeUpdate();
			return true;
		} catch (Throwable ex) {
            System.err.println("clearMessageTableForBatch failed. " + ex);
            return false;
            	
		}
	}
	


	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getMessageTables() {
		String sql = ("select table_name from information_schema.COLUMNS "
				+ " where TABLE_SCHEMA = :schemaName "
				+ " and table_name like concat('message_%')"
				+ " and column_name like 'transactionInId';");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("schemaName", schemaName);
        List<String> mt = query.list();
        return mt;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Integer> getBlankTransIds(ConfigForInsert config) {
		String sql = ("select transactionInId from "
				+ " transactionTranslatedIn where (length(concat(" + config.getSingleValueFields()
				+ ")) = 0 or length(concat(" + config.getSingleValueFields()
				+ ")) is null) and transactionInId in (select id from transactionIn where statusId = 10 "
				+ " and batchId = :batchUploadId"
				+ " and configId = :configId); ");
		
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("configId", config.getConfigId());
        query.setParameter("batchUploadId", config.getBatchUploadId());

        List<Integer> transId = query.list();

        return transId;
	}
	

	/** 
	 * This method will call the SP and pass in the transaction Id, field lists and table name
	 * 
	 * SP will select the values and split it so it will be inserted into
	 * the appropriate message_table in rows / pairs
	 * **/
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean insertMultiValToMessageTables(ConfigForInsert config) {
	
		return false;
	}
    
}

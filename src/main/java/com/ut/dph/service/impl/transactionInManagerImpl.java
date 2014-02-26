/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ut.dph.service.impl;

import com.ut.dph.dao.messageTypeDAO;
import com.ut.dph.dao.transactionInDAO;
import com.ut.dph.model.CrosswalkData;
import com.ut.dph.model.Macros;
import com.ut.dph.model.Organization;
import com.ut.dph.model.batchUploadSummary;
import com.ut.dph.model.batchUploads;
import com.ut.dph.model.configuration;
import com.ut.dph.model.configurationConnection;
import com.ut.dph.model.configurationDataTranslations;
import com.ut.dph.model.configurationFormFields;
import com.ut.dph.model.configurationTransport;
import com.ut.dph.model.fieldSelectOptions;
import com.ut.dph.model.transactionAttachment;
import com.ut.dph.model.transactionIn;
import com.ut.dph.model.transactionInRecords;
import com.ut.dph.model.transactionRecords;
import com.ut.dph.model.transactionTarget;
import com.ut.dph.model.custom.ConfigForInsert;
import com.ut.dph.reference.fileSystem;
import com.ut.dph.service.configurationManager;
import com.ut.dph.service.configurationTransportManager;
import com.ut.dph.service.messageTypeManager;
import com.ut.dph.service.organizationManager;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.ut.dph.service.transactionInManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chadmccue
 */
@Service
public class transactionInManagerImpl implements transactionInManager {

    @Autowired
    private transactionInDAO transactionInDAO;

    @Autowired
    private messageTypeDAO messageTypeDAO;

    @Autowired
    private configurationManager configurationManager;

    @Autowired
    private configurationTransportManager configurationtransportmanager;

    @Autowired
    private messageTypeManager messagetypemanager;

    @Autowired
    private organizationManager organizationmanager;

    @Override
    @Transactional
    public String getFieldValue(String tableName, String tableCol, int idValue) {
        return transactionInDAO.getFieldValue(tableName, tableCol, idValue);
    }

    @Override
    @Transactional
    public List<fieldSelectOptions> getFieldSelectOptions(int fieldId, int configId) {
        return transactionInDAO.getFieldSelectOptions(fieldId, configId);
    }

    @Override
    @Transactional
    public Integer submitBatchUpload(batchUploads batchUpload) throws Exception {
        return transactionInDAO.submitBatchUpload(batchUpload);
    }

    @Override
    @Transactional
    public void submitBatchUploadSummary(batchUploadSummary summary) throws Exception {
        transactionInDAO.submitBatchUploadSummary(summary);
    }

    @Override
    @Transactional
    public void submitBatchUploadChanges(batchUploads batchUpload) throws Exception {
        transactionInDAO.submitBatchUploadChanges(batchUpload);
    }

    @Override
    @Transactional
    public Integer submitTransactionIn(transactionIn transactionIn) throws Exception {
        return transactionInDAO.submitTransactionIn(transactionIn);
    }

    @Override
    @Transactional
    public void submitTransactionInChanges(transactionIn transactionIn) throws Exception {
        transactionInDAO.submitTransactionInChanges(transactionIn);
    }

    @Override
    @Transactional
    public Integer submitTransactionInRecords(transactionInRecords records) throws Exception {
        return transactionInDAO.submitTransactionInRecords(records);
    }

    @Override
    @Transactional
    public void submitTransactionInRecordsUpdates(transactionInRecords records) throws Exception {
        transactionInDAO.submitTransactionInRecordsUpdates(records);
    }

    @Override
    @Transactional
    public void submitTransactionTranslatedInRecords(int transactionId, int transactionRecordId, int configId) throws Exception {
        transactionInDAO.submitTransactionTranslatedInRecords(transactionId, transactionRecordId, configId);
    }

    @Override
    @Transactional
    public List<batchUploads> getpendingBatches(int userId, int orgId, String searchTerm, Date fromDate, Date toDate, int page, int maxResults) throws Exception {
        return transactionInDAO.getpendingBatches(userId, orgId, searchTerm, fromDate, toDate, page, maxResults);
    }

    @Override
    @Transactional
    public List<transactionIn> getBatchTransactions(int batchId, int userId) throws Exception {
        return transactionInDAO.getBatchTransactions(batchId, userId);
    }

    @Override
    @Transactional
    public List<batchUploads> getsentBatches(int userId, int orgId, String searchTerm, Date fromDate, Date toDate, int page, int maxResults) throws Exception {
        return transactionInDAO.getsentBatches(userId, orgId, searchTerm, fromDate, toDate, page, maxResults);
    }

    @Override
    @Transactional
    public batchUploads getBatchDetails(int batchId) throws Exception {
        return transactionInDAO.getBatchDetails(batchId);
    }

    @Override
    @Transactional
    public transactionIn getTransactionDetails(int transactionId) throws Exception {
        return transactionInDAO.getTransactionDetails(transactionId);
    }

    @Override
    @Transactional
    public transactionInRecords getTransactionRecords(int transactionId) {
        return transactionInDAO.getTransactionRecords(transactionId);
    }

    @Override
    @Transactional
    public transactionInRecords getTransactionRecord(int recordId) {
        return transactionInDAO.getTransactionRecord(recordId);
    }

    @Override
    @Transactional
    public void submitTransactionTarget(transactionTarget transactionTarget) {
        transactionInDAO.submitTransactionTarget(transactionTarget);
    }

    @Override
    @Transactional
    public transactionTarget getTransactionTargetDetails(int transactionTargetId) {
        return transactionInDAO.getTransactionTargetDetails(transactionTargetId);
    }

    @Override
    @Transactional
    public void submitTransactionTargetChanges(transactionTarget transactionTarget) throws Exception {
        transactionInDAO.submitTransactionTargetChanges(transactionTarget);
    }

    @Override
    @Transactional
    public transactionTarget getTransactionTarget(int batchUploadId, int transactionInId) {
        return transactionInDAO.getTransactionTarget(batchUploadId, transactionInId);
    }

    /**
     * The 'uploadAttachment' function will take in the file and orgName and upload the file to the appropriate file on the file system.
     *
     * @param fileUpload The file to be uploaded
     * @param orgName The organization name that is uploading the file. This will be the folder where to save the file to.
     */
    @Override
    public String uploadAttachment(MultipartFile fileUpload, String orgName) throws Exception {

        MultipartFile file = fileUpload;
        String fileName = file.getOriginalFilename();

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = file.getInputStream();
            File newFile = null;

            //Set the directory to save the brochures to
            fileSystem dir = new fileSystem();
            dir.setDir(orgName, "attachments");

            newFile = new File(dir.getDir() + fileName);

            if (newFile.exists()) {
                int i = 1;
                while (newFile.exists()) {
                    int iDot = fileName.lastIndexOf(".");
                    newFile = new File(dir.getDir() + fileName.substring(0, iDot) + "_(" + ++i + ")" + fileName.substring(iDot));
                }
                fileName = newFile.getName();
            } else {
                newFile.createNewFile();
            }

            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();

            //Save the attachment
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    @Override
    @Transactional
    public Integer submitAttachment(transactionAttachment attachment) throws Exception {
        return transactionInDAO.submitAttachment(attachment);
    }

    @Override
    @Transactional
    public transactionAttachment getAttachmentById(int attachmentId) throws Exception {
        return transactionInDAO.getAttachmentById(attachmentId);
    }

    @Override
    @Transactional
    public void submitAttachmentChanges(transactionAttachment attachment) throws Exception {
        transactionInDAO.submitAttachmentChanges(attachment);
    }

    @Override
    @Transactional
    public List<transactionAttachment> getAttachmentsByTransactionId(int transactionInId) throws Exception {
        return transactionInDAO.getAttachmentsByTransactionId(transactionInId);
    }

    @Override
    @Transactional
    public void removeAttachmentById(int attachmentId) throws Exception {

        /* Need to get the file name of the attachment */
        transactionAttachment attachment = getAttachmentById(attachmentId);

        /* Need to remove the attachment */
        fileSystem currdir = new fileSystem();
        currdir.setDirByName(attachment.getfileLocation());
        File currFile = new File(currdir.getDir() + attachment.getfileName());
        currFile.delete();

        /* Now remove the attachment from the database */
        transactionInDAO.removeAttachmentById(attachmentId);

    }

    /**
     * 1.27.14 not in use
     *
     * Last Step - insert all transactions with RP (10) status and batch status of SRP (5) for a batch
     *
     */
    @Override
    public boolean processTransactions(int batchUploadId) {
        return true;
    }

    @Override
    public List<ConfigForInsert> setConfigForInsert(int configId, int batchUploadId) {
        // we call sp and set the parameters here
        return transactionInDAO.setConfigForInsert(configId, batchUploadId);
    }

    @Override
    public List<Integer> getConfigIdsForBatch(int batchUploadId, boolean getAll) {
        return transactionInDAO.getConfigIdsForBatch(batchUploadId, getAll);
    }

    @Override
    public List<Integer> getTransWithMultiValues(ConfigForInsert config) {
        return transactionInDAO.getTransWithMultiValues(config);
    }

    /**
     * These are ready records We will insert by configId All the values for being inserted into the same field would have been appended to the first field Id *
     */
    @Override
    public boolean insertSingleToMessageTables(ConfigForInsert configForInsert) {
        return transactionInDAO.insertSingleToMessageTables(configForInsert);
    }

    /**
     * this method takes in the transId, the insert fields, the insert tables*
     */
    @Override
    public boolean insertMultiValToMessageTables(ConfigForInsert config, Integer subStringCounter, Integer transId) {
        return transactionInDAO.insertMultiValToMessageTables(config, subStringCounter, transId);
    }

    @Override
    public Integer clearMessageTables(int batchId) {
        List<String> mts = transactionInDAO.getMessageTables();
        Integer sysErrorCount = 0;
        try {
            for (String mt : mts) {
            	sysErrorCount = sysErrorCount + transactionInDAO.clearMessageTableForBatch(batchId, mt);
            }
            return sysErrorCount;
        } catch (Exception e) {
            System.out.println("clearMessageTables " + e.getStackTrace());
            return 1;

        }
    }

    /**
     * The 'uploadBatchFile' function will take in the file and orgName and upload the file to the appropriate file on the file system. The function will run the file through various validations. If a single validation fails the batch will be put in a error validation status and the file will be removed from the system. The user will receive an error message on the screen letting them know which validations have failed and be asked to upload a new file.
     *
     * The following validations will be taken place. - File is not empty - Proper file type (as determined in the configuration set up) - Proper delimiter (as determined in the configuration set up) - Does not exceed file size (as determined in the configuration set up)
     *
     * @param configId The configuration Id to get some validation parameters
     * @param fileUpload The file to be uploaded
     *
     */
    @Override
    public Map<String, String> uploadBatchFile(int configId, MultipartFile fileUpload) throws Exception {

        configuration configDetails = configurationManager.getConfigurationById(configId);
        configurationTransport transportDetails = configurationtransportmanager.getTransportDetails(configId);

        MultipartFile file = fileUpload;
        String fileName = file.getOriginalFilename();

        long fileSize = file.getSize();
        long fileSizeMB = (fileSize / (1024L * 1024L));

        /* 
         1 = File is empty
         2 = Too large
         3 = Wrong file type
         4 = Wrong delimiter
         */
        Map<String, String> batchFileResults = new HashMap<String, String>();

        /* Make sure the file is not empty : ERROR CODE 1 */
        if (fileSize == 0) {
            batchFileResults.put("emptyFile", "1");
        }

        /* Make sure file is the correct size : ERROR CODE 2 */
        double maxFileSize = (double) transportDetails.getmaxFileSize();

        if (fileSizeMB > maxFileSize) {
            batchFileResults.put("wrongSize", "2");
        }

        InputStream inputStream;
        OutputStream outputStream;

        try {
            inputStream = file.getInputStream();
            File newFile = null;

            //Set the directory to save the brochures to
            fileSystem dir = new fileSystem();

            String filelocation = transportDetails.getfileLocation();
            filelocation = filelocation.replace("/bowlink/", "");
            dir.setDirByName(filelocation);

            newFile = new File(dir.getDir() + fileName);

            if (newFile.exists()) {
                int i = 1;
                while (newFile.exists()) {
                    int iDot = fileName.lastIndexOf(".");
                    newFile = new File(dir.getDir() + fileName.substring(0, iDot) + "_(" + ++i + ")" + fileName.substring(iDot));
                }
                fileName = newFile.getName();
                newFile.createNewFile();
            } else {
                newFile.createNewFile();
            }

            batchFileResults.put("fileName", fileName);

            /* Make sure file is the correct file type : ERROR CODE 3 */
            String ext = FilenameUtils.getExtension(dir.getDir() + fileName);

            String fileType = (String) configurationManager.getFileTypesById(transportDetails.getfileType());

            if (ext == null ? fileType != null : !ext.equals(fileType)) {
                batchFileResults.put("wrongFileType", "3");
            }

            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();

            /* Make sure the file has the correct delimiter : ERROR CODE 5 */
            String delimChar = (String) messageTypeDAO.getDelimiterChar(transportDetails.getfileDelimiter());

            //Check to make sure the file contains the selected delimiter
            //Set the directory that holds the crosswalk files
            int delimCount = (Integer) dir.checkFileDelimiter(dir, fileName, delimChar);

            if (delimCount < 10) {
                batchFileResults.put("wrongDelim", "4");
            }

            //Save the attachment
        } catch (IOException e) {
            e.printStackTrace();
        }

        return batchFileResults;

    }

    @Override
    public List<Integer> getBlankTransIds(ConfigForInsert config) {
        return transactionInDAO.getBlankTransIds(config);
    }

    @Override
    public Integer countSubString(ConfigForInsert config, Integer transId) {
        return transactionInDAO.countSubString(config, transId);
    }

    @Override
    public List<batchUploads> getuploadedBatches(int userId, int orgId) throws Exception {
        return transactionInDAO.getuploadedBatches(userId, orgId);
    }

    /**
     * We will take a batch and then from its status etc we will decide if we want to process transactions or not. This allow the admin to run just one batch
     *
     * This assumes batches SR - 6, Trans status REL We still run through entire process but these records should pass... (check to make sure it aligns with file upload) just be applying Macros / CW and inserting into our message tables
     */
    /**
     * This assumes batch being passed in is SSA or SR 1. We set it to SBP, start date time
     *
     * *
     */
    @Override
    public boolean processBatch(int batchUploadId) throws Exception {

		Integer batchStausId = 29;
		List <Integer> errorStatusIds = Arrays.asList(11,13,14);
		/**
		 * get batch details *
		 */
		batchUploads batch = getBatchDetails(batchUploadId);

		/**
		 * ERG are loaded already, we load all other files - maybe move loading?
		 * *
		 */
		if (batch.gettransportMethodId() != 2) { // 2 is ERG

            // set batch to SBP - 4
            updateBatchStatus(batchUploadId, 4, "startDateTime");

            // let's clear all tables first as we are starting over
             Integer sysErrors = clearTransactionTables(batchUploadId);
             String errorMessage = "Load errors, please contact admin to review logs";
			// loading batch will take it all the way to loaded (9) status for
			
           //get delimiter, get fileWithPath etc
             
 	    	if (batch.getoriginalFileName().endsWith(".txt")) {
 	    		sysErrors = sysErrors + loadTextBatch(batch);
 	    	}
 	    	
 	    	//load targets
 	    	List<configurationConnection> batchTargetList =  getBatchTargets(batchUploadId);
 	    	for (configurationConnection bt : batchTargetList) {
 	    		sysErrors = sysErrors + insertBatchTargets(batchUploadId, bt);
 	    		//populate batchUploadSummary need batchId, transactionInId,  configId, sourceOrgId, messageTypeId - in configurations - missing targetOrgId, 
 	 	    	sysErrors = sysErrors + insertBatchUploadSummary(batch, bt);
 	    	}
 	    	
 	    	if (sysErrors > 0) {
            	insertProcessingError(5, null, batchUploadId, null, null, null, null, false, false, errorMessage);
 				updateBatchStatus(batchUploadId, batchStausId, "endDateTime");
 				return false;
             } 
           
			//we check handling here for rejecting entire batch
            List <configurationTransport> batchHandling = getHandlingDetailsByBatch(batchUploadId);
            if (batchHandling.size() != 1) {
				//TODO email admin to fix problem
				insertProcessingError(5, null, batchUploadId, null, null, null, null, false, false, "Multiple or no file handling found, please check auto-release and error handling configurations");
				updateRecordCounts (batchUploadId, new ArrayList <Integer> (), false, "totalRecordCount");
				// do we count pass records as errors?
				updateRecordCounts (batchUploadId, errorStatusIds, false, "errorRecordCount");
				updateBatchStatus(batchUploadId, batchStausId, "endDateTime");
				return false;
			}
            if (batchHandling.size() == 1) {
            	//reject submission on error
            	if (batchHandling.get(0).geterrorHandling() == 3) {
            		// at this point we will only have invalid records
            		if (getRecordCounts (batchUploadId, errorStatusIds, false) > 0) {
            			updateBatchStatus(batchUploadId, 7, "endDateTime");
            			//update loaded to rejected
            			updateTransactionStatus(batchUploadId, 0, 9 , 13);
            			return false;
            		}
            	}
            }
			
           //at the end of loaded, we update to PR
			updateTransactionStatus(batchUploadId, 0, 9 , 10);	
			updateTransactionTargetStatus(batchUploadId, 0, 9, 10);
			 
            // after loading is successful we update to SSL
            updateBatchStatus(batchUploadId, 3, "startDateTime");
           
						
		}
		
		// get batch details again for next set of processing
		batch = getBatchDetails(batchUploadId);
					

		//this should be the same point of both ERG and Uploaded File *
		Integer systemErrorCount = 0;
		// Check to make sure the file is valid for processing, valid file is a batch with SSL (3) or SR (6)*
		if ((batch.getstatusId() == 3 || batch.getstatusId() == 6)) {
			// set batch to SBP - 4*
			updateBatchStatus(batchUploadId, 4, "startDateTime");
			
			/** we should only process the ones that are not REL status, 
			 * to be safe, we copy over data from transactionInRecords**/
			resetTransactionTranslatedIn(batchUploadId, false);
			
			List<Integer> configIds = getConfigIdsForBatch(batchUploadId, false);
			for (Integer configId : configIds) {
				//we need to run all checks before insert regardless *
				
				//check R/O
				List<configurationFormFields> reqFields = getRequiredFieldsForConfig(configId);
				
				for (configurationFormFields cff : reqFields) {
					systemErrorCount = systemErrorCount + insertFailedRequiredFields(cff, batchUploadId);
				}
				// update status of the failed records to ERR - 14
				updateStatusForErrorTrans(batchUploadId, 14, false);

				/**
				 * run validation*
				 */
				systemErrorCount = systemErrorCount + runValidations(batchUploadId, configId);
				// update status of the failed records to ERR - 14
				updateStatusForErrorTrans(batchUploadId, 14, false);

				// 1. grab the configurationDataTranslations and run cw/macros
				List<configurationDataTranslations> dataTranslations = configurationManager
						.getDataTranslationsWithFieldNo(configId);
				for (configurationDataTranslations cdt : dataTranslations) {
					if (cdt.getCrosswalkId() != 0) {
						systemErrorCount = systemErrorCount + processCrosswalk(configId, batchUploadId, cdt, false);
					} else if (cdt.getMacroId() != 0) {
						systemErrorCount = systemErrorCount + processMacro(configId, batchUploadId, cdt, false);
					}
				}

				
				/**
				 * if there are errors, those are system errors, they will be
				 * logged we get errorId 5 and email to admin, update batch to
				 * 29
				 ***/
				if (systemErrorCount > 0) {
					//error batch
					updateBatchStatus (batchUploadId, batchStausId, "endDateTime");
					//TODO email admin
					// break out of loop as errorCount is system error
					return false;
				} 	
			} //end of configs
			
			updateTransactionStatus(batchUploadId, 0, 10, 12);
			//transactionIn and transactionTarget status should be the same 
			copyTransactionInStatusToTarget(batchUploadId);
			// TODO should move this method to processHandling so codes are not so cumbersome
			/**
			 * batches gets process again when user hits release button, maybe have separate method call
			 * for those that are just going from pending release to release, have to think about scenario when upload file is huge
			 * **/
			List <configurationTransport> handlingDetails = getHandlingDetailsByBatch(batchUploadId);
			// multiple config should be set to handle the batch the same way - we email admin if we don't have one way of handling a batch
			if (handlingDetails.size() != 1) {
				//TODO email admin to fix problem
				insertProcessingError(5, null, batchUploadId, null, null, null, null, false, false, "Multiple or no file handling found, please check auto-release and error handling configurations");
				updateRecordCounts (batchUploadId, new ArrayList <Integer> (), false, "totalRecordCount");
				// do we count pass records as errors?
				updateRecordCounts (batchUploadId, errorStatusIds, false, "errorRecordCount");
				updateBatchStatus(batchUploadId, batchStausId, "endDateTime");
				return false;
			}
			
			if (handlingDetails.size() == 1) {
				/**
				 	1 = Post errors to ERG
					2 = Reject record on error
					3 = Reject submission on error
					4 = Pass through errors
				 **/
				if (getRecordCounts (batchUploadId, Arrays.asList(14), false) > 0 && batch.getstatusId() == 6) {
					//batches with error should not be released, can only be Rejected/Invalid, set batch back to PR and go through auto/error handling
					batch.setstatusId(5);
					batchStausId = 5;
				}
				if ((handlingDetails.get(0).getautoRelease() && handlingDetails.get(0).geterrorHandling() == 1) 
						&& (batch.getstatusId() != 6)) {
					//TODO send email here
					insertProcessingError(5,  null, batchUploadId, null, null, null, null,
							false, false, "A batch cannot be set to auto-release and post errors to ERG.  Please modify configuration settings.");
					updateRecordCounts (batchUploadId, new ArrayList <Integer> (), false, "totalRecordCount");
					// do we count pass records as errors?
					updateRecordCounts (batchUploadId, errorStatusIds, false, "errorRecordCount");
					updateBatchStatus(batchUploadId, batchStausId, "endDateTime");
					return false;
				} else if (batch.getstatusId() == 6 || (handlingDetails.get(0).getautoRelease() &&
						(handlingDetails.get(0).geterrorHandling() == 2 || handlingDetails.get(0).geterrorHandling() == 4))) {
					
					if (handlingDetails.get(0).geterrorHandling() == 2) {
							//reject errors
							updateTransactionStatus(batchUploadId, 0, 14, 13);
							copyTransactionInStatusToTarget(batchUploadId);	
					} else if  (handlingDetails.get(0).geterrorHandling() == 4) {
							//update to pass - 16
							updateTransactionStatus(batchUploadId, 0, 14, 16);
							//target should still be pending output
							updateTransactionTargetStatus(batchUploadId, 0, 14, 19);
					}
					//we insert here
					if (!insertTransactions(batchUploadId)) {
						//something went wrong, we removed all inserted entries
						clearMessageTables(batchUploadId);
						insertProcessingError(5,  null,  batchUploadId,null, null, null, null,
								false, false, "An error occurred while inserting into message tables.");
						updateRecordCounts (batchUploadId, new ArrayList <Integer> (), false, "totalRecordCount");
						// do we count pass records as errors?
						updateRecordCounts (batchUploadId, errorStatusIds, false, "errorRecordCount");
						updateBatchStatus(batchUploadId, batchStausId, "endDateTime");
						return false;
					}
						// all went well
						updateTransactionStatus(batchUploadId, 0, 12, 19);
						updateTransactionTargetStatus(batchUploadId, 0, 12, 19);
						batchStausId = 24;
						
				}  else if (handlingDetails.get(0).getautoRelease() && handlingDetails.get(0).geterrorHandling() == 3) {
					//auto-release, 3 = Reject submission on error 
					batchStausId = 7;
					//updating entire batch to reject since error transactionIds are in error tables
					updateTransactionTargetStatus(batchUploadId, 0, 14, 13);
					updateTransactionStatus(batchUploadId, 0, 14, 13);
					
				}  else if (!handlingDetails.get(0).getautoRelease() && handlingDetails.get(0).geterrorHandling() == 1) { //manual release
					//transaction will be set to saved, batch will be set to RP
					batchStausId = 5;
					//we leave status alone as we already set them
				} else if (!handlingDetails.get(0).getautoRelease() && handlingDetails.get(0).geterrorHandling() == 2) {
					//reject records
					batchStausId = 5;
					updateTransactionStatus(batchUploadId, 0, 14, 13);
					updateTransactionTargetStatus(batchUploadId, 0, 14, 13);	
				} else if (!handlingDetails.get(0).getautoRelease() && handlingDetails.get(0).geterrorHandling() == 3) {
					batchStausId = 7;
					updateTransactionStatus(batchUploadId, 0, 14, 13);
					updateTransactionTargetStatus(batchUploadId, 0, 14, 13);
				} else if (!handlingDetails.get(0).getautoRelease() && handlingDetails.get(0).geterrorHandling() == 4) {
						batchStausId = 5;
						// pass
						updateTransactionStatus(batchUploadId, 0, 14, 16);
						updateTransactionTargetStatus(batchUploadId, 0, 14, 19);
				} //end of checking auto/error handling
				
				updateRecordCounts (batchUploadId, new ArrayList <Integer> (), false, "totalRecordCount");
				// do we count pass records as errors?
				updateRecordCounts (batchUploadId, errorStatusIds, false, "errorRecordCount");
				updateBatchStatus(batchUploadId, batchStausId, "endDateTime");
				 
			} //end of making sure there is one handling details for batch
		
		} // end of single batch insert 
		
		return true;
	}

    /**
     * this is called by the scheduler It selects all batch with a status of SSA Loads them SSL - Trans - L Starts the Process - SBP - Parses
     *
     * 1. Validate R/O - ERG records will just pass we select batches that are 2. Validate Fields - ERG records will just pass 3. Apply CW / Macros - ERG records should just pass as what is being inserted is our internal standard 4. We insert
     *
     * In between don't forget to change status Might need wrapper to align upload file process
     */
    @Override
    public boolean processBatches() {
		//0. grab all batches with SSA - 2
    	/**
         * loop and process
         */
        return false;
    }

    @Override
    public void updateBatchStatus(Integer batchUploadId, Integer statusId, String timeField) throws Exception {
        transactionInDAO.updateBatchStatus(batchUploadId, statusId, timeField);
    }

    @Override
    public void updateTransactionStatus(Integer batchUploadId, Integer transactionId, Integer fromStatusId, Integer toStatusId) throws Exception {
        transactionInDAO.updateTransactionStatus(batchUploadId, transactionId, fromStatusId, toStatusId);
    }

    @Override
    public void updateTransactionTargetStatus(Integer batchUploadId, Integer transactionId, Integer fromStatusId, Integer toStatusId) throws Exception {
        transactionInDAO.updateTransactionTargetStatus(batchUploadId, transactionId, fromStatusId, toStatusId);
    }

    /**
     * provided the batch status is not one of the delivery status (22 SDL, 23 SDC) what would we like clear batch to do 1. Change batch process to SBP to nothing can be touch 2. remove records from message tables 3. figure out what status to set batch *
     */
    @Override
    public boolean clearBatch(Integer batchUploadId) throws Exception {
        boolean canDelete = transactionInDAO.allowBatchClear(batchUploadId);
        Integer sysError = 0;
        if (canDelete) {
            //TODO how much should we clear? Is it different for ERG and Upload?

            sysError = clearMessageTables(batchUploadId);
            if (sysError == 0) {
                int toBatchStatusId = 3; //SSA
                if (getBatchDetails(batchUploadId).gettransportMethodId() == 2) {
                	sysError = clearTransactionInErrors(batchUploadId);
                    toBatchStatusId = 5;
                    resetTransactionTranslatedIn(batchUploadId, true);
                    transactionInDAO.updateTransactionStatus(batchUploadId, 0, 0, 15);
                } else {
                    //we clear transactionInRecords here as for batch upload we start over
                	sysError = clearTransactionTables(batchUploadId);
                }
                transactionInDAO.updateBatchStatus(batchUploadId, toBatchStatusId, "");
            }
        }
        if (sysError == 0) {
        	return true;
        } else {
        	return false;
        }
    }

    @Override
    public boolean setDoNotProcess(Integer batchId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Integer clearTransactionInRecords(Integer batchUploadId) {
        return transactionInDAO.clearTransactionInRecords(batchUploadId);
    }

    /**
     * This method assumes that all records are validated and ready for insert We loop through each configuration and insert Transaction status will remain unchanged. *
     */
    @Override
    public boolean insertTransactions(Integer batchUploadId) {
        List<Integer> configIds = getConfigIdsForBatch(batchUploadId, true);
        boolean processTransactions = true;

        for (Integer configId : configIds) {

            //blank values are seen as space and will cause errors when insert if field is not use
            List<configurationFormFields> configurationFormFields
                    = configurationtransportmanager.getCffByValidationType(configId, 0);
            for (configurationFormFields cff : configurationFormFields) {
                updateBlanksToNull(cff, batchUploadId);
            }

            /**
             * this list have the insert /check statements for each message table *
             */
            List<ConfigForInsert> configforConfigIds = setConfigForInsert(configId, batchUploadId);
            /**
             * we loop though each table and grab the transactions that has multiple values for that table, we set it to a list *
             */
            for (ConfigForInsert config : configforConfigIds) {
                /**
                 * we grab list of ids with multiple for this config we use the checkDelim string to look for those transactions *
                 */
                List<Integer> transIds = getTransWithMultiValues(config);
                config.setLoopTransIds(transIds);
                /**
                 * we need to check if we need to insert in case the whole table is mapped but doesn't contain values *
                 */
                List<Integer> skipTheseIds = getBlankTransIds(config);
                config.setBlankValueTransId(skipTheseIds);

                //we insert single values
                if (!insertSingleToMessageTables(config)) {
                    return false;
                }

                //we loop through transactions with multiple values and use SP to loop values with delimiters
                for (Integer transId : transIds) {
                    //we check how long field is
                    Integer subStringTotal = countSubString(config, transId);
                    for (int i = 0; i <= subStringTotal; i++) {
                        if (!insertMultiValToMessageTables(config, i + 1, transId)) {
                            return false;
                        }
                    }
                }
            }
        }

        return processTransactions;
    }

    /**
     * this process will load an upload file with status of SSA and take it all the way to SSL
     *
     * 1. read file 2. parse row by row and a. figure out config b. insert into transactionIn c. insert into transacitonTarget d. flag transactions as Loaded or Invalid *
     */
    @Override
    public Integer loadTextBatch(batchUploads batchUpload) {
    	try {
    		/**
	         * SP can't call load Files with prepared statement, we have to load file to real table with dynamic statement created in java
	         * 1. we create batchLoadTable
	         **/
	    	String loadTableName = "uploadTable_" + batchUpload.getId(); 
	    	//make sure old table is dropped if exists
	    	Integer sysError =  dropLoadTable(loadTableName);
	    	sysError  = sysError  + createLoadTable(loadTableName);
	    	
	    	
	    		fileSystem dir = new fileSystem();
	    		dir.setDirByName("/");
	            String fileWithPath = dir.getDir() + batchUpload.getFileLocation() + batchUpload.getoriginalFileName();
	            fileWithPath = fileWithPath.replace("bowlink///", "");
	            
		    	
	            //2. we load data with my sql
		    	sysError  = sysError  + insertLoadData (batchUpload.getId(), batchUpload.getDelimChar(), fileWithPath, loadTableName, batchUpload.isContainsHeaderRow());
		    	
		    	//3. we update batchId, loadRecordId
		    	sysError  = sysError  + updateLoadTable(loadTableName, batchUpload.getId());
		        
		    	// 4. we insert into transactionIn - status of invalid (11), batchId, loadRecordId
		    	sysError  = sysError  + loadTransactionIn(loadTableName, batchUpload.getId());
		        
		    	//5. we insert into transactionInRecords - we select transactionIn batchId, transactionInId
		    	sysError  = sysError  + loadTransactionInRecords(batchUpload.getId());
		    	
		    	//6. we match loadRecordId and update transactionInRecords's F1-F255 data
		    	sysError  = sysError  + loadTransactionInRecordsData(loadTableName);
		    	
		    	//7. we delete loadTable
		    	sysError  = sysError  + dropLoadTable(loadTableName);
	        	
		    	//8. we see how if the file only has one upload type so we don't need to parse every line
		    	 // if we only have one, we update the entire table 
		    	
		    	if (batchUpload.getConfigId() != null && batchUpload.getConfigId() != 0) {
		    		 // we update entire transactionIN with configId
		    		 sysError  = sysError  +  updateConfigIdForBatch(batchUpload.getId(), batchUpload.getConfigId());
		    	 } else {
		    		 // we parse every record to populate configId
		    		 //TODO need to write
		    		 sysError++;
		    		 insertProcessingError(5,  null,  batchUpload.getId(), null, null, null, null,
								false, false, "Files with multiple configurations are not supported yet.");
				 }
		    	 
		    	 //we populate transactionTranslatedIn
		    	 sysError  = sysError  +  loadTransactionTranslatedIn (batchUpload.getId());
		    	 
		    	 //update data in transactionTranslatedIn
		    	 resetTransactionTranslatedIn(batchUpload.getId(), true);
		    	
		    	 //records are loaded at this point
		    	 updateTransactionStatus(batchUpload.getId(), 0, 11 , 9);
		    	 
	    	return sysError;
    	} catch (Exception ex) {
    		System.out.println(ex.getClass() + " " + ex.getCause());
    		return 1;
    	}
        
    }

    @Override
    public Integer clearTransactionTranslatedIn(Integer batchUploadId) {
        return transactionInDAO.clearTransactionTranslatedIn(batchUploadId);
    }

    @Override
    public Integer clearTransactionTables(Integer batchUploadId) {
        //we clear transactionTranslatedIn
        Integer cleared = clearTransactionTranslatedIn(batchUploadId);
        //we clear transactionInRecords
        cleared = cleared + clearTransactionInRecords(batchUploadId);
        //we clear transactionTarget
        cleared = cleared + clearTransactionTarget(batchUploadId);
        cleared = cleared + clearTransactionInErrors(batchUploadId);
        cleared = cleared + clearBatchUploadSummary(batchUploadId);
        //we clear transactionIn
        cleared = cleared + clearTransactionIn(batchUploadId);
       
        if (cleared > 0) {
            flagAndEmailAdmin(batchUploadId);
        }
        return cleared;
    }

    @Override
    public Integer clearTransactionTarget(Integer batchUploadId) {
        return transactionInDAO.clearTransactionTarget(batchUploadId);
    }

    @Override
    public Integer clearTransactionIn(Integer batchUploadId) {
        return transactionInDAO.clearTransactionIn(batchUploadId);
    }

    @Override
    public void flagAndEmailAdmin(Integer batchUploadId) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<configurationFormFields> getRequiredFieldsForConfig(Integer configId) {
        return configurationtransportmanager.getRequiredFieldsForConfig(configId);
    }

    @Override
    public Integer insertFailedRequiredFields(configurationFormFields cff, Integer batchUploadId) {
        return transactionInDAO.insertFailedRequiredFields(cff, batchUploadId);
    }

    @Override
    public Integer clearTransactionInErrors(Integer batchUploadId) {
        return transactionInDAO.clearTransactionInErrors(batchUploadId);
    }

    /**
     * This method finds all error transactionInId in TransactionInErrors / TransactionOutErrors and update transactionIn with the appropriate error status It can be passed, reject and error
     *
     */
    @Override
    public void updateStatusForErrorTrans(Integer batchId,
            Integer statusId, boolean foroutboundProcessing) {
        transactionInDAO.updateStatusForErrorTrans(batchId, statusId, foroutboundProcessing);
    }

    @Override
    public Integer runValidations(Integer batchUploadId, Integer configId) {
        Integer errorCount = 0;
        //1. we get validation types
        //2. we skip 1 as that is not necessary
        //3. we skip date (4) as there is no isDate function in MySQL
        //4. we skip the ids that are not null as Mysql will bomb out checking character placement
        //5. back to date, we grab transaction info and we loop (errId 7)

        /**
         * MySql RegEXP validate numeric - ^-?[0-9]+[.]?[0-9]*$|^-?[.][0-9]+$ validate email - ^[a-z0-9\._%+!$&*=^|~#%\'`?{}/\-]+@[a-z0-9\.-]+\.[a-z]{2,6}$ or ^[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$ validate url - ^(https?://)?([\da-z.-]+).([a-z0-9])([0-9a-z]*)*[/]?$ - need to fix not correct - might have to run in java as mysql is not catching all. validate phone - should be no longer than 11 digits ^[0-9]{7,11}$ validate date - doing this in java
         *
         */
        //TODO was hoping to have one SP but concat in SP not setting and not catching errors correctly. Need to recheck
        List<configurationFormFields> configurationFormFields
                = configurationtransportmanager.getCffByValidationType(configId, 0);

        for (configurationFormFields cff : configurationFormFields) {
            String regEx = "";
            Integer validationTypeId = cff.getValidationType();
            switch (cff.getValidationType()) {
                case 1:
                    break; // no validation
                //email calling SQL to validation and insert - one statement
                case 2:
                    errorCount = errorCount + genericValidation(cff, validationTypeId, batchUploadId, regEx);
                    break;
                //phone  calling SP to validation and insert - one statement 
                case 3:
                    errorCount = errorCount + genericValidation(cff, validationTypeId, batchUploadId, regEx);
                    break;
                // need to loop through each record / each field
                case 4:
                    errorCount = errorCount + dateValidation(cff, validationTypeId, batchUploadId);
                    break;
                //numeric   calling SQL to validation and insert - one statement      
                case 5:
                    errorCount = errorCount + genericValidation(cff, validationTypeId, batchUploadId, regEx);
                    break;
                //url - need to rethink as regExp is not validating correctly
                case 6:
                    errorCount = errorCount + urlValidation(cff, validationTypeId, batchUploadId);
                    break;
                //anything new we hope to only have to modify sp
                default:
                    errorCount = errorCount + genericValidation(cff, validationTypeId, batchUploadId, regEx);
                    break;
            }

        }
        return errorCount;
    }

    @Override
    public Integer genericValidation(configurationFormFields cff,
            Integer validationTypeId, Integer batchUploadId, String regEx) {
        return transactionInDAO.genericValidation(cff, validationTypeId, batchUploadId, regEx);
    }

    @Override
    public Integer urlValidation(configurationFormFields cff,
            Integer validationTypeId, Integer batchUploadId) {
        try {
            //1. we grab all transactionInIds for messages that are not length of 0 and not null 
            List<transactionRecords> trs = getFieldColAndValues(batchUploadId, cff);
            //2. we look at each column and check each value to make sure it is a valid url
            for (transactionRecords tr : trs) {
                //System.out.println(tr.getfieldValue());
                if (tr.getfieldValue() != null) {
                    //we append http:// if url doesn't start with it
                    String urlToValidate = tr.getfieldValue();
                    if (!urlToValidate.startsWith("http")) {
                        urlToValidate = "http://" + urlToValidate;
                    }
                    if (!isValidURL(urlToValidate)) {
                        insertValidationError(tr, cff, batchUploadId);
                    }

                }
            }
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            transactionInDAO.insertProcessingError(5, cff.getconfigId(), batchUploadId, cff.getId(), null, null, validationTypeId, false, false, (ex.getClass() + " " + ex.getCause()));
            return 1;
        }

    }

    @Override
    public Integer dateValidation(configurationFormFields cff,
            Integer validationTypeId, Integer batchUploadId) {
        try {
            //1. we grab all transactionInIds for messages that are not length of 0 and not null 
            List<transactionRecords> trs = getFieldColAndValues(batchUploadId, cff);

            //2. we look at each column and check each value by trying to convert it to a date
            for (transactionRecords tr : trs) {
                if (tr.getfieldValue() != null) {
                //sql is picking up dates in mysql format and it is not massive inserting, running this check to avoid unnecessary sql call
                    //System.out.println(tr.getFieldValue());
                    //we check long dates
                    Date dateValue = null;
                    boolean mySQLDate = chkMySQLDate(tr.getFieldValue());

                    if (dateValue == null && !mySQLDate) {
                        dateValue = convertLongDate(tr.getFieldValue());
                    }
                    if (dateValue == null && !mySQLDate) {
                        dateValue = convertDate(tr.getfieldValue());
                    }

                    String formattedDate = null;
                    if (dateValue != null && !mySQLDate) {
                        formattedDate = formatDateForDB(dateValue);
                        //3. if it converts, we update the column value
                        updateFieldValue(tr, formattedDate);
                    }

                    if (formattedDate == null && !mySQLDate) {
                        insertValidationError(tr, cff, batchUploadId);
                    }

                }
            }
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            transactionInDAO.insertProcessingError(5, cff.getconfigId(), batchUploadId, cff.getId(), null, null, validationTypeId, false, false, (ex.getClass() + " " + ex.getCause()));
            return 1;
        }

    }

    /**
     * This method updates all the length of 0 values for a particular column for a batch and configuration to null.
     *
     */
    @Override
    public void updateBlanksToNull(configurationFormFields cff, Integer batchUploadId) {
        transactionInDAO.updateBlanksToNull(cff, batchUploadId);
    }

    @Override
    public List<transactionRecords> getFieldColAndValues(Integer batchUploadId, configurationFormFields cff) {
        return transactionInDAO.getFieldColAndValues(batchUploadId, cff);
    }

    /**
     * this method checks the potential day formats that a user can send in. We will check for long days such as February 2, 2014 Wednesday etc. Only accepting US format of month - day - year February 2, 2014 Sunday 2:00:02 PM February 2, 2014 02-02-2014 02/02/2014 02/02/14 02/2/14 12:02:00 PM etc.
     */
    /**
     * this method returns the pattern date is in so we can convert it properly and translate into mysql datetime insert format
     */
    public Date convertDate(String input) {

        // some regular expression
        String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
                + "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time

        // no check for leap years (Schaltjahr)
        // and 31.02.2006 will also be correct
        String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
        String month = "((1[012])|(0\\d))"; // 01 up to 12
        String year = "\\d{4}";

        // define here all date format
        String date = input.replace("/", "-");
        date = date.replace(".", "-");
        //ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        //Pattern pattern1 = Pattern.compile(day + "-" + month + "-" + year + time); //not matching, doesn't work for 01-02-2014 is it jan or feb, will only accept us dates
        Pattern pattern2 = Pattern.compile(year + "-" + month + "-" + day + time);
        Pattern pattern3 = Pattern.compile(month + "-" + day + "-" + year + time);
        // check dates
        //month needs to have leading 0
        System.out.print(date.indexOf("-"));
        if (date.indexOf("-") == 1) {
            date = "0" + date;
        }

        if (pattern2.matcher(date).matches()) {
            //we have y-m-d format, we transform and return date
            try {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd");
                dateformat.setLenient(false);
                Date dateValue = dateformat.parse(date);
                return dateValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } else if (pattern3.matcher(date).matches()) {
            //we have m-d-y format, we transform and return date
            try {
                SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy");
                dateformat.setLenient(false);
                Date dateValue = dateformat.parse(date);
                return dateValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }

    }

    @Override
    public void updateFieldValue(transactionRecords tr, String newValue) {
        transactionInDAO.updateFieldValue(tr, newValue);
    }

    @Override
    public void insertValidationError(transactionRecords tr,
            configurationFormFields cff, Integer batchUploadId) {
        transactionInDAO.insertValidationError(tr, cff, batchUploadId);
    }

    @Override
    @Transactional
    public Integer getFeedbackReportConnection(int configId, int targetorgId) {
        return transactionInDAO.getFeedbackReportConnection(configId, targetorgId);
    }

    @Override
    public String formatDateForDB(Date date) {
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            return dateformat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Date convertLongDate(String dateValue) {

        Date date = null;
        //this checks convert long date such February 2, 2014
        try {
            date = java.text.DateFormat.getDateInstance().parse(dateValue);
        } catch (Exception e) {
        }
        return date;
    }

    public boolean chkMySQLDate(String date) {

        // some regular expression
        String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
                + "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time

        // no check for leap years (Schaltjahr)
        // and 31.02.2006 will also be correct
        String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
        String month = "((1[012])|(0\\d))"; // 01 up to 12
        String year = "\\d{4}";

        // define here all date format
        date.replace("/", "-");
        date.replace(".", "-");
        Pattern pattern = Pattern.compile(year + "-" + month + "-" + day + time);

        // check dates
        if (pattern.matcher(date).matches()) {
            try {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                dateformat.setLenient(false);
                dateformat.parse(date);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isValidURL(String url) {
        UrlValidator urlValidator = new UrlValidator();
        if (urlValidator.isValid(url)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer processCrosswalk(Integer configId, Integer batchId,
            configurationDataTranslations cdt, boolean foroutboundProcessing) {
        try {
            // 1. we get the info for that cw (fieldNo, sourceVal, targetVal rel_crosswalkData)
            List<CrosswalkData> cdList = configurationManager.getCrosswalkData(cdt.getCrosswalkId());
            //we null forcw column, we translate and insert there, we then replace
            nullForCWCol(configId, batchId, foroutboundProcessing);
            for (CrosswalkData cwd : cdList) {
                executeCWData(configId, batchId, cdt.getFieldNo(), cwd, foroutboundProcessing, cdt.getFieldId());
            }

            //we replace original F[FieldNo] column with data in forcw
            updateFieldNoWithCWData(configId, batchId, cdt.getFieldNo(), cdt.getPassClear(), foroutboundProcessing);

            //flag errors, anything row that is not null in F[FieldNo] but null in forCW
            flagCWErrors(configId, batchId, cdt, foroutboundProcessing);

            //flag as error in transactionIn or transactionOut table
            updateStatusForErrorTrans(batchId, 14, foroutboundProcessing);

            //we replace original F[FieldNo] column with data in forcw
            updateFieldNoWithCWData(configId, batchId, cdt.getFieldNo(), cdt.getPassClear(), foroutboundProcessing);

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

    }

    @Override
    public Integer processMacro(Integer configId, Integer batchId, configurationDataTranslations cdt,
            boolean foroutboundProcessing) {
        // we clear forCW column for before we begin any translation
        nullForCWCol(configId, batchId, foroutboundProcessing);
        try {
    		Macros macro = configurationManager.getMacroById(cdt.getMacroId());
    		try {
        		// we expect the target field back so we can figure out clear pass option
    			return executeMacro(configId, batchId, cdt, foroutboundProcessing, macro);
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public void nullForCWCol(Integer configId, Integer batchId, boolean foroutboundProcessing) {
        transactionInDAO.nullForCWCol(configId, batchId, foroutboundProcessing);
    }

    @Override
    public void executeCWData(Integer configId, Integer batchId, Integer fieldNo, CrosswalkData cwd, boolean foroutboundProcessing, Integer fieldId) {
        transactionInDAO.executeCWData(configId, batchId, fieldNo, cwd, foroutboundProcessing, fieldId);
    }

    @Override
    public void updateFieldNoWithCWData(Integer configId, Integer batchId, Integer fieldNo, Integer passClear, boolean foroutboundProcessing) {
        transactionInDAO.updateFieldNoWithCWData(configId, batchId, fieldNo, passClear, foroutboundProcessing);
    }

    @Override
    public void flagCWErrors(Integer configId, Integer batchId,
            configurationDataTranslations cdt, boolean foroutboundProcessing) {
        transactionInDAO.flagCWErrors(configId, batchId, cdt, foroutboundProcessing);
    }

    @Override
    public void flagMacroErrors(Integer configId, Integer batchId,
            configurationDataTranslations cdt, boolean foroutboundProcessing) {
        transactionInDAO.flagMacroErrors(configId, batchId, cdt, foroutboundProcessing);
    }


    @Override
    public Integer executeMacro(Integer configId, Integer batchId,
            configurationDataTranslations cdt, boolean foroutboundProcessing, Macros macro) {
        return transactionInDAO.executeMacro(configId, batchId, cdt, foroutboundProcessing, macro);

    }

    @Override
    public List<configurationTransport> getHandlingDetailsByBatch(int batchId) {
        try {
            return transactionInDAO.getHandlingDetailsByBatch(batchId);
        } catch (Exception e) {
            return null;
        }
    }

	@Override
	public void insertProcessingError(Integer errorId, Integer configId, Integer batchId, Integer fieldId, 
			Integer macroId, Integer cwId, Integer validationTypeId, boolean required,
			boolean foroutboundProcessing, String errorCause) {
		transactionInDAO.insertProcessingError(errorId, configId, batchId, fieldId, macroId, cwId, validationTypeId, required, foroutboundProcessing, errorCause);
		
	}

	@Override
	public void updateRecordCounts(Integer batchId, List<Integer> statusIds,
			boolean foroutboundProcessing, String colNameToUpdate) {
		transactionInDAO.updateRecordCounts(batchId, statusIds,foroutboundProcessing, colNameToUpdate);
	}
	
	@Override
	public Integer getRecordCounts (Integer batchId, List <Integer> statusIds, boolean foroutboundProcessing) {
		return transactionInDAO.getRecordCounts(batchId, statusIds,foroutboundProcessing);
	}

	@Override
	public void resetTransactionTranslatedIn(Integer batchId, boolean resetAll) {
		transactionInDAO.resetTransactionTranslatedIn(batchId, resetAll);	
	}

	@Override
	public Integer copyTransactionInStatusToTarget(Integer batchId) {
		return transactionInDAO.copyTransactionInStatusToTarget(batchId);
	}

	@Override
	public Integer insertLoadData(Integer batchId, String delimChar, String fileWithPath, String loadTableName, boolean containsHeaderRow) {
		return transactionInDAO.insertLoadData(batchId, delimChar, fileWithPath, loadTableName, containsHeaderRow);
	}
	
	
	@Override
	public Integer createLoadTable(String loadTableName) {
		return transactionInDAO.createLoadTable(loadTableName);
	}

	@Override
	public Integer dropLoadTable(String loadTableName) {
		return transactionInDAO.dropLoadTable(loadTableName);
	}
	
	@Override
	public Integer updateLoadTable(String loadTableName, Integer batchId) {
		return transactionInDAO.updateLoadTable(loadTableName, batchId);
	}

	@Override
	public Integer loadTransactionIn(String loadTableName, Integer batchId) {
		return transactionInDAO.loadTransactionIn(loadTableName, batchId);
	}
	
	@Override
	public Integer loadTransactionInRecords(Integer batchId) {
		return transactionInDAO.loadTransactionInRecords(batchId);
	}
	
	@Override
	public Integer loadTransactionInRecordsData(String loadTableName) {
		return transactionInDAO.loadTransactionInRecordsData(loadTableName);
	}
	
	@Override
	public Integer updateConfigIdForBatch(Integer batchId, Integer configId) {
		return transactionInDAO.updateConfigIdForBatch(batchId, configId);
	} 
	
	@Override
	public Integer loadTransactionTranslatedIn(Integer batchId) {
		return transactionInDAO.loadTransactionTranslatedIn(batchId);
	}

	@Override
	public Integer insertBatchUploadSummary (batchUploads batchUpload, configurationConnection batchTargets) {
		return transactionInDAO.insertBatchUploadSummary(batchUpload, batchTargets);
	}

	@Override
	public Integer insertBatchTargets(Integer batchId, configurationConnection batchTargets) {
		return transactionInDAO.insertBatchTargets(batchId, batchTargets);
	}

	@Override
	public List<configurationConnection> getBatchTargets(Integer batchId) {
		return transactionInDAO.getBatchTargets(batchId);
	}
	
	@Override
	public Integer clearBatchUploadSummary(Integer batchId) {
		return transactionInDAO.clearBatchUploadSummary(batchId);
	}
}

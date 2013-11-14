package com.ut.dph.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ut.dph.dao.configurationTransportDAO;
import com.ut.dph.model.configurationFormFields;
import com.ut.dph.model.configurationTransport;

@Service
public class configurationTransportDAOImpl implements configurationTransportDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * The 'getTransportDetails' function will return the details of the transport
	 * method for the passed in configuration id
	 * 
	 * @param configId	Holds the id of the selected configuration
	 * 
	 * @Return	This function will return a configurationTransport object
	 */
	public configurationTransport getTransportDetails(int configId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configurationTransport.class);  
	    criteria.add(Restrictions.like("configId", configId));  
	    return (configurationTransport) criteria.uniqueResult();  
 	}
	
	/**
	 * The 'updateTransportDetails' function will update the configuration transport
	 * details
	 * 
	 * @param	transportDetails	The details of the transport form
	 * @param	clearFields			This will hold a variable that will determine if we clear out
	 * 								existing configuration details.
	 * 
	 * @return	this function does not return anything
	 */
	public void updateTransportDetails(configurationTransport transportDetails, int clearFields) {
		
		//if clearFields == 1 then we need to clear out the configuration form fields, mappings and data
		//translations. This will allow the admin to change the configuration transport method after
		//one was previously selected. This will only be available while the configuration is not active.
		if(clearFields == 1) {
			//Delete the existing form fields
			Query deleteFields = sessionFactory.getCurrentSession().createSQLQuery("DELETE from configurationFormFields where configId = :configId");
			deleteFields.setParameter("configId",transportDetails.getconfigId());
			deleteFields.executeUpdate();
			
		}
		
		sessionFactory.getCurrentSession().saveOrUpdate(transportDetails);
	}
	
	/**
	* The 'getTransportMethods' function will return a list of available transport methods
	* 
	*/
	@Override
	@SuppressWarnings("rawtypes")
	@Transactional
	public List getTransportMethods() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, transportMethod FROM ref_transportMethods order by transportMethod asc");
		
		return query.list();
	}
	
	/**
	 * The 'copyMessageTypeFields' function will copy the form fields for the selected
	 * message type for the selected configuration.
	 * 
	 * @param	configId		The id of the selected configuration
	 * 			messageTypeId	The id of the selected message type to copy the form fields
	 * 
	 * @return	This function does not return anything
	 */
	@Override
	@Transactional
	public void copyMessageTypeFields(int configId, int messageTypeId) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("INSERT INTO configurationFormFields (messageTypeFieldId, configId, fieldNo, fieldDesc, fieldLabel, validationType, required, bucketNo, bucketDspPos) SELECT id, :configId, fieldNo,  fieldDesc, fieldLabel, validationType, required, bucketNo, bucketDspPos FROM messageTypeFormFields where messageTypeId = :messageTypeId");
			  query.setParameter("configId",configId);
			  query.setParameter("messageTypeId",messageTypeId);
		
		query.executeUpdate();
		
	}
	
	/**
	 * The 'getConfigurationFields' function will return a list of saved form fields for
	 * the selected configuration.
	 * 
	 * @param	configId	Will hold the id of the configuration we want to return fields for
	 * 
	 * @return	This function will return a list of configuration form fields
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<configurationFormFields> getConfigurationFields(int configId) {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configurationFormFields.class)
			.add(Restrictions.eq("configId",configId))
			.addOrder(Order.asc("bucketNo"))
	     	.addOrder(Order.asc("bucketDspPos"));
		
		return criteria.list();
	}
	
	/**
	 * The 'updateConfigurationFormFields' function will update the configuration
	 * form field settings
	 * 
	 * @param 	formField	object that will hold the form field settings
	 * 
	 * @return 	This function will not return anything
	 */
	public void updateConfigurationFormFields(configurationFormFields formField) {
		sessionFactory.getCurrentSession().update(formField);
	}
	
	
}
package com.ut.dph.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ut.dph.dao.sysAdminDAO;
import com.ut.dph.dao.UtilitiesDAO;
import com.ut.dph.model.Macros;
import com.ut.dph.model.custom.LookUpTable;
import com.ut.dph.model.custom.TableData;
import com.ut.dph.model.lutables.lu_Counties;
import com.ut.dph.model.lutables.lu_GeneralHealthStatuses;
import com.ut.dph.model.lutables.lu_GeneralHealths;
import com.ut.dph.model.lutables.lu_Immunizations;
import com.ut.dph.model.lutables.lu_Manufacturers;
import com.ut.dph.model.lutables.lu_MedicalConditions;
import com.ut.dph.model.lutables.lu_Medications;

/**
 * @see com.ut.dph.dao.sysAdminDAO
 * @author gchan
 */
@Repository
public class sysAdminDAOImpl implements sysAdminDAO {

	@Autowired
	private UtilitiesDAO udao;
	
	@Autowired
	private SessionFactory sessionFactory;

	private String schemaName = "universalTranslator";

	/** this gets a list of Lookup tables **/
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<LookUpTable> getLookUpTables(int page, int maxResults, String searchTerm) {
		Query query = sessionFactory
				.getCurrentSession()
				.createSQLQuery(
						"select  "
								+ "displayText as displayName,"
								+ "utTableName,"
								+ "count(COLUMN_NAME) as columnNum, "
								+ "urlId as urlId, "
								+ "TABLE_ROWS as rowNum, "
								+ "description "
								+ "from information_schema.tables infoT, "
								+ "information_schema.COLUMNS infoc, lookUpTables "
								+ "where infoc.TABLE_SCHEMA = :schemaName "
								+ "and lookUpTables.utTableName = infot.TABLE_NAME "
								+ "and lookUpTables.utTableName = infoc.TABLE_NAME "
								+ "and infoc.TABLE_SCHEMA = infot.TABLE_SCHEMA "
								+ "and infoc.TABLE_NAME = infot.TABLE_NAME "
								+ "and displayText like :searchTerm "
								+ "group by infoc.TABLE_NAME order by infoc.TABLE_NAME")
				.addScalar("displayName", StandardBasicTypes.STRING)
				.addScalar("utTableName", StandardBasicTypes.STRING)
				.addScalar("urlId", StandardBasicTypes.STRING)
				.addScalar("columnNum", StandardBasicTypes.INTEGER)
				.addScalar("rowNum", StandardBasicTypes.INTEGER)
				.addScalar("description", StandardBasicTypes.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(LookUpTable.class))
				.setParameter("schemaName", schemaName)
				.setParameter("searchTerm", searchTerm);
		
		//By default we want to return the first result
	    int firstResult = 0;
		
		//If viewing a page other than the first we then need to figure out
	    //which result to start with
		if(page > 1) {
			firstResult = (maxResults*(page-1));
		}
		
		/** add codes for paging **/
		query.setFirstResult(firstResult);
		//Set the max results to display
		query.setMaxResults(maxResults);
		
		List<LookUpTable> tableList = query.list();
		
		return tableList;

	}

	/** this method returns the number of look up tables in the system **/
	@Override
	@Transactional
	public Integer findTotalLookUpTable() {

		Query query = sessionFactory
				.getCurrentSession()
				.createSQLQuery(
						"select count(*) as totalLookUpTables from lookUpTables").addScalar("totalLookUpTables", StandardBasicTypes.INTEGER);
		Integer totalTables = (Integer) query.list().get(0);

		return totalTables;
	}

	/**
	 * this method takes the table name and searchTerm (if there is one) and
	 * return the data in the table
	 **/

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<TableData> getDataList(int page, int maxResults, String utTableName, String searchTerm) {
		
		String sql = "select id, displayText, description, "
				+ " isCustom as custom, status as status, dateCreated as dateCreated from "
				+ utTableName +  " where (displayText like :searchTerm or description like :searchTerm) order by id";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
		.addScalar("id",StandardBasicTypes.INTEGER )
		.addScalar("displayText",StandardBasicTypes.STRING )
		.addScalar("description",StandardBasicTypes.STRING )
		.addScalar("custom",StandardBasicTypes.BOOLEAN)
		.addScalar("status",StandardBasicTypes.BOOLEAN)
		.addScalar("dateCreated",StandardBasicTypes.DATE)
		.setResultTransformer(Transformers.aliasToBean(TableData.class))
		.setParameter("searchTerm",searchTerm);
		
		//By default we want to return the first result
	    int firstResult = 0;
		
		//If viewing a page other than the first we then need to figure out
	    //which result to start with
		if(page > 1) {
			firstResult = (maxResults*(page-1));
		}
		/** add codes for paging **/
		query.setFirstResult(firstResult);
		//Set the max results to display
		query.setMaxResults(maxResults);
		
		List<TableData> dataList = query.list();
		// TODO
		/** add codes for paging **/

		return dataList;

	}

	@Override
	@Transactional
	public Integer findTotalDataRows(String utTableName) {
		String sql = "select count(*) as rowCount from " + utTableName;
		Query query = sessionFactory
				.getCurrentSession()
				.createSQLQuery(sql).addScalar("rowCount", StandardBasicTypes.INTEGER);
		Integer rowCount = (Integer) query.list().get(0);

		return rowCount;
	}

	@Override
	@Transactional
	public LookUpTable getTableInfo(String urlId) {
	
		LookUpTable lookUpTable = new LookUpTable();
		Query query = sessionFactory.getCurrentSession().createSQLQuery(""
				+ "select utTableName, "
				+ "displayText as displayName, "
				+ "urlId, description, "
				+ "dateCreated from lookUpTables where urlId = :urlId")
				.addScalar("utTableName",StandardBasicTypes.STRING )
				.addScalar("displayName",StandardBasicTypes.STRING )
				.addScalar("urlId",StandardBasicTypes.STRING )
				.addScalar("description",StandardBasicTypes.STRING)
				.addScalar("dateCreated",StandardBasicTypes.DATE).setResultTransformer(
						Transformers.aliasToBean(LookUpTable.class)).setParameter("urlId", urlId);
		
		if (query.list().size() == 1) {
			lookUpTable = (LookUpTable) query.list().get(0);
		}
		
		return lookUpTable;

	}

	/** this method deletes the data item in the table**/ 
	@Override
	@Transactional
	public boolean deleteDataItem(String utTableName, int id) {
				String sql  = "delete from " + utTableName + " where id = :id";
				Query deleteTable = sessionFactory.getCurrentSession().createSQLQuery(sql)
						.addScalar("id",StandardBasicTypes.INTEGER ).setParameter("id", id);
				try {
					deleteTable.executeUpdate();
					return true;
				} catch (Throwable ex) {
	                System.err.println("deleteDataItem failed." + ex);
	                return false;
	                	
				}
	}

	@Override
	@Transactional
	public TableData getTableData (Integer id, String utTableName) {
		//we create sql, we transform
		TableData tableData = new TableData();
		String sql = ("select id, displayText, description, isCustom as custom, "
				+ "status "
				+ " from " + utTableName + " where id = :id");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("id",StandardBasicTypes.INTEGER)
				.addScalar("displayText",StandardBasicTypes.STRING)
				.addScalar("description",StandardBasicTypes.STRING)
				.addScalar("custom",StandardBasicTypes.BOOLEAN)
				.addScalar("status",StandardBasicTypes.BOOLEAN).setResultTransformer(
						Transformers.aliasToBean(TableData.class)).setParameter("id", id);
		
		if (query.list().size() == 1) {
			tableData = (TableData) query.list().get(0);
		}
		return tableData;

	}

	/** 
	 * need table name to be dynamic but java complies and hibernate will not allow 
	 * dynamic table name. 
	 * Need to accurately return the id generated and select (max) or sql = 'inserted display text'
	 * is not a good idea.
	 * For inserts that are dynamic, we will use old fashion JDBC sql 
	 * 
	 * */
	

	@Override
	public Integer createTableData(TableData tableData, String utTableName) {
		Integer tableDataId= 0;
		
		   Connection conn = null;
		   PreparedStatement pst = null;
		   ResultSet rs = null;
		   
		     
		try {
			conn = udao.getConnection();
			String sql  = "insert into " + utTableName + 
				" (displayText, description, isCustom, status) "
				+ "values (?, ?,?, ?)";
		
			pst =
				conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, tableData.getDisplayText());
			pst.setString(2, tableData.getDescription());
			pst.setBoolean(3, tableData.isCustom());
			pst.setBoolean(4, tableData.isStatus());
			
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();			
			if (rs.next()) {
				tableDataId = rs.getInt(1);
			} 
			
		} catch (Exception ex) {
            System.err.println("insert table data failed." + ex);
		}
		finally
	    {
			try
			{
			if(rs!=null)
				rs.close();
			if(pst!=null)
				pst.close();
			if(conn!=null)
				conn.close();
			}
	        catch ( SQLException e ) {
	          System.out.println("Error: createData: - close connection: " + e);
	        }
	    }
		
		return tableDataId;
	}

	@Override
	@Transactional
	public boolean updateTableData(TableData tableData, String utTableName) {
		boolean updated = false;
		String sql  = "update " + utTableName
				+ " set displayText = :displayText, "
				+ "description = :description, "
				+ "status = :status, "
				+ "isCustom = :isCustom "
				+ "where id = :id ";
		Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("displayText",StandardBasicTypes.STRING)
				.addScalar("description",StandardBasicTypes.STRING)
				.addScalar("isCustom", StandardBasicTypes.BOOLEAN)
				.addScalar("status", StandardBasicTypes.BOOLEAN)
				.addScalar("id",StandardBasicTypes.INTEGER)
				.setParameter("displayText", tableData.getDisplayText())
				.setParameter("description", tableData.getDescription())
				.setParameter("isCustom", tableData.isCustom())
				.setParameter("status", tableData.isStatus())	
				.setParameter("id", tableData.getId())
				;
		try {
			updateData.executeUpdate();
			updated =true;
		} catch (Throwable ex) {
            System.err.println("update table data failed." + ex);
		}
		return updated;
		
	}

		@Override
		@Transactional
		public void createTableDataHibernate(TableData tableData, String utTableName) {
		
			String sql  = "insert into " + utTableName + " (displayText, description, isCustom, status) "
					+ "values (:displayText, :description, :isCustom, :status)";
			Query insertData = sessionFactory.getCurrentSession().createSQLQuery(sql)
					.addScalar("displayText",StandardBasicTypes.STRING)
					.addScalar("description",StandardBasicTypes.STRING)
					.addScalar("isCustom", StandardBasicTypes.BOOLEAN)
					.addScalar("status", StandardBasicTypes.BOOLEAN)
					.setParameter("displayText", tableData.getDisplayText())
					.setParameter("description", tableData.getDescription())
					.setParameter("isCustom", tableData.isCustom())
					.setParameter("status", tableData.isStatus())		
					;
			try {
				insertData.executeUpdate();
			} catch (Throwable ex) {
	            System.err.println("insert table data failed." + ex);
			}
		}

		 @SuppressWarnings("unchecked")
		 @Override
		 @Transactional
		public List<Macros> getMarcoList(int maxResults, int page, String searchTerm) {
			 
			Query query = sessionFactory.getCurrentSession().createQuery("from Macros where "
					+ "macro_short_name like :searchTerm "
					+ "order by category asc");
			query.setParameter("searchTerm",searchTerm);
			
			//By default we want to return the first result
		    int firstResult = 0;
			
			//If viewing a page other than the first we then need to figure out
		    //which result to start with
			if(page > 1) {
				firstResult = (maxResults*(page-1));
			}
			/** codes for paging **/
			query.setFirstResult(firstResult);
			//Set the max results to display
			query.setMaxResults(maxResults);
			return query.list();
		}

		@Override
		@Transactional
		public Long findTotalMacroRows() {
			Query query = sessionFactory.getCurrentSession().createQuery("select count(*) as totalMacros from Macros");
			Long totalMacros = (Long) query.uniqueResult();
			return totalMacros;
		}
	
		/** this method deletes the macro in the table**/ 
		@Override
		@Transactional
		public boolean deleteMacro(int id) {
			Query deletMarco = sessionFactory.getCurrentSession().createQuery("delete from Macros where id = :macroId)");
			deletMarco.setParameter("macroId", id);
			deletMarco.executeUpdate();
					try {
						deletMarco.executeUpdate();
						return true;
					} catch (Throwable ex) {
		                System.err.println("delete macro failed." + ex);
		                return false;  	
					}
		}
		
		/** this method adds a macro**/ 
		@Override
		@Transactional
		public void createMacro(Macros macro) {
				try {
						sessionFactory.getCurrentSession().save(macro);						
					} catch (Throwable ex) {
		                System.err.println("create macro failed." + ex);
		             	
					}
		}
		
		@Override
		@Transactional
		public boolean updateMacro(Macros macro) {
				try {
					sessionFactory.getCurrentSession().update(macro);
					return true;
					} catch (Throwable ex) {
		                System.err.println("update macro failed." + ex);
		                return false;
		            }
		}

		@Override
		@Transactional
		public void createCounty(lu_Counties luc) {
			try {
				sessionFactory.getCurrentSession().save(luc);						
			} catch (Throwable ex) {
                System.err.println("create county failed." + ex);
             	
			}
			
		}

		@Override
		@Transactional
		public lu_Counties getCountyById(int id) {
			return (lu_Counties) sessionFactory.getCurrentSession().get(lu_Counties.class, id);
		}

		@Override
		@Transactional
		public void updateCounty(lu_Counties luc) {
			 sessionFactory.getCurrentSession().update(luc);
		}

		@Override
		@Transactional
		public void createGeneralHealth(lu_GeneralHealths lu) {
			try {
				sessionFactory.getCurrentSession().save(lu);						
			} catch (Throwable ex) {
                System.err.println("create general health failed." + ex);
			}
		}

		@Override
		@Transactional
		public lu_GeneralHealths getGeneralHealthById(int id) {
			try {
				return (lu_GeneralHealths) sessionFactory.getCurrentSession().get(lu_GeneralHealths.class, id);						
			} catch (Throwable ex) {
                System.err.println("get general health failed." + ex);
                return null;
			}
		}

		@Override
		@Transactional
		public void updateGeneralHealth(lu_GeneralHealths lu) {
			try {
				sessionFactory.getCurrentSession().update(lu);							
			} catch (Throwable ex) {
                System.err.println("update general health failed." + ex);
			}
		}

		@Override
		@Transactional
		public void createGeneralHealthStatus(lu_GeneralHealthStatuses lu) {
			try {
				sessionFactory.getCurrentSession().save(lu);						
			} catch (Throwable ex) {
                System.err.println("create general health status failed." + ex);
			}	
		}

		@Override
		@Transactional
		public lu_GeneralHealthStatuses getGeneralHealthStatusById(int id) {
			try {
				return (lu_GeneralHealthStatuses) sessionFactory.getCurrentSession().get(lu_GeneralHealthStatuses.class, id);						
			} catch (Throwable ex) {
                System.err.println("get general health status failed." + ex);
                return null;
			}
		}

		@Override
		@Transactional
		public void updateGeneralHealthStatus(lu_GeneralHealthStatuses lu) {
			try {
				sessionFactory.getCurrentSession().update(lu);							
			} catch (Throwable ex) {
                System.err.println("update general health status failed." + ex);
			}	
		}

		@Override
		@Transactional
		public void createImmunization(lu_Immunizations lu) {
			try {
				sessionFactory.getCurrentSession().save(lu);						
			} catch (Throwable ex) {
                System.err.println("create Immunization failed." + ex);
			}
		}

		@Override
		@Transactional
		public lu_Immunizations getImmunizationById(int id) {
			try {
				return (lu_Immunizations) sessionFactory.getCurrentSession().get(lu_Immunizations.class, id);						
			} catch (Throwable ex) {
                System.err.println("get Immunization failed." + ex);
                return null;
			}
		}

		@Override
		@Transactional
		public void updateImmunization(lu_Immunizations lu) {
			try {
				sessionFactory.getCurrentSession().update(lu);							
			} catch (Throwable ex) {
                System.err.println("update Immunization failed." + ex);
			}
		}

		@Override
		@Transactional
		public void createManufacturer(lu_Manufacturers lu) {
			try {
				sessionFactory.getCurrentSession().save(lu);						
			} catch (Throwable ex) {
                System.err.println("create Manufacturer failed." + ex);
			}
		}

		@Override
		@Transactional
		public lu_Manufacturers getManufacturerById(int id) {
			try {
				return (lu_Manufacturers) sessionFactory.getCurrentSession().get(lu_Manufacturers.class, id);						
			} catch (Throwable ex) {
                System.err.println("get Manufacturers failed." + ex);
                return null;
			}
		}

		@Override
		@Transactional
		public void updateManufacturer(lu_Manufacturers lu) {
			try {
				sessionFactory.getCurrentSession().update(lu);							
			} catch (Throwable ex) {
                System.err.println("update Manufacturers failed." + ex);
			}
		}
		
		@Override
		@Transactional
		public void createMedicalCondition(lu_MedicalConditions lu) {
			try {
				sessionFactory.getCurrentSession().save(lu);						
			} catch (Throwable ex) {
                System.err.println("create medical condition failed." + ex);
			}
		}

		@Override
		@Transactional
		public lu_MedicalConditions getMedicalConditionById(int id) {
			try {
				return (lu_MedicalConditions) sessionFactory.getCurrentSession().get(lu_MedicalConditions.class, id);						
			} catch (Throwable ex) {
                System.err.println("get Medical Condition failed." + ex);
                return null;
			}
		}

		@Override
		@Transactional
		public void updateMedicalCondition(lu_MedicalConditions lu) {
			try {
				sessionFactory.getCurrentSession().update(lu);							
			} catch (Throwable ex) {
                System.err.println("update Medical Condition failed." + ex);
			}
		}
		
		@Override
		@Transactional
		public void createMedication(lu_Medications lu) {
			try {
				sessionFactory.getCurrentSession().save(lu);						
			} catch (Throwable ex) {
                System.err.println("create Medication failed." + ex);
			}
		}

		@Override
		@Transactional
		public lu_Medications getMedicationById(int id) {
			try {
				return (lu_Medications) sessionFactory.getCurrentSession().get(lu_Medications.class, id);						
			} catch (Throwable ex) {
                System.err.println("get Medication failed." + ex);
                return null;
			}
		}

		@Override
		@Transactional
		public void updateMedication(lu_Medications lu) {
			try {
				sessionFactory.getCurrentSession().update(lu);							
			} catch (Throwable ex) {
                System.err.println("update Medication failed." + ex);
			}
		}
		
}

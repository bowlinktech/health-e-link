package com.ut.dph.dao;

import java.sql.Connection;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilitiesDAO {
	
	Connection getConnection();

}

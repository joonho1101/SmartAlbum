package com.sa.db.layout;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;

import com.sa.db.layout.data.Photo;


public class PhotoDAO extends GenericDAO<Photo>{

	public PhotoDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(Photo.class, tableName, cp);
	}
}

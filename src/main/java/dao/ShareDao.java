package dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import service.Pmap;

public class ShareDao{
	private static Logger logger = Logger.getLogger(ShareDao.class);

	private String sql;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
    
	public Integer Save(Connection conn, String sql, Object[] parms, int[] types) {

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			for (int i = 0; i < parms.length; i++) {
				if (types[i] == Types.VARCHAR) {
					ps.setString(i + 1, (String) parms[i]);
				} else if (types[i] == Types.INTEGER) {
					ps.setInt(i + 1, (Integer) parms[i]);
				} else if (types[i] == Types.DOUBLE) {
					ps.setDouble(i + 1, (Double) parms[i]);

				} else {
					ps.setNull(i + 1, Types.NULL);
				}
			}
			Integer num = ps.executeUpdate();
			ps.close();
			return num;
		} catch (Exception e) {
			return 0;
		}

	}
	public int Delete(Connection conn, String sql, Object[] parms, int[] types) {

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			for (int i = 0; i < parms.length; i++) {
				if (types[i] == Types.VARCHAR) {
					ps.setString(i + 1, (String) parms[i]);
				} else if (types[i] == Types.INTEGER) {
					ps.setInt(i + 1, (Integer) parms[i]);
				} else if (types[i] == Types.DOUBLE) {
					ps.setDouble(i + 1, (Double) parms[i]);

				} else {
					ps.setNull(i + 1, Types.NULL);
				}
			}
			Integer num = ps.executeUpdate();
			ps.close();
			return num;
		} catch (Exception e) {
			return 0;
		}

	}
	
	public <T> List<T> getResultList(Connection conn, String sql,
			Class<T> type, Object[] parms, int[] types) {
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			logger.debug(sql);
			for (int i = 0; i < types.length; i++) {
				if (types[i] == Types.VARCHAR) {
					ps.setString(i + 1, (String) parms[i]);
					logger.debug((String) parms[i]);
				} else if (types[i] == Types.INTEGER) {
					ps.setInt(i + 1, (Integer) parms[i]);
					logger.debug((String) parms[i]);
				} else if (types[i] == Types.DOUBLE) {
					ps.setDouble(i + 1, (Double) parms[i]);
					logger.debug((String) parms[i]);

				} else {
					ps.setNull(i + 1, Types.NULL);
					logger.debug((String) parms[i]);
				}
			}

			List<T> list = new ArrayList<T>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				T tempobj = (T) type.newInstance();
				Method[] methods = tempobj.getClass().getMethods();
				for (Method m : methods) {
					Annotation[] ans = m.getAnnotations();
					if (ans.length > 0) {
						Annotation Tag = ans[0];
						if (Tag instanceof Pmap) {
							String tableRowName = ((Pmap) Tag).TableName();
							String rowType = ((Pmap) Tag).TypeName();
							if (rowType.equals("String")) {
								String parm = rs.getString(tableRowName);
								m.invoke(tempobj, parm);
							} else if (rowType.equals("Integer")) {
								Integer parm = Integer.parseInt(rs
										.getString(tableRowName));
								m.invoke(tempobj, parm);
							}
						}

					}

				}
				list.add(tempobj);

			}

			logger.debug("size = " + list.size());
			ps.close();
			rs.close();
			return list;
		} catch (Exception e) {
			return  null;
		}
	}

}

package dao;

import java.sql.Connection;
import java.sql.Types;
import java.util.List;

import cglibProxy.CglibProxy;
import model.User;
public class UserDao extends ShareDao {
	public boolean save(String email, String password) {
		    Connection conn = (Connection) CglibProxy.getTl().get();
		    try {
			String sql = "select * from user where email = ?";
			Object[] objects = new Object[2];
			objects[0] = email;
			if (getResultList(conn, sql, User.class, objects,new int[] { Types.VARCHAR }).size() == 0) {
				String insql = "insert into user(email,password) values(?,?)";
				objects[0] = email;
				objects[1] = password;
				if (Save(conn, insql, objects, new int[] { Types.VARCHAR,Types.VARCHAR }) == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public User getUserByEmail(String email) {
		try {
			Connection conn = (Connection) CglibProxy.getTl().get();
			String sql = "select * from user u where u.email = ?";
			Object[] objects = new Object[1];
			objects[0] = email;
			List<User> list = getResultList(conn, sql, User.class, objects,
					new int[] { Types.VARCHAR });
			if (list == null || list.size() == 0) {
				return null;
			} else {
				return list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

}

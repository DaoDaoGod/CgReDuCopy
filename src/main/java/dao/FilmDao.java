package dao;

import java.sql.Connection;
import java.sql.Types;
import java.util.List;

import model.Film;

import org.apache.log4j.Logger;

import cglibProxy.CglibProxy;
import control.Transaction;

public class FilmDao extends ShareDao {
	private static Logger logger = Logger.getLogger(FilmDao.class);
	public List<Film> getFilmByCon(String searchText) {
		Connection conn = (Connection) CglibProxy.getTl().get();
		String sql = "SELECT * FROM film f WHERE f.name LIKE ?";
		Object[] objects = new Object[1];
		objects[0] = "%" + searchText + "%";
		return getResultList(conn, sql, Film.class, objects,
				new int[] { Types.VARCHAR });
	}

	public Integer saveNewFilm(String name) {
		Connection conn = (Connection) CglibProxy.getTl().get();
		String sql = "INSERT INTO FILM(name) values(?)";
		Object[] args = new Object[1];
		args[0] = name;
		logger.debug("saveNewFilm: " + conn);
		int changenum = Save(conn, sql, args, new int[] { Types.VARCHAR });
		return changenum;
	}

	public Integer saveIndexFilm(int ind, String name) {
		Connection conn = (Connection) CglibProxy.getTl().get();
		String sql = "INSERT INTO FILM(film_id,name) values(?,?)";
		Object[] args = new Object[2];
		args[0] = ind;
		args[1] = name;
		int changenum = Save(conn, sql, args, new int[] { Types.INTEGER,Types.VARCHAR });
		return changenum;
	}

	public Integer deleteFilm(int ind) {
		Connection conn = (Connection) CglibProxy.getTl().get();
		String sql = "delete from film where film_id = ?";
		Object[] args = new Object[1];
		args[0] = ind;
		int Changenum = Delete(conn, sql, args, new int[] { Types.INTEGER });
		return Changenum;
	}

	@Transaction
	public Integer FailedFilm(int saveId, int deleteId) {
		try {
			Connection conn = (Connection) CglibProxy.getTl().get();
			int ch1, ch2, ch3;
			ch1 = saveNewFilm("FireStrom");
			ch2 = saveIndexFilm(saveId, "FILM");
			ch3 = deleteFilm(deleteId);
			System.out.println("ch1 =" + ch1 + "ch2 =" + ch2 + "ch3=" + ch3);
			if (ch1 == 0 || ch2 == 0 || ch3 == 0) {
				return null;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return -1;
		}
	}

}

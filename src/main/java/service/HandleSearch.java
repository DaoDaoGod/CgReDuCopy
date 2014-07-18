package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cglibProxy.CglibProxy;
import dao.FilmDao;
import model.Film;

public class HandleSearch {
	
	public  @Pdao(Dao="dao.FilmDao",Method="setFilmDao")  FilmDao  filmDao;
	
	
	public FilmDao getFilmDao() {
		return filmDao;
	}


	public void setFilmDao(FilmDao filmDao) {
		this.filmDao = filmDao;
	}


	public String search(@Pname(name="searchText")String searchText,HttpServletRequest request,HttpServletResponse response)
	{
		
		try {
			if (searchText.compareTo("") != 0) {
				List<Film> films = new ArrayList<Film>();
				films=filmDao.getFilmByCon(searchText);
				if(films!=null)
				{
				List<String> strs=new ArrayList<String>();
				for (int i = 0; i < films.size(); i++) {
					strs.add(films.get(i).getName());
				}
				  request.setAttribute("my-data1",strs);
				}
			} else {
	
			      request.setAttribute("my-data2","search is empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/my.jsp";
	}
public String demo(@Pname(name="saveid")String saveid,@Pname(name="deleteid") String deleteid,HttpServletRequest request,HttpServletResponse response)
	{
		
		try {
			if (!saveid.equals("")&&!deleteid.equals("")) {
				List<Film> films = new ArrayList<Film>();
				int saveId=Integer.parseInt(saveid);
				Integer deleteId=Integer.parseInt(deleteid);
				System.out.println("saveId:"+saveid);
				Integer state=filmDao.FailedFilm(saveId, deleteId);
				if(state!=null&&state.equals(0))
				{
				  request.setAttribute("my-data2","Success");
				  System.out.println("Success");
				}
				else {
				   request.setAttribute("my-data2", "Failed,RollBack");
				   System.out.println("Failed");
				}
			} else {
	
			      request.setAttribute("my-data2","Query is empty");
			      System.out.println("Query is empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/logininfo.jsp";
	}	
 

}

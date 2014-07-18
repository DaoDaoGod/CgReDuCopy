package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDao;

public class HandleAddFilm{

	@Pdao(Dao="dao.FilmDao",Method="setFilmDao")
    private FilmDao filmDao;
	
	public FilmDao getFilmDao() {
		return filmDao;
	}

	public void setFilmDao(FilmDao filmDao) {
		this.filmDao = filmDao;
	}

	public String addFilm(@Pname(name="filmname") String name,HttpServletRequest request,HttpServletResponse response) {
	// TODO Auto-generated method stub
	try {
		
		if (name.compareTo("") != 0 ) {

			int state = filmDao.saveNewFilm(name);
			if (state>=0) {
				request.setAttribute("my-data2","Successful");
			} else {
				request.setAttribute("my-data2","Failed");
			}
		} else {
			    request.setAttribute("my-data2","film info error");
		}

	} catch (Exception e) {
		// TODO: handle exception
	}
	return "/logininfo.jsp";

}
	 

}

package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.apache.log4j.Logger;

import dao.UserDao;

public class HandleLogin {
	@Pdao(Dao="dao.UserDao",Method="setUserDao")
	private UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	private static Logger logger = Logger.getLogger(HandleLogin.class);
	public String login(@Pname(name = "loginname") String name,
			@Pname(name = "loginpassword") String password,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (name.compareTo("") != 0 && password.compareTo("") != 0) {
				UserDao userdao = new UserDao();
				User newuser = userDao.getUserByEmail(name);
				//logger.debug(newuser.getPassword());
				if (newuser!=null&&newuser.getPassword().equals(password)) {
					request.setAttribute("my-data2","Successful");
				} else {
					request.setAttribute("my-data2","Failed");
				}
			    } else {
			    	request.setAttribute("my-data2","User info error");

			    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/logininfo.jsp";
	}

}

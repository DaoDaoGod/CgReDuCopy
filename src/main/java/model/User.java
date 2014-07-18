package model;

import service.Pmap;
import service.Pname;

public class User {
	private Integer userId;
    private String email;
    private String password;
	public Integer getUserId() {
		return userId;
	}
	@Pmap(TableName="user_id",TypeName="Integer")
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	@Pmap(TableName="email",TypeName="String")
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	@Pmap(TableName="password",TypeName="String")
	public void setPassword(String password) {
		this.password = password;
	}
    
}

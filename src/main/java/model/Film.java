package model;

import service.Pmap;
import service.Pname;

public class Film {
	private Integer FilmId;
	private String Name;
	private String Description;
	private String DouBanUrl;
	
	public Integer getFilmId() {
		return FilmId;
	}
	@Pmap(TableName="film_id",TypeName="Integer")
	public void setFilmId(Integer filmId) {
		FilmId = filmId;
	}
	public String getName() {
		return Name;
	}
	@Pmap(TableName="name",TypeName="String")
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	@Pmap(TableName="description",TypeName="String")
	public void setDescription(String description) {
		Description = description;
	}
	public String getDouBanUrl() {
		return DouBanUrl;
	}
	@Pmap(TableName="filmUrl",TypeName="String")
	public void setDouBanUrl(String douBanUrl) {
		DouBanUrl = douBanUrl;
	}
	

}

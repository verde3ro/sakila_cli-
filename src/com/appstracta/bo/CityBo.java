package com.appstracta.bo;

import com.appstracta.bean.CityBean;
import com.appstracta.dao.CityDao;
import com.appstracta.exceptions.InternalException;

import java.util.List;

public class CityBo {

	private final CityDao cityDao;

	public CityBo() {
		this.cityDao = new CityDao();
	}

	public List<CityBean> obtenerTodos() throws InternalException {
		try {
			return this.cityDao.obtenerTodos();
		} catch (InternalException ex) {
			throw ex;
		}
	}

}

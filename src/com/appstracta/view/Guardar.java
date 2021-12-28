package com.appstracta.view;

import com.appstracta.bean.CityBean;
import com.appstracta.bo.CityBo;
import com.appstracta.exceptions.InternalException;

import java.util.Date;

public class Guardar {

	public static void main(String[] args) {
		try {
			CityBo cityBo = new CityBo();

			CityBean cityBean = cityBo.guardar("Querétaro", 50, new Date());
			System.out.println(cityBean);
		} catch (InternalException ex) {
			System.out.println(ex.getMessage());
		}
	}

}

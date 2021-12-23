package com.appstracta.view;

import com.appstracta.bean.CityBean;
import com.appstracta.bo.CityBo;
import com.appstracta.exceptions.InternalException;

import java.util.Date;

public class Actualizar {
	public static void main(String[] args) {
		try {
			CityBo cityBo = new CityBo();
			CityBean cityBean = cityBo.actualizar(601, "Celaya", 50, new Date());

			System.out.println(cityBean);
		} catch (InternalException ex) {
			System.out.println(ex.getMessage());
		}
	}
}

package com.appstracta.view;

import com.appstracta.bean.CityBean;
import com.appstracta.bo.CityBo;
import com.appstracta.exceptions.InternalException;

public class ObtenerPorId {

	public static void main(String[] args) {
		try {
			CityBo cityBo = new CityBo();
			CityBean cityBean = cityBo.obtenerPorId(500);
			System.out.println(cityBean);
		} catch (InternalException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println("ócurrio un error");
		}
	}

}

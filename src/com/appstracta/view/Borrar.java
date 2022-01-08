package com.appstracta.view;

import com.appstracta.bo.CityBo;
import com.appstracta.exceptions.InternalException;

public class Borrar {

	public static void main(String[] args) {
		CityBo cityBo = new CityBo();

		try {
			Integer cityId = cityBo.borrar(601);
			System.out.printf("El id %d de ciudad se borro corectamente", cityId);
		} catch (InternalException ex) {
			System.out.println(ex.getMessage());
		}
	}

}

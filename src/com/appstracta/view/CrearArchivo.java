package com.appstracta.view;

import com.appstracta.bo.CityBo;
import com.appstracta.exceptions.InternalException;

public class CrearArchivo {

	public static void main(String[] args) {
		try {
			CityBo cityBo = new CityBo();

			cityBo.crearArchivo();
			System.out.println("Termino generación...");
		} catch (InternalException ex){
			System.out.println(ex.getMessage());
		}
	}

}

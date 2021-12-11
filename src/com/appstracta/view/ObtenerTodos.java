package com.appstracta.view;

import com.appstracta.bean.CityBean;
import com.appstracta.bo.CityBo;
import com.appstracta.exceptions.InternalException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ObtenerTodos {

	public static void main(String[] args) {
		try {
			CityBo cityBo = new CityBo();

			List<CityBean> ciudades = cityBo.obtenerTodos();

			ciudades.forEach(System.out::println);

			try (FileWriter writer = new FileWriter("ciudades.txt")) {
				ciudades.forEach(ciudad -> {
					try {
						writer.write(ciudad + System.lineSeparator());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (InternalException | IOException ex) {
			System.out.println(ex.getMessage());
		}

	}

}

package com.appstracta.dao;

import com.appstracta.bean.CityBean;
import com.appstracta.bean.CountryBean;
import com.appstracta.exceptions.InternalException;
import com.appstracta.utils.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CityDao {

	public List<CityBean> obtenerTodos() throws InternalException {
		Conexion conexion = null;
		List<CityBean> ciudades = new ArrayList<>();

		try {
			String sql = "SELECT city.city_id," +
					" city.city," +
					" city.country_id," +
					" city.last_update," +
					" country.country_id," +
					" country.country, " +
					" country.last_update AS last_update_country " +
					"FROM country " +
					"INNER JOIN city  ON country.country_id = city.country_id " +
					"ORDER BY city.city_id";
			conexion = new Conexion();
			conexion.connectar();

			try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
				try (ResultSet rs = ps.executeQuery()){
					while (rs.next()) {
						CityBean cityBean = new CityBean();

						cityBean.setCityId(rs.getInt("city_id"));
						cityBean.setCity(rs.getString("city"));
						cityBean.setLastUpdate(rs.getDate("last_update"));
						cityBean.setCountry(new CountryBean(rs.getInt("country_id"), rs.getString("country"), rs.getDate("last_update_country")));

						ciudades.add(cityBean);
					}
				}
			}

			return ciudades;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InternalException("Ocurrió un error al obtener los datos de ciudades.");
		} finally {
			if (conexion != null) {
				try {
					conexion.cerrar();
				} catch (Exception ex) {
					throw new InternalException(ex.getMessage());
				}
			}
		}
	}

}

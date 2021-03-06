package com.appstracta.bo;

import com.appstracta.bean.CityBean;
import com.appstracta.bean.CountryBean;
import com.appstracta.dao.CityDao;
import com.appstracta.exceptions.InternalException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
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

	public CityBean obtenerPorId(Integer cityId) throws InternalException {
		if (null != cityId) {
			try{
				CityBean cityBean = this.cityDao.obtenerPorId(cityId);

				if (null != cityBean.getCityId()) {
					return cityBean;
				} else {
					throw new InternalException(String.format("El id %d de ciudad no se encuentra registrado.", cityId));
				}
			} catch (InternalException ex) {
				throw ex;
			}
		} else {
			throw new InternalException("El id de la ciudad no debe se nulo o vacío.");
		}
	}

	public CityBean guardar (String city, Integer countryId, Date lastUpdate) throws InternalException {
		if (null != city && !city.trim().isEmpty() && countryId != null && countryId > 0 && null != lastUpdate) {
			try {
				return cityDao.guardar(this.setearValores(null, city, countryId, lastUpdate));
			} catch (InternalException ex) {
				throw ex;
			}
		} else {
			throw new InternalException("La ciudad y el id de pais no deben ser nulos o vaciós.");
		}
	}

	public CityBean actualizar (Integer cityId, String city, Integer countryId, Date lastUpdate) throws InternalException {
		if (null != city && !city.trim().isEmpty() && countryId != null && countryId > 0 && null != lastUpdate) {
			try {
				return cityDao.actualizar(this.setearValores(cityId, city, countryId, lastUpdate));
			} catch (InternalException ex) {
				throw ex;
			}
		} else {
			throw new InternalException("La ciudad y el id de pais no deben ser nulos o vaciós.");
		}
	}

	public Integer borrar (Integer cityId) throws InternalException {
		if (cityId != null && cityId > 0) {
			try {
				return cityDao.borrar(cityId);
			} catch (InternalException ex) {
				throw ex;
			}
		} else {
			throw new InternalException("El id e la ciudad no deben ser nulo o vacío.");
		}
	}

	public void crearArchivo() throws InternalException {
		try {
			List<CityBean> ciudades = this.cityDao.obtenerTodos();

			try(FileWriter writer = new FileWriter("Ciudades.txt")) {
				for (CityBean ciudad : ciudades) {
					writer.write(ciudad + System.lineSeparator());
				}
			}
		} catch (InternalException ex) {
			throw ex;
		} catch (IOException e) {
			throw new InternalException("Ócurrio un erro al generar el archivo");
		}
	}

	private CityBean setearValores(Integer cityId, String city, Integer countryId, Date lastUpdate) {
		CityBean cityBean = new CityBean();
		cityBean.setCityId(cityId);
		cityBean.setCity(city);
		cityBean.setCountry(new CountryBean(countryId));
		cityBean.setLastUpdate(lastUpdate);

		return cityBean;
	}

}

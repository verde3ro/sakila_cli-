package com.appstracta.dao;

import com.appstracta.bean.CityBean;
import com.appstracta.bean.CountryBean;
import com.appstracta.exceptions.InternalException;
import com.appstracta.utils.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
                try (ResultSet rs = ps.executeQuery()) {
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

    public CityBean obtenerPorId(Integer cityId) throws InternalException {
        Conexion conexion = null;
        CityBean cityBean = new CityBean();

        try {
            String sql = "SELECT city.city_id," +
                    " city.city," +
                    " city.country_id," +
                    " city.last_update," +
                    " country.country_id," +
                    " country.country, " +
                    " country.last_update AS last_update_country " +
                    "FROM country " +
                    "INNER JOIN city ON country.country_id = city.country_id " +
                    "WHERE city.city_id = ?";
            conexion = new Conexion();
            conexion.connectar();

            try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {
                ps.setInt(1, cityId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cityBean.setCityId(rs.getInt("city_id"));
                        cityBean.setCity(rs.getString("city"));
                        cityBean.setLastUpdate(rs.getDate("last_update"));
                        cityBean.setCountry(new CountryBean(rs.getInt("country_id"), rs.getString("country"), rs.getDate("last_update_country")));
                    }
                }
            }

            return cityBean;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalException(String.format("Ocurrió un error al obtener los datos de la ciudad con id %d.", cityId));
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

    public CityBean guardar(CityBean cityBean) throws InternalException {
        Conexion conexion = null;

        try {
            conexion = new Conexion();
            String sql = "INSERT INTO city (city, country_id, last_update) VALUES (?,?,?)";
            conexion.connectar();
            // MYSQL y SQL SERVER
            try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, cityBean.getCity());
                ps.setInt(2, cityBean.getCountry().getCountryId());
                ps.setDate(3, new java.sql.Date(cityBean.getLastUpdate().getTime()));
                ps.executeUpdate(); // Para insert y update

                // Para recuperar el valor de llave primaria
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    // Se llena la propiedad del ID
                    cityBean.setCityId(rs.getInt(1));
                }
            }

            return cityBean;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalException("Ocurrió un error al insertar los datos de la ciudad");
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

    public CityBean actualizar(CityBean cityBean) throws InternalException {
        Conexion conexion = null;

        try {
            int registros;
            conexion = new Conexion();
            String sql = "UPDATE city SET city = ?, country_id = ?, last_update = ? WHERE city_id = ?";
            conexion.connectar();
            // MYSQL y SQL SERVER
            try (PreparedStatement ps = conexion.getConnection().prepareStatement(sql)) {

                ps.setString(1, cityBean.getCity());
                ps.setInt(2, cityBean.getCountry().getCountryId());
                ps.setDate(3, new java.sql.Date(cityBean.getLastUpdate().getTime()));
                ps.setInt(4, cityBean.getCityId());
                registros = ps.executeUpdate(); // Para insert y update

                if (registros == 0) {
                    throw new InternalException(String.format("El id %d no se encuentra registrado en la base de datos", cityBean.getCityId()));
                }
            }

            return cityBean;
        } catch (InternalException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalException("Ocurrió un error al actulizar los datos de la ciudad");
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

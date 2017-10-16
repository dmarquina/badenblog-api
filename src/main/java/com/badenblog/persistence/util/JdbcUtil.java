package com.badenblog.persistence.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;

/**
 * Utilitario para la llamada de procedimientos almacenados
 */
public final class JdbcUtil {

  private static final Logger LOG = Logger.getLogger(JdbcUtil.class);

  private JdbcUtil(){
    
  }
  public static Map<String, Object> ejecutarProcedimiento(final JdbcTemplate jdbcTemplate,
      final Class<? extends StoredProcedure> storedProcedure, final Map<String, Object> parametros) {
    try {
      StoredProcedure instancia = storedProcedure.getDeclaredConstructor(JdbcTemplate.class).newInstance(jdbcTemplate);
      return instancia.execute(parametros);
    } catch (final InstantiationException e) {
      LOG.error("InstantiationException", e);
    } catch (final IllegalAccessException e) {
      LOG.error("IllegalAccessException", e);
    } catch (final IllegalArgumentException e) {
      LOG.error("IllegalArgumentException", e);
    } catch (final SecurityException e) {
      LOG.error("SecurityException", e);
    } catch (final InvocationTargetException e) {
      LOG.error("InvocationTargetException", e);
    } catch (final NoSuchMethodException e) {
      LOG.error("NoSuchMethodException", e);
    }
    return null;
  }
  
  public static Map<String, Object> ejecutarProcedimiento(final JdbcTemplate jdbcTemplate,
          final Class<? extends StoredProcedure> storedProcedure,
          final Map<String, Object> parametros, final String nameStoreProcedure) {
    try {

      final Class<?>[] parameterTypes = { JdbcTemplate.class, nameStoreProcedure.getClass() };
      final StoredProcedure instancia = storedProcedure.getDeclaredConstructor(parameterTypes)
              .newInstance(jdbcTemplate, nameStoreProcedure);

      return instancia.execute(parametros);
    } catch (final InstantiationException e) {
      LOG.error("InstantiationException", e);
    } catch (final IllegalAccessException e) {
      LOG.error("IllegalAccessException", e);
    } catch (final IllegalArgumentException e) {
      LOG.error("IllegalArgumentException", e);
    } catch (final SecurityException e) {
      LOG.error("SecurityException", e);
    } catch (final InvocationTargetException e) {
      LOG.error("InvocationTargetException", e);
    } catch (final NoSuchMethodException e) {
      LOG.error("NoSuchMethodException", e);
    }
    return null;
  }
}

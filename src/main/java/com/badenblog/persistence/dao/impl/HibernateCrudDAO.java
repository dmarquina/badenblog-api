package com.badenblog.persistence.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.badenblog.persistence.util.CustomRowMapper;
import com.badenblog.persistence.util.JdbcUtil;

public class HibernateCrudDAO<T> {
  
  private static final String ESPACIO_VACIO = " ";
  private static final String CARACTER_PORCENTAJE = "%"; 
  
  @Autowired
  @Lazy
  SessionFactory sessionFactory;
  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  protected Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  
  protected JdbcTemplate getJdbcTemplate(){
    return jdbcTemplate;
  }

  protected Session openNewSession() {
    return sessionFactory.openSession();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public T persist(final T t) {
    getSession().saveOrUpdate(t);
    return t;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public T update(final T t) {
    getSession().merge(t);
    return t;
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> X findById(final Class<X> type, final Object id) {
    return (X) getSession().createCriteria(type).add(Restrictions.idEq(id))
            .uniqueResult();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<T> findAll(final Class<T> type) {
    final Criteria criteria = getSession().createCriteria(type);
    criteria.add(Restrictions.eq("isdeleted", "N"));
    return criteria.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<T> findAll(final Class<T> type, final Long codSede) {
    final Criteria criteria = getSession().createCriteria(type);
    criteria.add(Restrictions.eq("isdeleted", "N"));
    criteria.add(Restrictions.eq("codsede", codSede));
    return criteria.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<T> findByName(final Class<T> type, final String propertyName, final Object value) {
    return getSession().createCriteria(type)
            .add(Restrictions.eq(propertyName, value)).list();
  }

  protected Query setParametersQuery(final String queryString, final Map<String, Object> parameters) {
    final Query query = getSession().createQuery(queryString);
    return setParameters(query, parameters);
  }

  protected Query setParametersQuery(final String queryString,
          final Map<String, Object> parameters, final int firstResult, final int maxResults) {
    final Query query = setParametersQuery(queryString, parameters);
    setPagination(query, firstResult, maxResults);
    return query;
  }

  protected Query setParametersNamedQuery(final String namedQuery,
          final Map<String, Object> parameters) {
    final Query query = getSession().getNamedQuery(namedQuery);
    return setParameters(query, parameters);
  }
  
  protected Query setParametersNativeQuery(final String queryString,
          final Map<String, Object> parameters, final ResultTransformer resultTransformer) {
    final Query query = getSession().createSQLQuery(queryString);
    if (resultTransformer != null) {
      query.setResultTransformer(resultTransformer);
    }
    return setParameters(query, parameters);
  }

  protected Query setParametersNativeQuery(final String queryString,
          final Map<String, Object> parameters, final ResultTransformer resultTransformer,
          final int firstResult, final int maxResults) {
    final Query query = setParametersNativeQuery(queryString, parameters, resultTransformer);
    return setPagination(query, firstResult, maxResults);
  }

  protected Query setParametersNativeQuery(final String queryString,
          final Map<String, Object> parameters) {
    return setParametersNativeQuery(queryString, parameters, null);

  }

  protected Query setParametersNamedQuery(final String namedQuery,
          final Map<String, Object> parameters, final int firstResult, final int maxResults) {
    final Query query = setParametersNamedQuery(namedQuery, parameters);
    setPagination(query, firstResult, maxResults);
    return query;
  }

  protected Query setParameters(final Query query, final Map<String, Object> parameters) {
    if (parameters != null) {
      for (final String key : parameters.keySet()) {
        final Object value = parameters.get(key);
        if (value instanceof Collection<?>) {
          query.setParameterList(key, (Collection<?>) value);
        } else {
          query.setParameter(key, parameters.get(key));
        }
      }
    }
    return query;
  }

  protected Query setPagination(final Query query, final int firstResult, final int maxResults) {
    if (firstResult > 0) {
      query.setFirstResult(firstResult);
    }
    if (maxResults > 0) {
      query.setMaxResults(maxResults);
    }
    return query;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void executeNamedQuery(final String namedQuery, final Map<String, Object> parameters) {
    final Query query = setParametersNamedQuery(namedQuery, parameters);
    query.executeUpdate();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void executeNativeQuery(final String queryString, final Map<String, Object> parameters) {
    final Query query = setParametersNativeQuery(queryString, parameters);
    query.executeUpdate();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void executeQuery(final String queryString, final Map<String, Object> parameters) {
    final Query query = setParametersQuery(queryString, parameters);
    query.executeUpdate();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<T> findAllWithQuery(final String queryString, final Map<String, Object> parameters) {
    final Query query = setParametersQuery(queryString, parameters);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<T> findAllWithQuery(final String queryString, final Map<String, Object> parameters,
          final int firstResult, final int maxResults) {
    final Query query = setParametersQuery(queryString, parameters, firstResult, maxResults);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public T findSingleResultWithQuery(final String queryString, final Map<String, Object> parameters) {
    final Query query = setParametersQuery(queryString, parameters, 0, 1);
    query.setMaxResults(1);
    return (T) query.uniqueResult();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> List<X> findAllWithQuery(final Class<X> type, final String queryString,
          final Map<String, Object> parameters) {
    final Query query = setParametersQuery(queryString, parameters);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> List<X> findAllWithQuery(final Class<X> type, final String queryString,
          final Map<String, Object> parameters, final int firstResult, final int maxResults) {
    final Query query = setParametersQuery(queryString, parameters, firstResult, maxResults);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> X findSingleResultWithQuery(final Class<X> type, final String queryString,
          final Map<String, Object> parameters) {
    final Query query = setParametersQuery(queryString, parameters);
    query.setMaxResults(1);
    return (X) query.uniqueResult();
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> List<X> findAllWithNativeQuery(final Class<X> type, final String queryString,
                                            final Map<String, Object> parameters) {
    final Query query = setParametersNativeQuery(queryString, parameters);
    return query.list();
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> List<X> findAllWithNativeQuery(final Class<X> type, final String queryString,
          final Map<String, Object> parameters, final ResultTransformer resultTransformer) {
    final Query query = setParametersNativeQuery(queryString, parameters, resultTransformer);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> List<X> findAllWithNativeQuery(final Class<X> type, final String queryString,
          final Map<String, Object> parameters, final ResultTransformer resultTransformer,
          final int firstResult, final int maxResults) {
    final Query query = setParametersNativeQuery(queryString, parameters, resultTransformer,
            firstResult, maxResults);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> X findSingleResultWithNativeQuery(final Class<X> type, final String queryString,
          final Map<String, Object> parameters, final ResultTransformer resultTransformer) {
    final Query query = setParametersNativeQuery(queryString, parameters, resultTransformer);
    query.setMaxResults(1);
    return (X) query.uniqueResult();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public <X> X findSingleResultWithNativeQuery(final Class<X> type, final String queryString,
          final Map<String, Object> parameters) {
    return findSingleResultWithNativeQuery(type, queryString, parameters, true);
  }

  @SuppressWarnings("unchecked")
  @Transactional(propagation = Propagation.REQUIRED)
  public <X> X findSingleResultWithNativeQuery(final Class<X> type, final String queryString,
          final Map<String, Object> parameters, final boolean forceMaxResults) {
    final Query query = setParametersNativeQuery(queryString, parameters);
    if (forceMaxResults) {
      query.setMaxResults(1);
    }
    return (X) query.uniqueResult();
  }

  public int deleteWithNativeQuery(final String queryString, final Map<String, Object> parameters) {
    final Query query = setParametersNativeQuery(queryString, parameters);
    return query.executeUpdate();
  }

  public int insertWithNativeQuery(final String queryString, final Map<String, Object> parameters) {
    final Query query = setParametersNativeQuery(queryString, parameters);
    return query.executeUpdate();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<T> findAllWithNamedQuery(final String namedQuery, final Map<String, Object> parameters) {
    final Query query = setParametersNamedQuery(namedQuery, parameters);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<T> findAllWithNamedQuery(final String namedQuery,
          final Map<String, Object> parameters, final int firstResult, final int maxResults) {
    final Query query = setParametersNamedQuery(namedQuery, parameters, firstResult, maxResults);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public T findSingleResultWithNamedQuery(final String namedQuery,
          final Map<String, Object> parameters) {
    final Query query = setParametersNamedQuery(namedQuery, parameters, 0, 1);
    query.setMaxResults(1);
    return (T) query.uniqueResult();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> List<X> findAllWithNamedQuery(final Class<X> type, final String namedQuery,
          final Map<String, Object> parameters) {
    final Query query = setParametersNamedQuery(namedQuery, parameters);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> List<X> findAllWithNamedQuery(final Class<X> type, final String namedQuery,
          final Map<String, Object> parameters, final int firstResult, final int maxResults) {
    final Query query = setParametersNamedQuery(namedQuery, parameters, firstResult, maxResults);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> X findSingleResultWithNamedQuery(final Class<X> type, final String namedQuery,
          final Map<String, Object> parameters) {
    final Query query = setParametersNamedQuery(namedQuery, parameters);
    query.setMaxResults(1);
    return (X) query.uniqueResult();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public <X> X save(final X t) {
    getSession().saveOrUpdate(t);
    return t;
  }

  public void flush() {
    getSession().flush();
  }

  public Transaction initTransaction() {
    return getSession().beginTransaction();
  }
  
  public String likeQueryPattern(String cadena) {
    if (cadena == null || cadena.trim().isEmpty()) {
      return CARACTER_PORCENTAJE;
    }
    StringBuilder sb = new StringBuilder(CARACTER_PORCENTAJE);
    cadena = cadena.trim();
    int emptyCharacterIndex = cadena.indexOf(ESPACIO_VACIO);
    while (emptyCharacterIndex != -1) {
      sb.append(cadena.substring(0, emptyCharacterIndex));
      cadena = cadena.substring(emptyCharacterIndex + 1);
      cadena = cadena.trim();
      sb.append(CARACTER_PORCENTAJE);
      emptyCharacterIndex = cadena.indexOf(ESPACIO_VACIO);
    }
    sb.append(cadena);
    sb = sb.append(CARACTER_PORCENTAJE);
    return sb.toString();
  }

  public Map<String, Object> ejecutarProcedimiento(
      final Class<? extends StoredProcedure> storedProcedure,
      final Map<String, Object> parametros) {
    return JdbcUtil.ejecutarProcedimiento(getJdbcTemplate(), storedProcedure, parametros);
  }
  
  public Map<String, Object> ejecutarProcedimiento(
          final Class<? extends StoredProcedure> storedProcedure,
          final Map<String, Object> parametros, final String nameStoredProcedure) {
    return JdbcUtil.ejecutarProcedimiento(getJdbcTemplate(), storedProcedure, parametros,
            nameStoredProcedure);
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public <X> List<X> executeQuery(String query, final Map<String, Object> parameters, CustomRowMapper<X> rowMapper){
    return namedParameterJdbcTemplate.query(query, parameters, rowMapper);
  }
}

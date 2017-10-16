package com.badenblog.persistence.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;

public interface ICrudDAO<T> {

  /**
   * Utilizado para guardar y actualizar un objeto
   * 
   * @param t
   *          - Objeto a ser guardado o actualizado
   * @return El objeto actualizado
   */

  T persist(T t);

  /**
   * Utilizado para actualizar un objeto sin considerar el objeto en la misma
   * sesion
   * 
   * @param t
   *          - Objeto a ser actualizado
   * @return El objeto actualizado
   */
  T update(T t);

  /**
   * Busca un objeto por su id
   * 
   * @param type
   *          - La clase del objeto que sera buscado
   * @param id
   *          - El id del objeto por el que se buscara
   * @return El objeto completo encontrado
   */
  <X> X findById(Class<X> type, Object id);

  /**
   * Busca todos los objetos de cierto tipo
   * 
   * @param type
   *          - La clase de los objetos a buscar
   * @return Lista de objetos encontrados
   */
  List<T> findAll(Class<T> type);

  /**
   * Busca todos los objetos de cierto tipo para la sede indicada
   * 
   * @param type
   *          - La clase de los objetos a buscar
   * @param codSede
   *          - La sede a buscar
   * @return Lista de objetos encontrados
   */
  List<T> findAll(Class<T> type, Long codSede);

  /**
   * Busca todos los objetos por un filtro
   * 
   * @param type
   *          - La clase de los objetos a buscar
   * @param propertyName
   *          - La propiedad por la que se desea buscar
   * @param value
   *          - El valor que debe contener dicha propiedad
   * @return Lista de objetos encontrados
   */
  List<T> findByName(Class<T> type, String propertyName, Object value);

//  /**
//   * Borra la entidad de la base de datos logico
//   * 
//   * @param t
//   *          - Objeto a ser borrado
//   * @throws Exception
//   */
//  void remover(EntidadUtec t);
//
//  /**
//   * Borrado fisico de la entidad de la base de datos
//   * 
//   * @param t
//   *          - Objeto a ser eliminado
//   * @return El objeto actualizado
//   */
//  void delete(T t);

  void executeNamedQuery(String namedQuery, Map<String, Object> parameters);

  /*
   * metodos de query JPQL
   */
  List<T> findAllWithQuery(String queryString, Map<String, Object> parameters);

  List<T> findAllWithQuery(String queryString, Map<String, Object> parameters, int firstResult,
          int maxResults);

  T findSingleResultWithQuery(String queryString, Map<String, Object> parameters);

  <X> List<X> findAllWithQuery(Class<X> type, String queryString, Map<String, Object> parameters);

  <X> List<X> findAllWithQuery(Class<X> type, String queryString, Map<String, Object> parameters,
          int firstResult, int maxResults);

  <X> X findSingleResultWithQuery(Class<X> type, String queryString, Map<String, Object> parameters);

  /*
   * metodos de query nativo
   */
  <X> List<X> findAllWithNativeQuery(Class<X> type, String queryString,
          Map<String, Object> parameters, ResultTransformer resultTransformer);

  <X> List<X> findAllWithNativeQuery(Class<X> type, String queryString,
          Map<String, Object> parameters, ResultTransformer resultTransformer, int firstResult,
          int maxResults);

  <X> X findSingleResultWithNativeQuery(Class<X> type, String queryString,
          Map<String, Object> parameters, ResultTransformer resultTransformer);

  <X> X findSingleResultWithNativeQuery(Class<X> type, String queryString,
          Map<String, Object> parameters);

  <X> X findSingleResultWithNativeQuery(Class<X> type, String queryString,
          Map<String, Object> parameters, boolean forceMaxResults);

  int deleteWithNativeQuery(String queryString, Map<String, Object> parameters);

  int insertWithNativeQuery(String queryString, Map<String, Object> parameters);

  /*
   * metodos de named query
   */
  List<T> findAllWithNamedQuery(String namedQuery, Map<String, Object> parameters);

  List<T> findAllWithNamedQuery(String namedQuery, Map<String, Object> parameters, int firstResult,
          int maxResults);

  T findSingleResultWithNamedQuery(String namedQuery, Map<String, Object> parameters);

  /**
   * Busca todos los objetos usando ciertos filtros
   * 
   * @param type
   *          - La clase de los objetos a buscar
   * @param namedQuery
   *          - NamedQuery declarado en la clase a buscar
   * @param parameters
   *          - Map con los parametros de los nombres y filtros
   * @return Lista de objetos encontrados
   */
  <X> List<X> findAllWithNamedQuery(Class<X> type, String namedQuery, Map<String, Object> parameters);

  <X> List<X> findAllWithNamedQuery(Class<X> type, String namedQuery,
          Map<String, Object> parameters, int firstResult, int maxResults);

  <X> X findSingleResultWithNamedQuery(Class<X> type, String namedQuery,
          Map<String, Object> parameters);

  <X> X save(X t);

  void executeNativeQuery(String queryString, Map<String, Object> parameters);

  void flush();

  void executeQuery(String queryString, Map<String, Object> parameters);
}

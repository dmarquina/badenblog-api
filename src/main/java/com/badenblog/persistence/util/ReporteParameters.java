package com.badenblog.persistence.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReporteParameters {

  private static List<Map<Integer, Object[]>> listData = null;
  private static Map<Integer, Object[]> cabecera = null;
  private int indiceCabecera = 1;
  private static Map<Integer, Object[]> subData = null;
  private int indiceData = 1;
  private Object[] objetos = null;

  public class CabeceraParameters {
    private final List<Object> parametros;

    public CabeceraParameters(final Integer startIndex) {
      parametros = new ArrayList<Object>();
      parametros.add(startIndex);
    }

    public CabeceraParameters and(final String label, final Object value) {
      parametros.add(new Object[] { label, 1 });
      parametros.add(new Object[] { value, 2 });
      return this;
    }

    public Object[] parameters() {
      final Object[] result = new Object[parametros.size()];
      int i = 0;
      for (final Object object : parametros) {
        result[i++] = object;
      }
      return result;
    }
  }

  public class GrillaParameters {
    private String[] parametrosCabecera;
    private int indiceColumnaCabecera = 0;
    private final List<Object[]> parametrosBody;
    private Object[] parametrosRegistro;
    private int indiceColumnaRegistro = 0;
    private final int numColumnas;

    public GrillaParameters(final Integer numColumnas) {
      this.numColumnas = numColumnas;
      parametrosCabecera = new String[numColumnas];
      parametrosBody = new ArrayList<Object[]>();
      parametrosRegistro = new Object[numColumnas];
    }

    public GrillaParameters agregarCabecera(final String nombreCabecera) {
      parametrosCabecera[indiceColumnaCabecera] = nombreCabecera;
      indiceColumnaCabecera++;
      return this;
    }

    public GrillaParameters agregarSubBody(final Object object) {
      parametrosRegistro[indiceColumnaRegistro] = object;
      indiceColumnaRegistro++;
      return this;
    }

    public GrillaParameters agregarBody() {
      parametrosBody.add(parametrosRegistro);
      parametrosRegistro = new Object[numColumnas];
      indiceColumnaRegistro = 0;
      return this;
    }

    public Object[] parameters() {
      final Object[] result = new Object[parametrosBody.size() + 1];
      result[0] = parametrosCabecera;
      int i = 1;
      for (final Object object : parametrosBody) {
        result[i++] = object;
      }
      indiceColumnaCabecera = 0;
      parametrosCabecera = new String[numColumnas];
      return result;
    }

    public Object[] getParametrosCabecera() {
      return parametrosCabecera;
    }

    public List<Object[]> getParametrosBody() {
      return parametrosBody;
    }
  }

  public ReporteParameters.CabeceraParameters getCabeceraParamStartIndex(final Integer startIndex) {
    return new CabeceraParameters(startIndex);
  }

  public ReporteParameters.GrillaParameters getGrilla(final Integer numColumnas) {
    return new GrillaParameters(numColumnas);
  }

  public Object[] getObjeto() {
    return objetos;
  }

  public void setObjeto(final Object[] objeto) {
    objetos = objeto;
  }

  private ReporteParameters() {
    listData = new ArrayList<Map<Integer, Object[]>>();
    cabecera = new TreeMap<Integer, Object[]>();
    subData = new HashMap<Integer, Object[]>();
  }

  private ReporteParameters(final int posicion, final String cadena) {
    this();
    objetos[posicion] = cadena;
  }

  public static ReporteParameters param(final int posicion, final String cadena) {
    return new ReporteParameters(posicion, cadena);
  }

  public ReporteParameters otherParam(final int posicion, final String cadena) {
    objetos[posicion] = cadena;
    return this;
  }

  public Object[] objetos() {
    return objetos;
  }

  public static List<Map<Integer, Object[]>> listData(final Map<Integer, Object[]> subData) {
    listData.add(subData);
    return listData;
  }

  public ReporteParameters agregarCabecera(final Object[] value) {
    cabecera.put(indiceCabecera++, value);
    return this;
  }

  public static ReporteParameters createInstance() {
    return new ReporteParameters();
  }

  public ReporteParameters agregarSubData(final Object[] value) {
    subData.put(indiceData++, value);
    return this;
  }

  public ReporteParameters agregarData() {
    listData.add(subData);
    subData = new HashMap<Integer, Object[]>();
    indiceData = 1;
    return this;
  }

  public static List<Map<Integer, Object[]>> getListData() {
    return listData;
  }

  public static Map<Integer, Object[]> getCabecera() {
    return cabecera;
  }

}

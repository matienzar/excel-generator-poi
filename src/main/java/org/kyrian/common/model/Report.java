package org.kyrian.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase usada para el mapeo a un objeto de tipo Informe
 *
 * @author matienzar
 * @since 202209.1.0
 */
public class Report {

    private TipoInforme tipo;

    private String nombreInforme;

    private String sql;

    @JsonIgnore
    private EntityManager em;

    @JsonIgnore
    private Map<String, Object> parameters;

    private List<Column> columnList;

    @JsonIgnore
    private List<List<Object>> datos;

    public void addFila(List<Object> fila){
        if (datos == null){
            datos = new ArrayList<>();
        }
        datos.add(fila);
    }
    public void addColumna(Column column){
        if (columnList == null){
            columnList = new ArrayList<>();
        }
        columnList.add(column);
    }
    private String pathToExport;

    public TipoInforme getTipo() {
        return tipo;
    }

    public void setTipo(TipoInforme tipo) {
        this.tipo = tipo;
    }

    public String getNombreInforme() {
        return nombreInforme;
    }

    public void setNombreInforme(String nombreInforme) {
        this.nombreInforme = nombreInforme;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public List<Column> getColumnaList() {
        return columnList;
    }

    public void setColumnaList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public List<List<Object>> getDatos() {
        return datos;
    }

    public void setDatos(List<List<Object>> datos) {
        this.datos = datos;
    }

    public String getPathToExport() {
        return pathToExport;
    }

    public void setPathToExport(String pathToExport) {
        this.pathToExport = pathToExport;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}

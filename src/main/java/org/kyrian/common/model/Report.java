package org.kyrian.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mapping to a Report Object
 *
 * @author matienzar
 * @since 202209.1.0
 */
public class Report {

    private String reportName;

    private String sql;

    @JsonIgnore
    private EntityManager em;

    @JsonIgnore
    private Map<String, Object> parameters;

    private List<Column> columnList;

    @JsonIgnore
    private List<List<Object>> data;

    public void addRow(List<Object> row){
        if (data == null){
            data = new ArrayList<>();
        }
        data.add(row);
    }
    public void addColumn(Column column){
        if (columnList == null){
            columnList = new ArrayList<>();
        }
        columnList.add(column);
    }
    private String pathToExport;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
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

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
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

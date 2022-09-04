package org.kyrian.common.model;

import org.apache.poi.ss.usermodel.CellStyle;

public class Column {

    private String columnaSql;
    private DataType tipoDato;

    private CellStyle cellStyle;

    private String nombre;

    public String getColumnaSql() {
        return columnaSql;
    }

    public void setColumnaSql(String columnaSql) {
        this.columnaSql = columnaSql;
    }

    public DataType getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(DataType tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }
}

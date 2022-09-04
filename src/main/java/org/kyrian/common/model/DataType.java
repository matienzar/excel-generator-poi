package org.kyrian.common.model;

import org.apache.poi.ss.usermodel.CellType;

/**
 * @author matienzar
 * @since 202209.1.0
 */
public enum DataType {
    FECHA_LARGA(25, HorizontalAlignment.CENTRADO,"dd/MM/yyyy", CellType.STRING),
    FECHA(20, HorizontalAlignment.CENTRADO,"dd/MM/yy", CellType.STRING),
    FECHA_HORA(35, HorizontalAlignment.CENTRADO, "dd/MM/yy HH:mm", CellType.STRING),
    HORA(20, HorizontalAlignment.CENTRADO,"HH:mm:ss", CellType.STRING),
    ZONA(30, HorizontalAlignment.IZQUIERDA,"", CellType.STRING),
    DT(30, HorizontalAlignment.IZQUIERDA,"", CellType.STRING),
    NOMBRE_PERSONA(80, HorizontalAlignment.IZQUIERDA,"", CellType.STRING),
    INICIALES(10, HorizontalAlignment.CENTRADO,"", CellType.STRING),
    NUMERO(20, HorizontalAlignment.DERECHA,"", CellType.NUMERIC),
    CADENA(100, HorizontalAlignment.IZQUIERDA,"", CellType.STRING),
    CODIGO(20, HorizontalAlignment.CENTRADO,"", CellType.STRING),
    EMPRESA(80, HorizontalAlignment.IZQUIERDA,"", CellType.STRING),
    DESPACHO(80, HorizontalAlignment.IZQUIERDA,"", CellType.STRING);

    public final HorizontalAlignment horizontalAlignment;
    public final Integer numCaracteres;
    public final String formato;

    public final CellType tipoDato;

    private DataType(Integer numCaracteres, HorizontalAlignment horizontalAlignment, String formato, CellType tipoDato) {
        this.horizontalAlignment = horizontalAlignment;
        this.numCaracteres = numCaracteres;
        this.formato = formato;
        this.tipoDato = tipoDato;
    }
}

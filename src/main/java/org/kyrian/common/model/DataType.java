package org.kyrian.common.model;

import org.apache.poi.ss.usermodel.CellType;

/**
 * @author matienzar
 * @since 202209.1.0
 */
public enum DataType {
    LONG_DATE(25, HorizontalAlignment.CENTER,"dd/MM/yyyy", CellType.STRING),
    DATE(20, HorizontalAlignment.CENTER,"dd/MM/yy", CellType.STRING),
    DATE_HOUR(35, HorizontalAlignment.CENTER, "dd/MM/yy HH:mm", CellType.STRING),
    HOUR(20, HorizontalAlignment.CENTER,"HH:mm:ss", CellType.STRING),
    NAME(80, HorizontalAlignment.LEFT,"", CellType.STRING),
    NICKNAME(10, HorizontalAlignment.CENTER,"", CellType.STRING),
    NUMBER(20, HorizontalAlignment.RIGHT,"", CellType.NUMERIC),
    STRING(100, HorizontalAlignment.LEFT,"", CellType.STRING),
    CODE(20, HorizontalAlignment.CENTER,"", CellType.STRING),
    COMPANY_NAME(80, HorizontalAlignment.LEFT,"", CellType.STRING);

    public final HorizontalAlignment horizontalAlignment;
    public final Integer numChars;
    public final String format;

    public final CellType dataType;

    private DataType(Integer numCaracteres, HorizontalAlignment horizontalAlignment, String format, CellType dataType) {
        this.horizontalAlignment = horizontalAlignment;
        this.numChars = numCaracteres;
        this.format = format;
        this.dataType = dataType;
    }
}

package org.kyrian.common.model;

public enum HorizontalAlignment {
    IZQUIERDA, DERECHA, CENTRADO, JUSTIFICADO;

    public final org.apache.poi.ss.usermodel.HorizontalAlignment getHorizontalAlignment(){
        switch (this){
            case DERECHA:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
            case IZQUIERDA:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
            case CENTRADO:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
            default:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.JUSTIFY;
        }
    }
}

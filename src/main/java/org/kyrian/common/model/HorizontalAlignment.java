package org.kyrian.common.model;

public enum HorizontalAlignment {
    LEFT, RIGHT, CENTER, JUSTIFY;

    public final org.apache.poi.ss.usermodel.HorizontalAlignment getHorizontalAlignment(){
        switch (this){
            case RIGHT:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
            case LEFT:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
            case CENTER:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
            default:
                return org.apache.poi.ss.usermodel.HorizontalAlignment.JUSTIFY;
        }
    }
}

package org.kyrian.common.styles;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public final class ReportFonts {

    private static final String FONT_NAME_ARIAL = "Arial";
    private static final String FONT_NAME_SANS_SERIF = "SansSerif";


    public static Font getFuenteDatoTabla(Workbook workbook) {
        Font fuenteDatoTabla = workbook.createFont();
        fuenteDatoTabla.setFontName(FONT_NAME_SANS_SERIF);
        fuenteDatoTabla.setFontHeightInPoints((short) 12);
        return fuenteDatoTabla;
    }

    public static Font getFuenteEncabezadoTabla(Workbook workbook) {
        Font fuenteEncabezadoTabla = workbook.createFont();
        fuenteEncabezadoTabla.setFontName(FONT_NAME_SANS_SERIF);
        fuenteEncabezadoTabla.setFontHeightInPoints((short) 12);
        fuenteEncabezadoTabla.setBold(true);

        return fuenteEncabezadoTabla;
    }
}

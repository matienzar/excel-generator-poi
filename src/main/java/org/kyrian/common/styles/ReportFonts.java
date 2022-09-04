package org.kyrian.common.styles;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public final class ReportFonts {

    private static final String FONT_NAME_ARIAL = "Arial";
    private static final String FONT_NAME_SANS_SERIF = "SansSerif";


    public static Font getDataFont(Workbook workbook) {
        Font dataFont = workbook.createFont();
        dataFont.setFontName(FONT_NAME_SANS_SERIF);
        dataFont.setFontHeightInPoints((short) 12);
        return dataFont;
    }

    public static Font getHeaderFont(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setFontName(FONT_NAME_SANS_SERIF);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);

        return headerFont;
    }
}

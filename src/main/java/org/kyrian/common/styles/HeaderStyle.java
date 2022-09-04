package org.kyrian.common.styles;

import org.apache.poi.ss.usermodel.*;

/**
 * Estilo asociado al encabezado
 *
 * @author matienzar
 * @since 202209.1.0
 */
public final class HeaderStyle {


    private HeaderStyle() {
        // DO NOTHING
    }

    public static CellStyle get(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        headerStyle.setFillPattern(FillPatternType.NO_FILL);
        headerStyle.setBorderBottom(BorderStyle.THICK);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFont(ReportFonts.getFuenteEncabezadoTabla(workbook));
        return headerStyle;
    }
}

package org.kyrian.common.styles;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.kyrian.common.model.HorizontalAlignment;

/**
 * Cell style associated to data cell
 *
 * @author matienzar
 * @since 202209.1.0
 */
public final class DataStyle {

    private DataStyle() {
        // DO NOTHING
    }

    public static CellStyle get(Workbook workbook, HorizontalAlignment horizontalAlignment) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillPattern(FillPatternType.NO_FILL);
        cellStyle.setAlignment(horizontalAlignment.getHorizontalAlignment());
        cellStyle.setFont(ReportFonts.getDataFont(workbook));
        return cellStyle;
    }
}

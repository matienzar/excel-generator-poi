package org.kyrian.common;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.kyrian.common.styles.DataStyle;
import org.kyrian.common.styles.HeaderStyle;
import org.kyrian.common.model.Column;
import org.kyrian.common.model.ExportTo;
import org.kyrian.common.model.Report;
import org.kyrian.common.model.DataType;
import org.slf4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.*;


public class ReportGenerator {

    private static final Logger LOGGER = getLogger(ReportGenerator.class);
    private static final int WIDTH_BY_CHAR = 256;
    public static final String HEADER = "HEADER";
    private Report report;

    private final Map<String, CellStyle> styles = new HashMap<>();

    private ReportGenerator() {
        // DO NOTHING
    }

    public ReportGenerator(Report report) {
        this.report = report;
    }

    public String getReport(ExportTo exportTo) {
        switch (exportTo) {
            case EXCEL:
                return toExcel();
            default:
                break;
        }

        return "";
    }

    private String toExcel() {
        Path filePath = getFilePath("xlsx");
        if (filePath != null) {
            try (FileOutputStream outputStream = new FileOutputStream(filePath.toString()); Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet(report.getReportName());
                initStyles(sheet);
                addColumns(sheet);
                addHeader(sheet);
                addDataRow(sheet);
                autoSizeColumns(sheet);
                workbook.write(outputStream);
                return filePath.toAbsolutePath().toString();
            } catch (IOException e) {
                throw new ReportGeneratorException("Generating the excel report for:" + report.getReportName(), e);
            }
        }
        return null;

    }

    private void initStyles(final Sheet sheet){
        styles.put(HEADER, HeaderStyle.get(sheet.getWorkbook()));
    }

    private void autoSizeColumns(final Sheet sheet) {
        //Auto size all the columns
        Row firstRow = sheet.getRow(0);
        Iterator<Cell> cellIterator = firstRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()) {
                sheet.autoSizeColumn(cell.getColumnIndex());
            }
        }
    }

    private Path getFilePath(final String fileExtension) {
        String path = report.getPathToExport();
        LOGGER.info("Path: {}", path);
        if (path == null || path.trim().isEmpty()) {
            try {
                Path filePathTemp = Files.createTempFile("app_", "." + fileExtension);
                LOGGER.info("Temporal file: {}", filePathTemp);
                return filePathTemp;
            } catch (IOException e) {
                LOGGER.error("Failed to generate temporary file", e);
                return null;
            }
        } else {
            String fileName = UUID.randomUUID() + "." + fileExtension;
            Path filePath = Paths.get(path, fileName);
            LOGGER.info("File generated: {}", filePath);
            return filePath;
        }
    }

    private void addDataRow(final Sheet sheet) {
        final AtomicInteger reportRow = new AtomicInteger(1);
        if (report.getData() != null && !report.getData().isEmpty()) {
            for (List<Object> rows : report.getData()) {
                int columnPos = 0;
                Row row = sheet.createRow(reportRow.get());
                for (Object field : rows) {
                    Column column = report.getColumnList().get(columnPos);
                    Cell cell = row.createCell(columnPos);
                    setCellValue(cell, field, column.getDataType());
                    cell.setCellStyle(column.getCellStyle());
                    LOGGER.debug("Value of the cell to fill {}-{} : {} - {}", reportRow, columnPos, field, column.getDataType().horizontalAlignment);

                    columnPos++;
                }
                reportRow.getAndIncrement();
            }
        } else if (report.getSql() != null && !report.getSql().trim().isEmpty()) {
            // Estamos ante un informe para generar a través de una SQL
            Session session = report.getEm().unwrap(Session.class);
            // https://thorben-janssen.com/get-query-results-stream-hibernate-5/
            NativeQuery query = session.createNativeQuery(report.getSql());
            if (report.getParameters() != null && !report.getParameters().isEmpty()) {
                for (Map.Entry<String, Object> parameter : report.getParameters().entrySet()) {
                    query.setParameter(parameter.getKey(), parameter.getValue());
                }
            }
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            Stream<Map<String, Object>> stream = query.stream();
            stream.forEach(m -> addDataRow(sheet, reportRow.getAndIncrement(), m));
        }

    }

    private void addDataRow(final Sheet sheet, final int rowPos, Map<String, Object> data) {
        int posColumn = 0;
        Row row = sheet.createRow(rowPos);
        for (Column column : report.getColumnList()) {
            Cell cell = row.createCell(posColumn);
            setCellValue(cell, data.get(column.getSqlColumnName().toUpperCase()), column.getDataType());
            cell.setCellStyle(column.getCellStyle());
            LOGGER.debug("Value of the cell to fill {}-{} : {} - {}", rowPos, posColumn, data.get(column.getSqlColumnName().toUpperCase()), column.getDataType().horizontalAlignment);
            posColumn++;
        }
    }

    private void setCellValue(final Cell cell, Object cellValue, DataType cellType) {

        if (cellValue == null) {
            cell.setCellValue("");
        } else {
            switch (cellType) {
                case DATE:
                case LONG_DATE:
                case DATE_HOUR:
                case HOUR:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(cellType.format);
                    LocalDate dateTime = LocalDate.parse(cellValue.toString(), formatter);
                    cell.setCellValue(dateTime);
                default:
                    cell.setCellValue(cellValue.toString());
            }
        }
    }

    private void addColumns(final Sheet sheet) {
        int columPos = 0;
        for (Column column : report.getColumnList()) {
            sheet.setColumnWidth(columPos++, column.getDataType().numChars * WIDTH_BY_CHAR);
        }
    }

    private void addHeader(final Sheet sheet) {
        Row header = sheet.createRow(0);

        // FIX HEADER
        sheet.createFreezePane(0, 1);

        int columPos = 0;
        for (Column column : report.getColumnList()) {
            Cell headerCell = header.createCell(columPos++);
            headerCell.setCellValue(column.getName());
            headerCell.setCellStyle(styles.get(HEADER));
            column.setCellStyle(DataStyle.get(sheet.getWorkbook(), column.getDataType().horizontalAlignment));
        }

        LOGGER.debug("Last cell {}", header.getLastCellNum());
        LOGGER.debug("Last cell on excel {}", header.getCell(header.getLastCellNum() - 1).getAddress().formatAsString());

        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:" + header.getCell(header.getLastCellNum() - 1).getAddress().formatAsString()));
    }
}

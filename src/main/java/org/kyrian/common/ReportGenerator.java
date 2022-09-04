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
import org.slf4j.LoggerFactory;

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


public class ReportGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportGenerator.class);
    private static final int TAMANYO_X_CARACTER = 256;
    private Report informe;

    private Map<String, CellStyle> estilos = new HashMap<>();

    private ReportGenerator() {

    }

    public ReportGenerator(Report informe) {
        this.informe = informe;
    }

    public String generaInforme(ExportTo exportTo) {
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
                Sheet sheet = workbook.createSheet(informe.getNombreInforme());
                initStyles(sheet);
                addColumns(sheet);
                addHeader(sheet);
                addDataRow(sheet);
                autoSizeColumns(sheet);
                workbook.write(outputStream);
                return filePath.toAbsolutePath().toString();
            } catch (IOException e) {
                throw new ReportGeneratorException("Generando el informe excel para:" + informe.getNombreInforme(), e);
            }
        }
        return null;

    }

    private void initStyles(final Sheet sheet){
        estilos.put("ENCABEZADO", HeaderStyle.get(sheet.getWorkbook()));
    }

    private void autoSizeColumns(final Sheet sheet) {
        //Auto size all the columns
        Row filaInicial = sheet.getRow(0);
        Iterator<Cell> cellIterator = filaInicial.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getStringCellValue() != null && !cell.getStringCellValue().isEmpty()) {
                sheet.autoSizeColumn(cell.getColumnIndex());
            }
        }
    }

    private Path getFilePath(final String fileExtension) {
        String path = informe.getPathToExport();
        LOGGER.info("Path del informe: {}", path);
        if (path == null || path.trim().isEmpty()) {
            try {
                Path filePathTemp = Files.createTempFile("app_", "." + fileExtension);
                LOGGER.info("Fichero temporal generado: {}", filePathTemp);
                return filePathTemp;
            } catch (IOException e) {
                LOGGER.error("No se ha podido generar el fichero temporal", e);
                return null;
            }
        } else {
            String fileName = UUID.randomUUID() + "." + fileExtension;
            Path filePath = Paths.get(path, fileName);
            LOGGER.info("Fichero generado: {}", filePath);
            return filePath;
        }
    }

    private void addDataRow(final Sheet sheet) {
        final AtomicInteger fila = new AtomicInteger(1);
        if (informe.getDatos() != null && !informe.getDatos().isEmpty()) {
            for (List<Object> filas : informe.getDatos()) {
                int posColumna = 0;
                Row row = sheet.createRow(fila.get());
                for (Object campo : filas) {
                    Column column = informe.getColumnaList().get(posColumna);
                    Cell cell = row.createCell(posColumna);
                    setCellValue(cell, campo, column.getTipoDato());
                    cell.setCellStyle(column.getCellStyle());
                    LOGGER.debug("Valor de la celda a rellenar {}-{} : {} - {}", fila, posColumna, campo, column.getTipoDato().horizontalAlignment);

                    posColumna++;
                }
                fila.getAndIncrement();
            }
        } else if (informe.getSql() != null && !informe.getSql().trim().isEmpty()) {
            // Estamos ante un informe para generar a través de una SQL
            Session session = informe.getEm().unwrap(Session.class);
            // https://thorben-janssen.com/get-query-results-stream-hibernate-5/
            NativeQuery query = session.createNativeQuery(informe.getSql());
            if (informe.getParameters() != null && !informe.getParameters().isEmpty()) {
                for (Map.Entry<String, Object> parametro : informe.getParameters().entrySet()) {
                    query.setParameter(parametro.getKey(), parametro.getValue());
                }
            }
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            Stream<Map<String, Object>> stream = query.stream();
            stream.forEach(m -> addDataRow(sheet, fila.getAndIncrement(), m));
        }

    }

    private void addDataRow(final Sheet sheet, final int fila, Map<String, Object> datos) {
        int posColumna = 0;
        Row row = sheet.createRow(fila);
        for (Column column : informe.getColumnaList()) {
            Cell cell = row.createCell(posColumna);
            setCellValue(cell, datos.get(column.getColumnaSql().toUpperCase()), column.getTipoDato());
            cell.setCellStyle(column.getCellStyle());
            LOGGER.debug("Valor de la celda a rellenar {}-{} : {} - {}", fila, posColumna, datos.get(column.getColumnaSql().toUpperCase()), column.getTipoDato().horizontalAlignment);
            posColumna++;
        }
    }

    private void setCellValue(final Cell cell, Object cellValue, DataType cellType) {

        if (cellValue == null) {
            cell.setCellValue("");
        } else {
            switch (cellType) {
                case FECHA:
                case FECHA_LARGA:
                case FECHA_HORA:
                case HORA:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(cellType.formato);
                    LocalDate dateTime = LocalDate.parse(cellValue.toString(), formatter);
                    cell.setCellValue(dateTime);
                default:
                    cell.setCellValue(cellValue.toString());
            }
        }
    }

    private void addColumns(final Sheet sheet) {
        int columPos = 0;
        for (Column column : informe.getColumnaList()) {
            sheet.setColumnWidth(columPos++, column.getTipoDato().numCaracteres * TAMANYO_X_CARACTER);
        }
    }

    private void addHeader(final Sheet sheet) {
        Row header = sheet.createRow(0);

        // HEADER FIJO
        sheet.createFreezePane(0, 1);

        int columPos = 0;
        for (Column column : informe.getColumnaList()) {
            Cell headerCell = header.createCell(columPos++);
            headerCell.setCellValue(column.getNombre());
            headerCell.setCellStyle(estilos.get("ENCABEZADO"));

            // Definimos el estilo para esa columna a nivel de datos para reaprovecharlo
            column.setCellStyle(DataStyle.get(sheet.getWorkbook(), column.getTipoDato().horizontalAlignment));
        }

        LOGGER.info("Última celda {}", header.getLastCellNum());
        LOGGER.info("Última celda en excel {}", header.getCell(header.getLastCellNum() - 1).getAddress().formatAsString());

        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:" + header.getCell(header.getLastCellNum() - 1).getAddress().formatAsString()));
    }
}

package org.kyrian.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.kyrian.common.ReportGeneratorException;
import org.kyrian.common.model.Report;

import java.io.IOException;

/**
 * Clase de utilidades para el generador de informes
 *
 * @author matienzar
 */
public final class ReportGeneratorUtility {

    private ReportGeneratorUtility() {
        // DO NOTHING
    }

    /**
     * Report from a json
     * @param reportJson Example:
     * {
     *   "reportName": "Test from data",
     *   "columnList": [
     *     {
     *       "dataType": "DATE",
     *       "name": "Date"
     *     },
     *     {
     *       "dataType": "COMPANY_NAME",
     *       "name": "Company Name"
     *     }
     *   ]
     * }
     * @return Report mapped from json
     */
    public static Report fromJson(String reportJson) {
        try {
            return JacksonJsonInit.INSTANCE.getObjectMapper().readValue(reportJson, new TypeReference<Report>() {
            });
        } catch (IOException e) {
            throw new ReportGeneratorException("Converting the json to Report object:" + reportJson, e);
        }
    }

    public static String toJson(Report informe) {
        try {
            return JacksonJsonInit.INSTANCE.getObjectMapper().writeValueAsString(informe);
        } catch (IOException e) {
            throw new ReportGeneratorException("Converting the json to Report object", e);
        }
    }

}

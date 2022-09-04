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
     * Obtenemos el objeto informe a partir del json proporcionado
     * @param informeJson Ejemplo:
     * {
     *    "nombreInforme":"prueba generador",
     *    "columnaList":[
     *       {
     *          "tipoDato":"FECHA",
     *          "nombre":"Fecha"
     *       },
     *       {
     *          "tipoDato":"EMPRESA",
     *          "nombre":"Tipo empresa"
     *       }
     *    ],
     *    "datos":[
     *       [
     *          "01/02/22",
     *          "Pavasal Empresa Constructora"
     *       ],
     *       [
     *          "05/06/23",
     *          "Mercadona"
     *       ]
     *    ],
     *    "pathToExport":"/Users/matienzar/Downloads"
     * }
     * @return
     */
    public static Report fromJson(String informeJson) {
        try {
            return JacksonJsonInit.INSTANCE.getObjectMapper().readValue(informeJson, new TypeReference<Report>() {
            });
        } catch (IOException e) {
            throw new ReportGeneratorException("Convirtiendo el json al objeto Informe:" + informeJson, e);
        }
    }

    public static String toJson(Report informe) {
        try {
            return JacksonJsonInit.INSTANCE.getObjectMapper().writeValueAsString(informe);
        } catch (IOException e) {
            throw new ReportGeneratorException("Convirtiendo el json al objeto Informe", e);
        }
    }

}

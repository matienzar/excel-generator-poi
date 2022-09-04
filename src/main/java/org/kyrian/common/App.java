package org.kyrian.common;

import org.kyrian.common.model.Column;
import org.kyrian.common.model.ExportTo;
import org.kyrian.common.model.Report;
import org.kyrian.common.model.DataType;
import org.kyrian.common.util.ReportGeneratorUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Report informe = new Report();
        informe.setNombreInforme("prueba generador");
        informe.setPathToExport("/Users/matienzar/Downloads");

        Column column1 = new Column();
        column1.setNombre("Fecha");
        column1.setTipoDato(DataType.FECHA);
        informe.addColumna(column1);

        Column column2 = new Column();
        column2.setNombre("Tipo empresa");
        column2.setTipoDato(DataType.EMPRESA);
        informe.addColumna(column2);

        List<Object> fila = new ArrayList<>();
        fila.add("01/02/22");
        fila.add("Pavasal Empresa Constructora");
        informe.addFila(fila);

        fila = new ArrayList<>();
        fila.add("05/06/23");
        fila.add("Mercadona");
        informe.addFila(fila);

        String informeJson = ReportGeneratorUtility.toJson(informe);

        System.out.println("Informe formato json: "+ informeJson);
        Report informeDesdeJson = ReportGeneratorUtility.fromJson(informeJson);
        String informeGenerado = new ReportGenerator(informeDesdeJson).generaInforme(ExportTo.EXCEL);

        System.out.println("Informe generado: "+ informeGenerado);



    }
}

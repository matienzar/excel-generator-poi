package org.kyrian.common;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.kyrian.common.model.Column;
import org.kyrian.common.model.DataType;
import org.kyrian.common.model.ExportTo;
import org.kyrian.common.model.Report;
import org.kyrian.common.util.ReportGeneratorUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void fromData()
    {
        Report report = new Report();
        report.setReportName("Test from data");

        Column column1 = new Column();
        column1.setName("Date");
        column1.setDataType(DataType.DATE);
        report.addColumn(column1);

        Column column2 = new Column();
        column2.setName("Company Name");
        column2.setDataType(DataType.COMPANY_NAME);
        report.addColumn(column2);

        List<Object> row = new ArrayList<>();
        row.add("01/02/22");
        row.add("Apple Inc");
        report.addRow(row);

        row = new ArrayList<>();
        row.add("05/06/23");
        row.add("Alphabet");
        report.addRow(row);

        String pathToReport = new ReportGenerator(report).getReport(ExportTo.EXCEL);

        Assert.assertNotNull("Path to report must no be null [from data objects]",pathToReport);
    }

    @Test
    public void fromJson(){
        String jsonConfiguration = "{" +
                "  \"reportName\": \"Test from data\"," +
                "  \"columnList\": [" +
                "    {" +
                "      \"dataType\": \"DATE\"," +
                "      \"name\": \"Date\"" +
                "    }," +
                "    {" +
                "      \"dataType\": \"COMPANY_NAME\"," +
                "      \"name\": \"Company Name\"" +
                "    }" +
                "  ]" +
                "}";

        Report report = ReportGeneratorUtility.fromJson(jsonConfiguration);
        List<Object> row = new ArrayList<>();
        row.add("01/02/22");
        row.add("Apple Inc");
        report.addRow(row);

        row = new ArrayList<>();
        row.add("05/06/23");
        row.add("Alphabet");
        report.addRow(row);

        String pathToReport = new ReportGenerator(report).getReport(ExportTo.EXCEL);

        Assert.assertNotNull("Path to report must no be null [from json config]",pathToReport);
    }
}

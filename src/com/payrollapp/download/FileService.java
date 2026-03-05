package com.payrollapp.download;

import java.io.FileWriter;
import java.io.IOException;

public class FileService {

    public String savePayslipAsText(DownloadablePayslip payslip) throws IOException {
        String fileName = "Payslip_" + payslip.getEmpId() + "_"
                + System.currentTimeMillis() + ".txt";
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(payslip.toString());
        }
        return fileName;
    }

    public String savePayslipAsPdf(DownloadablePayslip payslip) throws IOException {
        String fileName = "Payslip_" + payslip.getEmpId() + "_"
                + System.currentTimeMillis() + ".pdf";
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(payslip.toString());
        }
        return fileName;
    }
}

package com.mucanze.algafood.infrastruture.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mucanze.algafood.domain.filter.VendaDiariaFilter;
import com.mucanze.algafood.domain.service.VendaQueryService;
import com.mucanze.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportService implements VendaReportService {
	
	@Autowired
	private VendaQueryService vendaQueryService;

	@Override
	public byte[] emitirRelatorioVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset) {
		try {
			var inputStream = this.getClass().getResourceAsStream("/reports/vendas-diarias.jasper");
			
			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
			
			var vendasDiarias = vendaQueryService.listarVendasDiarias(vendaDiariaFilter, timeOffset); 
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
			
			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
			
			return JasperExportManager.exportReportToPdf(jasperPrint);
			
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}
	}

}

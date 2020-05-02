package util;

import java.awt.Font;
import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class JFreeChartUtil {
	public static void main(String[] args) {
		XYSeriesCollection data = null;
		data = new XYSeriesCollection();
		data.removeAllSeries();
		int length = 100;
		for (int i = 0; i < 2; i++) {
			XYSeries series = new XYSeries(((Integer) i).toString());
			for (int j = 0; j < length; j++) {
				series.add(j, Math.random());
			}
			data.addSeries(series);
		}

		String filePath = "output/chart test.jpg";
		createLineChart(data, filePath);
	}

	
	public static void createLineChart(String filepath, String[] datanames, double[]... values) {
		XYSeriesCollection data = null;
		data = new XYSeriesCollection();
		data.removeAllSeries();
		for (int i = 0; i < values.length; i++) {
			XYSeries series = new XYSeries(datanames[i]);
			for (int j = 0; j < values[i].length; j++) {
				series.add(j, values[i][j]);
			}
			data.addSeries(series);

		}
		try {
			// ������״ͼ.����,X����,Y����,���ݼ���,orientation,�Ƿ���ʾlegend,�Ƿ���ʾtooltip,�Ƿ�ʹ��url����
			JFreeChart chart = ChartFactory.createXYLineChart("Wave", "", "Y", data, PlotOrientation.VERTICAL, true,
					true, false);
			chart.getLegend().setItemFont(new Font(null, Font.PLAIN, 20));
			ChartUtilities.saveChartAsJPEG(new File(filepath), chart, 1207, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void createLineChart(XYSeriesCollection data, String filePath) {
		try {
			// ������״ͼ.����,X����,Y����,���ݼ���,orientation,�Ƿ���ʾlegend,�Ƿ���ʾtooltip,�Ƿ�ʹ��url����
			JFreeChart chart = ChartFactory.createXYLineChart("Wave", "", "Y", data, PlotOrientation.VERTICAL, false,
					true, true);
			ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 1207, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}

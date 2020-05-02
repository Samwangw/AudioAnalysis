package util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

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

		// double[] values = new double[length];
		// String[] x = new String[length];
		// String[] y = new String[length];

		String filePath = "output/chart test.jpg";
		createLineChart(data, filePath);
		// createLineChart(values, filePath);
	}

	public static DefaultCategoryDataset createCateDataset(double[] values, String[] x, String[] y) {
		if (values.length != x.length || x.length != y.length)
			throw new IllegalArgumentException("Pictrue dataset length is not equal");
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		for (int i = 0; i < values.length; i++) {
			ds.setValue(values[i], y[i], x[i]);
		}
		return ds;
	}

	public static void createLineChart(double[] values1, double[] values2, String filepath) {
		XYSeriesCollection data = null;
		data = new XYSeriesCollection();
		data.removeAllSeries();

		XYSeries series1 = new XYSeries("first");
		for (int j = 0; j < values1.length; j++) {
			series1.add(j, values1[j]);
		}
		data.addSeries(series1);

		XYSeries series2 = new XYSeries("second");
		for (int j = 0; j < values2.length; j++) {
			series2.add(j, values2[j]);
		}
		data.addSeries(series2);
		try {
			// ������״ͼ.����,X����,Y����,���ݼ���,orientation,�Ƿ���ʾlegend,�Ƿ���ʾtooltip,�Ƿ�ʹ��url����
			JFreeChart chart = ChartFactory.createXYLineChart("Wave", "", "Y", data, PlotOrientation.VERTICAL, false,
					true, true);
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

	public static void createLineChart(double[] values, String[] x, String[] y, String filePath) {
		try {
			DefaultCategoryDataset ds = createCateDataset(values, x, y);
			// ������״ͼ.����,X����,Y����,���ݼ���,orientation,�Ƿ���ʾlegend,�Ƿ���ʾtooltip,�Ƿ�ʹ��url����
			JFreeChart chart = ChartFactory.createLineChart("Wave", "", "Y", ds, PlotOrientation.VERTICAL, false, true,
					true);
			chart.setBackgroundPaint(Color.WHITE);
			Font font = new Font("����", Font.BOLD, 12);
			chart.getTitle().setFont(font);
			chart.setBackgroundPaint(Color.WHITE);
			// �������壨������������ͨ�÷�����
			Font xfont = new Font("����", Font.BOLD, 12); // X��
			Font yfont = new Font("����", Font.BOLD, 12); // Y��
			Font titleFont = new Font("����", Font.BOLD, 12); // ͼƬ����
			CategoryPlot categoryPlot = chart.getCategoryPlot();
			categoryPlot.getDomainAxis().setLabelFont(xfont);
			categoryPlot.getDomainAxis().setLabelFont(xfont);
			categoryPlot.getRangeAxis().setLabelFont(yfont);
			chart.getTitle().setFont(titleFont);
			categoryPlot.setBackgroundPaint(Color.WHITE);
			// x�� // �����������Ƿ�ɼ�
			categoryPlot.setDomainGridlinesVisible(false);
			// y�� //�����������Ƿ�ɼ�
			categoryPlot.setRangeGridlinesVisible(true);
			// ��������������ɫ
			categoryPlot.setDomainGridlinePaint(Color.LIGHT_GRAY);
			// �������������ɫ
			categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
			// û������ʱ��ʾ������˵��
			categoryPlot.setNoDataMessage("û��������ʾ");
			// ��������ͼ��xy��ľ���
			categoryPlot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 0d));
			// �����������
			Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
			// ȡ��Y��
			NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			rangeAxis.setAutoRangeIncludesZero(true);
			rangeAxis.setUpperMargin(0.20);
			rangeAxis.setLabelAngle(Math.PI / 2.0);
			// ȡ��X��
			CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
			categoryAxis.setTickLabelsVisible(false);
			// // ����X�������ϵ�����
			// categoryAxis.setTickLabelFont(labelFont);
			// // ����X��ı�������
			// categoryAxis.setLabelFont(labelFont);
			// // �����ϵ� Lable 45����б
			// categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			// ���þ���ͼƬ��˾���
			categoryAxis.setLowerMargin(0.0);
			// ���þ���ͼƬ�Ҷ˾���
			categoryAxis.setUpperMargin(0.0);
			// ���renderer ע���������������͵�lineandshaperenderer����
			LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
			// �Ƿ���ʾ�۵�
			lineandshaperenderer.setBaseShapesVisible(false);
			// �Ƿ���ʾ����
			lineandshaperenderer.setBaseLinesVisible(true);
			// series �㣨�����ݵ㣩�������߿ɼ� ��ʾ�۵�����
			lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			lineandshaperenderer.setBaseItemLabelsVisible(false);
			ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 1207, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DefaultCategoryDataset createCateDataset(double[] values) {
		String[] x = new String[values.length];
		String[] y = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			x[i] = ((Integer) i).toString();
			y[i] = "";
		}
		return createCateDataset(values, x, y);
	}

	public static void createLineChart(double[] values, String filePath) {
		try {
			DefaultCategoryDataset ds = createCateDataset(values);
			// ������״ͼ.����,X����,Y����,���ݼ���,orientation,�Ƿ���ʾlegend,�Ƿ���ʾtooltip,�Ƿ�ʹ��url����
			JFreeChart chart = ChartFactory.createLineChart("Wave", "", "Y", ds, PlotOrientation.VERTICAL, false, true,
					true);
			chart.setBackgroundPaint(Color.WHITE);
			Font font = new Font("����", Font.BOLD, 12);
			chart.getTitle().setFont(font);
			chart.setBackgroundPaint(Color.WHITE);
			// �������壨������������ͨ�÷�����
			Font xfont = new Font("����", Font.BOLD, 12); // X��
			Font yfont = new Font("����", Font.BOLD, 12); // Y��
			Font titleFont = new Font("����", Font.BOLD, 12); // ͼƬ����
			CategoryPlot categoryPlot = chart.getCategoryPlot();
			categoryPlot.getDomainAxis().setLabelFont(xfont);
			categoryPlot.getDomainAxis().setLabelFont(xfont);
			categoryPlot.getRangeAxis().setLabelFont(yfont);
			chart.getTitle().setFont(titleFont);
			categoryPlot.setBackgroundPaint(Color.WHITE);
			// x�� // �����������Ƿ�ɼ�
			categoryPlot.setDomainGridlinesVisible(false);
			// y�� //�����������Ƿ�ɼ�
			categoryPlot.setRangeGridlinesVisible(true);
			// ��������������ɫ
			categoryPlot.setDomainGridlinePaint(Color.LIGHT_GRAY);
			// �������������ɫ
			categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
			// û������ʱ��ʾ������˵��
			categoryPlot.setNoDataMessage("û��������ʾ");
			// ��������ͼ��xy��ľ���
			categoryPlot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 0d));
			// �����������
			Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
			// ȡ��Y��
			NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			rangeAxis.setAutoRangeIncludesZero(true);
			rangeAxis.setUpperMargin(0.20);
			rangeAxis.setLabelAngle(Math.PI / 2.0);
			// ȡ��X��
			CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
			categoryAxis.setTickLabelsVisible(false);
			// // ����X�������ϵ�����
			// categoryAxis.setTickLabelFont(labelFont);
			// // ����X��ı�������
			// categoryAxis.setLabelFont(labelFont);
			// // �����ϵ� Lable 45����б
			// categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			// ���þ���ͼƬ��˾���
			categoryAxis.setLowerMargin(0.0);
			// ���þ���ͼƬ�Ҷ˾���
			categoryAxis.setUpperMargin(0.0);
			// ���renderer ע���������������͵�lineandshaperenderer����
			LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
			// �Ƿ���ʾ�۵�
			lineandshaperenderer.setBaseShapesVisible(false);
			// �Ƿ���ʾ����
			lineandshaperenderer.setBaseLinesVisible(true);
			// series �㣨�����ݵ㣩�������߿ɼ� ��ʾ�۵�����
			lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			lineandshaperenderer.setBaseItemLabelsVisible(false);
			ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 1207, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

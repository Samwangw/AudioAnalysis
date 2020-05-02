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
			// 创建柱状图.标题,X坐标,Y坐标,数据集合,orientation,是否显示legend,是否显示tooltip,是否使用url链接
			JFreeChart chart = ChartFactory.createXYLineChart("Wave", "", "Y", data, PlotOrientation.VERTICAL, false,
					true, true);
			ChartUtilities.saveChartAsJPEG(new File(filepath), chart, 1207, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void createLineChart(XYSeriesCollection data, String filePath) {
		try {
			// 创建柱状图.标题,X坐标,Y坐标,数据集合,orientation,是否显示legend,是否显示tooltip,是否使用url链接
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
			// 创建柱状图.标题,X坐标,Y坐标,数据集合,orientation,是否显示legend,是否显示tooltip,是否使用url链接
			JFreeChart chart = ChartFactory.createLineChart("Wave", "", "Y", ds, PlotOrientation.VERTICAL, false, true,
					true);
			chart.setBackgroundPaint(Color.WHITE);
			Font font = new Font("宋体", Font.BOLD, 12);
			chart.getTitle().setFont(font);
			chart.setBackgroundPaint(Color.WHITE);
			// 配置字体（解决中文乱码的通用方法）
			Font xfont = new Font("仿宋", Font.BOLD, 12); // X轴
			Font yfont = new Font("宋体", Font.BOLD, 12); // Y轴
			Font titleFont = new Font("宋体", Font.BOLD, 12); // 图片标题
			CategoryPlot categoryPlot = chart.getCategoryPlot();
			categoryPlot.getDomainAxis().setLabelFont(xfont);
			categoryPlot.getDomainAxis().setLabelFont(xfont);
			categoryPlot.getRangeAxis().setLabelFont(yfont);
			chart.getTitle().setFont(titleFont);
			categoryPlot.setBackgroundPaint(Color.WHITE);
			// x轴 // 分类轴网格是否可见
			categoryPlot.setDomainGridlinesVisible(false);
			// y轴 //数据轴网格是否可见
			categoryPlot.setRangeGridlinesVisible(true);
			// 设置网格竖线颜色
			categoryPlot.setDomainGridlinePaint(Color.LIGHT_GRAY);
			// 设置网格横线颜色
			categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
			// 没有数据时显示的文字说明
			categoryPlot.setNoDataMessage("没有数据显示");
			// 设置曲线图与xy轴的距离
			categoryPlot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 0d));
			// 设置面板字体
			Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
			// 取得Y轴
			NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			rangeAxis.setAutoRangeIncludesZero(true);
			rangeAxis.setUpperMargin(0.20);
			rangeAxis.setLabelAngle(Math.PI / 2.0);
			// 取得X轴
			CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
			categoryAxis.setTickLabelsVisible(false);
			// // 设置X轴坐标上的文字
			// categoryAxis.setTickLabelFont(labelFont);
			// // 设置X轴的标题文字
			// categoryAxis.setLabelFont(labelFont);
			// // 横轴上的 Lable 45度倾斜
			// categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			// 设置距离图片左端距离
			categoryAxis.setLowerMargin(0.0);
			// 设置距离图片右端距离
			categoryAxis.setUpperMargin(0.0);
			// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
			LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
			// 是否显示折点
			lineandshaperenderer.setBaseShapesVisible(false);
			// 是否显示折线
			lineandshaperenderer.setBaseLinesVisible(true);
			// series 点（即数据点）间有连线可见 显示折点数据
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
			// 创建柱状图.标题,X坐标,Y坐标,数据集合,orientation,是否显示legend,是否显示tooltip,是否使用url链接
			JFreeChart chart = ChartFactory.createLineChart("Wave", "", "Y", ds, PlotOrientation.VERTICAL, false, true,
					true);
			chart.setBackgroundPaint(Color.WHITE);
			Font font = new Font("宋体", Font.BOLD, 12);
			chart.getTitle().setFont(font);
			chart.setBackgroundPaint(Color.WHITE);
			// 配置字体（解决中文乱码的通用方法）
			Font xfont = new Font("仿宋", Font.BOLD, 12); // X轴
			Font yfont = new Font("宋体", Font.BOLD, 12); // Y轴
			Font titleFont = new Font("宋体", Font.BOLD, 12); // 图片标题
			CategoryPlot categoryPlot = chart.getCategoryPlot();
			categoryPlot.getDomainAxis().setLabelFont(xfont);
			categoryPlot.getDomainAxis().setLabelFont(xfont);
			categoryPlot.getRangeAxis().setLabelFont(yfont);
			chart.getTitle().setFont(titleFont);
			categoryPlot.setBackgroundPaint(Color.WHITE);
			// x轴 // 分类轴网格是否可见
			categoryPlot.setDomainGridlinesVisible(false);
			// y轴 //数据轴网格是否可见
			categoryPlot.setRangeGridlinesVisible(true);
			// 设置网格竖线颜色
			categoryPlot.setDomainGridlinePaint(Color.LIGHT_GRAY);
			// 设置网格横线颜色
			categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
			// 没有数据时显示的文字说明
			categoryPlot.setNoDataMessage("没有数据显示");
			// 设置曲线图与xy轴的距离
			categoryPlot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 0d));
			// 设置面板字体
			Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
			// 取得Y轴
			NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			rangeAxis.setAutoRangeIncludesZero(true);
			rangeAxis.setUpperMargin(0.20);
			rangeAxis.setLabelAngle(Math.PI / 2.0);
			// 取得X轴
			CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
			categoryAxis.setTickLabelsVisible(false);
			// // 设置X轴坐标上的文字
			// categoryAxis.setTickLabelFont(labelFont);
			// // 设置X轴的标题文字
			// categoryAxis.setLabelFont(labelFont);
			// // 横轴上的 Lable 45度倾斜
			// categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			// 设置距离图片左端距离
			categoryAxis.setLowerMargin(0.0);
			// 设置距离图片右端距离
			categoryAxis.setUpperMargin(0.0);
			// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
			LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
			// 是否显示折点
			lineandshaperenderer.setBaseShapesVisible(false);
			// 是否显示折线
			lineandshaperenderer.setBaseLinesVisible(true);
			// series 点（即数据点）间有连线可见 显示折点数据
			lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			lineandshaperenderer.setBaseItemLabelsVisible(false);
			ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 1207, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

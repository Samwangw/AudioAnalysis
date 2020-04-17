package models;

import java.util.Vector;

public class Distance {

	public static double getEuclidDistance(double a[], double b[]) {
		if (a.length != b.length) {
			throw new IllegalArgumentException("Two dimension are different.");
		}
		double dis = 0;
		for (int i = 0; i < a.length; i++) {
			double oneD = a[i] - b[i];
			dis += oneD * oneD;
		}
		dis = Math.sqrt(dis);
		return dis;
	}

	public static double getEuclidDistance(int a[], int b[]) {
		if (a.length != b.length) {
			throw new IllegalArgumentException("Two dimension are different.");
		}
		double dis = 0;
		for (int i = 0; i < a.length; i++) {
			double oneD = a[i] - b[i];
			dis += oneD * oneD;
		}
		dis = Math.sqrt(dis);
		return dis;
	}

	public static double getDTWDistance(double[] a, double[] b) {
		if (0 == a.length || 0 == b.length) {
			throw new IllegalArgumentException("Series is empty.");
		}
		double dis = 0;
		double[][] diff = new double[a.length][b.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				diff[i][j] = Math.abs(a[i] - b[j]);
			}
		}
		double[][] acc_diff = new double[a.length][b.length];
		acc_diff[0][0] = diff[0][0];
		for (int i = 1; i < a.length; i++)
			acc_diff[i][0] = acc_diff[i - 1][0] + diff[i][0];
		for (int i = 1; i < b.length; i++)
			acc_diff[0][i] = acc_diff[0][i - 1] + diff[0][i];
		for (int i = 1; i < a.length; i++) {
			for (int j = 1; j < b.length; j++) {
				acc_diff[i][j] = Math.min(acc_diff[i - 1][j - 1], Math.min(acc_diff[i - 1][j], acc_diff[i][j - 1]))
						+ diff[i][j];
			}
		}
		dis = acc_diff[a.length - 1][b.length - 1];
		return dis;
	}
	
	public static double getDTWDistance(double[][] a, double[][] b) {
		if (0 == a.length || 0 == b.length) {
			throw new IllegalArgumentException("Series is empty.");
		}
		double dis = 0;
		double[][] diff = new double[a.length][b.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				diff[i][j] = getEuclidDistance(a[i],b[j]);
			}
		}
		double[][] acc_diff = new double[a.length][b.length];
		acc_diff[0][0] = diff[0][0];
		for (int i = 1; i < a.length; i++)
			acc_diff[i][0] = acc_diff[i - 1][0] + diff[i][0];
		for (int i = 1; i < b.length; i++)
			acc_diff[0][i] = acc_diff[0][i - 1] + diff[0][i];
		for (int i = 1; i < a.length; i++) {
			for (int j = 1; j < b.length; j++) {
				acc_diff[i][j] = Math.min(acc_diff[i - 1][j - 1], Math.min(acc_diff[i - 1][j], acc_diff[i][j - 1]))
						+ diff[i][j];
			}
		}
		dis = acc_diff[a.length - 1][b.length - 1];
		return dis;
	}
}

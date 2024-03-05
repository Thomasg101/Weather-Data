import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		ArrayList<double[]> data = new ArrayList<double[]>();
		double [][] topDays = new double[10][3];
		
		for(int year = 1995; year <= 2022; year++) {
			data.add(fileInput(year));
		}
		
		topDays = top2DayMonths(data);
		
		System.out.println("TOP 10 TWO DAY PERIODS (MONTH, YEAR, AMT)");
		for(int i = 0; i < 10 ; i++) {
			System.out.println(Arrays.toString(topDays[i]));
		}
		System.out.println("\n");
		
		double [][] topMonths = top10Months(data);
		
		System.out.println("TOP 10 MONTHS (MONTH, YEAR, AMT)");
		for(int i = 0; i < 10 ; i++) {
			System.out.println(Arrays.toString(topMonths[i]));
		}
		
		//System.out.println(Arrays.toString(perMonth(fileInput(2021))));	
	}
	
	public static double[][] top2DayMonths(ArrayList<double[]> data){
		double [] months = new double[336];
		for(int year = 0; year < 28; year++) {
			for(int month = 0; month < 12 ; month++) {
				months[year * 12 + month] = topPerMonth(data.get(year), month);
			}
		}
		
		return top10(months);
	}
	
	
	public static double topPerMonth(double[] data, int month) {
		int[] months = null;
		double cur = 0;
		double top = 0;
		if(data[0] == -1) {
			months = new int[] {1, 32, 61, 92, 122, 153, 183, 214, 245, 275, 306, 336, 367};
		} else {
			months = new int[] {1, 32, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};
		}
		for(int i = months[month] ; i < months[month + 1] - 2; i++) {
			if(data[i] != -1) cur += data[i];
			if (data[i + 1] != -1) cur += data[i + 1];
			if (cur > top) {
				top = cur;
			}
			cur = 0;
		}
		return top;
	}
	
	public static double[][] top10(double [] data){
		double[][] returnData = new double[10][3];
		
		for(int i = 0; i < data.length - 1; i++) {
			if(data[i] > returnData[9][2]) {
				for(int k = 8 ; k >= 0 ; k--) {
					if(data[i] < returnData[k][2]) {
						replaceVal(returnData, data[i], k + 1);
						returnData[k + 1][0] = i % 12 + 1;
						returnData[k + 1][1] = 1995 + (int)(i / 12);
						k = -1;
					}
					if(k == 0) {
						replaceVal(returnData, data[i], 0);
						returnData[0][0] = i % 12 + 1;
						returnData[0][1] = 1995 + (int)(i / 12);
					}
					//System.out.println(k);
				}
			}
		}
		return returnData;
	}
	
	public static double[][] top102Day(ArrayList<double[]> data){
		double cur = 0;
		double[][] topDays = new double[10][3];
		for(int i = 0 ; i < data.size() ; i++) {
			for(int j = 1 ; j < data.get(i).length - 1; j++) {
				if(data.get(i)[j] != -1) cur += data.get(i)[j];
				if(data.get(i)[j + 1] != -1) cur += data.get(i)[j + 1];
				if(cur > topDays[9][2]) {
					for(int k = 8 ; k >= 0 ; k--) {
						if(cur < topDays[k][2]) {
							replaceVal(topDays, cur, k + 1);
							topDays[k + 1][0] = i + 1995;
							topDays[k + 1][1] = j;
							k = -1;
						}
						if(k == 0) {
							replaceVal(topDays, cur, 0);
							topDays[0][0] = i + 1995;
							topDays[0][1] = j;
						}
						//System.out.println(k);
					}
				}
				cur = 0;
			}
			
		}
		return topDays;
	}
	
	public static void replaceVal(double[][] dataSet, double cur, int spot) {
		for(int i = 9 ; i > spot ; i--) {
			dataSet[i][0] = dataSet[i - 1][0];
			dataSet[i][1] = dataSet[i - 1][1];
			dataSet[i][2] = dataSet[i - 1][2];
		}
		cur = (double)((int)(10 * cur)) / 10;
		dataSet[spot][2] = cur;
	}

	public static double[][] top10Months(ArrayList<double[]> data){
		String[] m = {"January","February","March","April","May","June","July","August","September","October","November","December"};
		double [] months = new double[336];
		double [] cur = null;
		
		for(int year = 0; year < 28; year++) {
			cur = perMonth(data.get(year));
			for(int month = 0; month < 12 ; month++) {
				months[year * 12 + month] = cur[month];
			}
		}
		
		return top10(months);
	}
	
	
	public static double[] perMonth(double[] data) {
		int[] months = null;
		double counter = 0;
		int days = 0;
		double avg [] = new double[12];
		if(data[0] == -1) {
			months = new int[] {1, 32, 61, 92, 122, 153, 183, 214, 245, 275, 306, 336, 367};
		} else {
			months = new int[] {1, 32, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};
		}
		
		for(int i = 0; i < months.length - 1; i++) {
			days = months[i + 1] - months[i];
			for(int j = months[i] ; j < months[i + 1] - 1; j++) {
				if(data[j] != -1) {
					counter += data[j];
				} else {
					days--;
				}
			}
			avg[i] = (double)((int)(10 * ((counter) * (months[i + 1] - months[i]) / days))) / 10;
			counter = 0;
			days = 0;
		}
		
		return avg;
	}
	
	public static double[] fileInput(int year) {
		double U = 0;
		double G = 0;
		double avg = 0;
		BufferedReader in = null;
		
		try {
		    in = new BufferedReader(new FileReader("src/weather/en_climate_daily_BC_1018598_" + year + "_P1D.csv"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] university = in.lines().toArray(String[] ::new);
		try {
			in = new BufferedReader(new FileReader("src/weather/en_climate_daily_BC_1018611_" + year + "_P1D.csv"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] gonzales = in.lines().toArray(String[] ::new);
		
        int days = (isLeap(year) ? 367 : 366);
        
        double[] data = new double[days];
        data[0] = (isLeap(year) ? -2 : -1);
        
        for(int i = 1; i < days; i++) {
        	try{
        		G = Double.parseDouble(gonzales[i + 1].split("\",\"")[23]);
        	} catch(Exception e) {
        		G = -1;
        	}
        	try{
        		U = Double.parseDouble(university[i + 1].split("\",\"")[23]);
        	} catch(Exception e) {
        		U = -1;
        	}
        	if(G == -1 || U == -1) {
        		if(G == -1) data[i] = U;
        		if(U == -1) data[i] = G;
        	} else {
        		avg = (double)((int)(10 * ((G + U) / 2))) / 10;
        		data[i] = avg;
        	}
		}
        System.out.println(Arrays.toString(data));
        return data;
	}
	
	public static boolean isLeap(int year) {
		return (year % 400 == 0 || year % 4 == 0 && year % 100 != 0);
	}
}
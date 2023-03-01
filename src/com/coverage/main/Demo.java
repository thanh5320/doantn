package com.coverage.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

public class Demo {
    public static void main(String[] args) throws IOException {
//		changeTarget(1, 1);
//		changeTarget(2, 1);
//		changeTarget(3, 1);
//		changeTarget(1, 2);
//		changeTarget(2, 2);
//		changeTarget(3, 2);
//
//		changeK(1,1);
//		changeRs(1,1);
		changeRs(1,2);

    }

	public static void changeK(int alg, int domain) throws IOException {
		CSVWriter csv = null;
		if(domain==1){
			csv = new CSVWriter(new FileWriter(new File("results/K_1/"+alg+"_algo.csv"), true));
		}else if(domain == 2){
			csv = new CSVWriter(new FileWriter(new File("results/K_2/"+alg+"_algo.csv"), true));
		}

		for(int i=2;i<=8;i++){
			KM.K = i;
			KM km = new KM("resources/input_"+domain+"/target_"+domain+"000x"+domain+"000_100.txt", alg);
			km.run();

			int numOfSensors = km.getNumOfSensors();
			int numOfRelays = km.getNumOfRelays();
			double cost = km.getCost();

			String[] row = new String[4];
			row[0] = Integer.toString(KM.K);
			row[1] = Integer.toString(numOfSensors);
			row[2] = Integer.toString(numOfRelays);
			row[3] = Double.toString(cost);
			csv.writeNext(row);
		}

		csv.close();
	}

	public static void changeRs(int alg, int domain) throws IOException {
		KM.K = 4;
		CSVWriter csv = null;
		if(domain==1){
			csv = new CSVWriter(new FileWriter(new File("results/Rs_1/"+alg+"_algo.csv"), true));

		}else if(domain == 2){
			csv = new CSVWriter(new FileWriter(new File("results/Rs_2/"+alg+"_algo.csv"), true));
		}

		for(int i=40;i<=100;i=i+10){
			KM.RS = i;
			KM km = new KM("resources/input_"+domain+"/target_"+domain+"000x"+domain+"000_100.txt", alg);
			km.run();

			int numOfSensors = km.getNumOfSensors();
			int numOfRelays = km.getNumOfRelays();
			double cost = km.getCost();

			String[] row = new String[4];
			row[0] = Integer.toString((int) KM.RS);
			row[1] = Integer.toString(numOfSensors);
			row[2] = Integer.toString(numOfRelays);
			row[3] = Double.toString(cost);
			csv.writeNext(row);
		}

		csv.close();
	}
    
    // change target
    public static void changeTarget(int alg, int domain) throws IOException {
    	List<String> filenames = new ArrayList<String>();
    	for(int i=100; i<=400; i=i+50) {
			String filename = "";
			if(domain == 1){
				filename = "resources/input_1/target_1000x1000_" + i + ".txt";
			}else if(domain == 2){
				filename = "resources/input_2/target_2000x2000_" + i + ".txt";
			}
    		filenames.add(filename);
    	}

		CSVWriter csv = null;
		if(domain == 1){
			csv = new CSVWriter(new FileWriter(new File("results/T_1/"+alg+"_algo.csv")));
		}else if(domain == 2){
			csv = new CSVWriter(new FileWriter(new File("results/T_2/"+alg+"_algo.csv")));
		}
    	int numTarget = 100;
    	for(String filename: filenames) {
        	KM km = new KM(filename, alg);
    		km.run();
    		
    		int numOfSensors = km.getNumOfSensors();
    		int numOfRelays = km.getNumOfRelays();
    		double cost = km.getCost();
    		
    		String[] row = new String[4];
    		row[0] = Integer.toString(numTarget);
    		row[1] = Integer.toString(numOfSensors);
    		row[2] = Integer.toString(numOfRelays);
    		row[3] = Double.toString(cost);
    		
    		csv.writeNext(row);
        	
        	numTarget += 50;
    	}
    	
    	csv.close();
    }
}

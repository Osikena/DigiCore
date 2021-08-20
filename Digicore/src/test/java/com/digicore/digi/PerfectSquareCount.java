/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digicore.digi;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Osikena
 */
public class PerfectSquareCount {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		ArrayList<Double> perfectsqu = new ArrayList<Double>();
		
		int n = scan.nextInt();
		
		for(int k = 0; k < n; k++){
			
			double answer = 0;
			int firsttvalue = scan.nextInt();
			int secoundvalue = scan.nextInt();
			
			for(double i = 1; i <= firsttvalue; i++){

				for(double j = 1; j <= secoundvalue; j++){

					if(i == j){
						
						answer = answer + Math.pow(i, 2);
						
					}
					
				}
				
			}
			
			perfectsqu.add(answer);

		}
		
		System.out.println("Output");
		
		for(int i = 0; i < perfectsqu.size(); i++){
			
			System.out.println(Math.round(perfectsqu.get(i)));
			
		}
		
	}
	
}

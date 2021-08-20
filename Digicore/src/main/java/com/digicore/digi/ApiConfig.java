/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digicore.digi;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Osikena
 */
public class ApiConfig {
	
	Logic logic = new Logic();
	
	public ArrayList<String> getdetailslist(String colname, String filename){
		
		JSONParser pass = new JSONParser();
		ArrayList<String> namelist = new ArrayList<String>();
		try{
			
			File file = new File("./src/main/resources/api/" + filename);
			FileReader read = new FileReader(file);
			Object obj = pass.parse(read);
			JSONArray list = (JSONArray) obj;
			System.out.println(list);
			JSONObject get = new JSONObject();
			list.forEach((each) -> {
				JSONObject readd = (JSONObject) each;
				String name = (String) readd.get(colname);
				namelist.add(name);
				System.out.println(name);
				});
			
		}catch(Exception e){
			System.out.println(e);
		}
		return namelist;
	}
	
	public long getaccountnumber(){
		
		long accountnumber = logic.generateaccountnumber();
		int x = 0;
		ArrayList<String> accnumlist = getdetailslist("Account_Number", "userinfofile.json");
		for(int i = 0; i < accnumlist.size(); i++){
			long accnum = Long.parseLong(accnumlist.get(i));
			if(accountnumber == accnum){
				x++;
				break;
			}else{
				
			}
		}
		return accountnumber;
		
	}
	
	public void setlogindetails(UserData data){
		ArrayList<String> userlist = getdetailslist("Account_Number", "userinfofile.json");
		ArrayList<String> passwordlist = getdetailslist("Password", "userinfofile.json");
		for(int i = 0; i < userlist.size(); i++){
			data.setUsername(userlist.get(i));
			data.setPassword(passwordlist.get(i));
		}
		
		
	}
	
	public void createaccount(UserData data){
		
		long accountnumber = getaccountnumber();
		System.out.println(data.getFullname());
		System.out.println(data.getUsername());
		System.out.println(data.getPassword());
		System.out.println(accountnumber);
		System.out.println(data.getInitialdeposit());
		
		
		
		JSONObject send = new JSONObject();
		send.put("Full_Name", data.getFullname());
		send.put("Username", data.getUsername());
		send.put("Password", data.getPassword());
		send.put("Account_Number", Long.toString(accountnumber));
		send.put("Initial_Deposit", data.getInitialdeposit());
		JSONArray list = new JSONArray();
		list.add(send);
		
		JSONObject send1 = new JSONObject();
		send1.put("Account_Number", Long.toString(accountnumber));
		send1.put("Balance", data.getInitialdeposit());
		JSONArray list2 = new JSONArray();
		list2.add(send1);
		
		try{
			File file = new File("./src/main/resources/api/userinfofile.json");
			FileWriter write = new FileWriter(file);
			write.write(list.toJSONString());
			write.flush();
			
			File file2 = new File("./src/main/resources/api/accountstatement.json");
			FileWriter write2 = new FileWriter(file2);
			write2.write(list2.toJSONString());
			write2.flush();
			
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	public void getAccountInformation(UserData data, String accnum){
		
		ArrayList<String> namelist = getdetailslist("Full_Name", "userinfofile.json");
		ArrayList<String> accountnumber = getdetailslist("Account_Number", "userinfofile.json");
		ArrayList<String> balance = getdetailslist("Balance", "accountstatement.json");
		
		for(int i = 0; i < namelist.size(); i++){
			if(accountnumber.get(i).equals(accnum)){
				data.setAccountnumber(accountnumber.get(i));
				data.setFullname(namelist.get(i));
				data.setBalance(balance.get(i));
			}
		}
		
	}
	
	
	public void getWithdrawalInformation(UserData data, String accnum){
		
		ArrayList<String> accountnumber = getdetailslist("Account_Number", "accountstatement.json");
		ArrayList<String> balance = getdetailslist("Balance", "accountstatement.json");
		
		for(int i = 0; i < accountnumber.size(); i++){
			if(accountnumber.get(i).equals(accnum)){
				data.setAccountnumber(accountnumber.get(i));
				data.setBalance(balance.get(i));
				data.setNarration("Withdrawal");
				data.setDate(logic.date());
			}
		}
		
	}
	
	public void setWithdrawalInformation(UserData data, String balance){
		
		try{
			File file = new File("./src/main/resources/api/accountstatement.json");
			JSONParser pass = new JSONParser();
			FileReader read = new FileReader(file);
			Object obj = pass.parse(read);
			JSONArray list = (JSONArray) obj;
			System.out.println(list);
			list.forEach((each) -> {
				JSONObject readd = (JSONObject) each;
				String accountnumber = (String) readd.get("Account_Number");
				if(accountnumber.equals(data.getAccountnumber())){
					
					int x = list.indexOf(each);
					JSONObject send = new JSONObject();
					send.put("Account_Number", data.getAccountnumber());
					send.put("Balance", balance);
					list.set(x, send);
					try{
						
						FileWriter write = new FileWriter(file);
						write.write(list.toJSONString());
						write.flush();
					
					}catch(Exception e){
						System.out.println(e);
					}
//					
					
					
				}
			});
			
//			File file1 = new File("./src/main/resources/api/transactionstatement.json");
//			JSONParser pass1 = new JSONParser();
//			FileReader read1 = new FileReader(file1);
//			Object obj1 = pass1.parse(read1);
//			JSONArray list1 = (JSONArray) obj1;
//		
//			JSONObject send = new JSONObject();
//			send.put("Full_Name", data.getFullname());
//			send.put("Username", data.getUsername());
//			send.put("Password", data.getPassword());
//			send.put("Account_Number", data.getAccountnumber());
//			send.put("Initial_Deposit", data.getInitialdeposit());
//			list1.add(send);
//			
//			FileWriter write = new FileWriter(file1);
//			write.write(list1.toJSONString());
//			write.flush();
			
		}catch(Exception e){
			System.out.println(e);
		}
		
	
	
	}
	
	
	
	
}

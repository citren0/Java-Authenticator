import java.io.BufferedReader;  
import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.*;

public class Authenticate {
	public static String filePath = "/mnt/c/Users/alpha/eclipse-workspace/Authentication/src/Java-Authenticator/userdata.csv";
	
	
	public static Scanner read = new Scanner(System.in);
	public static void main(String args[]) {
		if(Arrays.asList(args).contains("--help")) {
			help();
		} 
		
		if(Arrays.asList(args).contains("-m")) {
			if(passwordCheck(args[1], args[2]) == true) {
				System.out.println("Access Granted.");
				System.exit(0);
			} else {
				System.out.println("Access Denied.");
				System.exit(0);
			}
		}



		System.out.print("\n1. Log in\n2. Quit\nChoice: ");
		switch(read.nextInt()) {
			case 0:
				System.exit(0);
				break;
			case 1:
				System.out.print("\nEnter your username: ");
				String username = read.next();
				System.out.print("\nEnter your password: ");
				String password = read.next();
				if(passwordCheck(username, password) == true) {
					System.out.println("Access Granted.");
					System.exit(0);
				} else {
					System.out.println("Access Denied.");
					main(null);
				}
		}
		
	}
	
	public static boolean passwordCheck(String username, String password) {
		boolean allowed = false;
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuffer hexString = new StringBuffer();
		    for (int i = 0; i < hash.length; i++) {
		    	String hex = Integer.toHexString(0xff & hash[i]);
		    	if(hex.length() == 1) hexString.append('0');
		        	hexString.append(hex);
		    }
		    String hashString = hexString.toString();
		    
		    BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
		    String row = null;
		    boolean stop = false;
		    while((row = csvReader.readLine()) != null) {
		    	
		    	String[] data = row.split(",");
		    	
		    	if(stop == true) {
		    		String fileHash = String.join(",", data);
		    		if(fileHash.contains(hashString)) {
		    			allowed = true;
		    		}
		    	}
		    	
		    	if(String.join(",", data).substring(0, String.join(",", data).indexOf(",")).equals(username)) {
		    		stop = true;
		    	}
		    }
		    csvReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return allowed;

	}

	public static void help() {
		System.out.println("Authenticate.java -m [username] [password]");
		System.out.println("Authenticate.java with no args for interface.");
		System.exit(0);
	}
}

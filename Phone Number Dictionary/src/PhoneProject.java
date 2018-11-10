import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class PhoneProject {

	public static void main(String[] args) throws FileNotFoundException {

		// Hold all the inputs
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		// Holds the user's input
		String phoneNumber = "";

		// Create a scanner object that allows the user to add an input
		Scanner userInput = new Scanner(System.in);

		// Hold the flag if the input is a phone number
		boolean isPhoneNumber;

		// Holds the value list that is returned from the map
		List<String> mapValue;

		// Store the text file into a map
		AddWords(map);
		// Print out the map

		while (!phoneNumber.equals("exit")) {
			// Reset the phone number flag
			isPhoneNumber = true;
			System.out.println(
					"Input a 10 digit phone number or \"exit\" to exit \nIf a 11 digit number is entered, the first digit will be removed");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n");
			phoneNumber = userInput.next();
			
			// Remove all dashes, assuming that the user puts the dashes in the number in
			// the correct order
			phoneNumber = phoneNumber.replace("-", "");

			// If the input is not a 10 digit number or a 11 digit number, reset prompt
			if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
				System.out.println("See the instructions for the correct number of digits to enter! There will be no star by your name!");
				continue;
			}

			// Converts any 11 digit number to a 10 digit number by removing the first char
			if (phoneNumber.length() == 11) {
				phoneNumber = phoneNumber.substring(1);
			}

			// Test if every character is a digit
			for (int i = 0; i < phoneNumber.length(); i++) {
				// If char is not a digit, break from the for loop and trigger isPhoneNumber
				// flag
				if (!Character.isDigit(phoneNumber.charAt(i))) {
					System.out.println("Enter only digits! There will be no star by your name!");
					isPhoneNumber = false;
					break;
				}
			}

			// If it is not a phone number, reset prompt
			if (!isPhoneNumber) {
				continue;
			}

			String area = phoneNumber.substring(0, 3);
			String exchange = phoneNumber.substring(3, 6);
			String number = phoneNumber.substring(6);

			mapValue = GetMapValue(phoneNumber.substring(0, 3), map);

			// Print any word that exists in the area code
			mapValue = GetMapValue(area, map);
			//If the mapValue is not null
			if(mapValue != null) {
				PrintArea(mapValue, exchange, number);
			}

			// Print any word that exist in the exchange
			mapValue = GetMapValue(exchange, map);
			if(mapValue != null) {
				PrintExchange(mapValue, exchange, number);
			}

			// Print any word that exist in the number
			mapValue = GetMapValue(number, map);
			if(mapValue != null) {
				PrintNumber(mapValue, area, exchange);
			}

			// Print any word that exists as a combination of area+exchange
			mapValue = GetMapValue((area + exchange), map);
			if(mapValue != null) {
				PrintAreaExchange(mapValue, number);
			}
			
			// Print any word that exists as a combination of exchange+number
			mapValue = GetMapValue((exchange+number), map);
			if(mapValue != null) {
				PrintExchangeNumber(mapValue, area);
			}
			
			// Print any word that exists as a combination of area+exchange+number
			mapValue = GetMapValue((area+exchange+number), map);
			if(mapValue != null) {
				PrintAreaExchangeNumber(mapValue);
			}

			// Print next line
			System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++");

		}
		System.out.println("Thank you for using the program!");

	}

	// Store word.txt as a map
	public static void AddWords(HashMap<String, List<String>> map) throws FileNotFoundException {
		// Path for the file
		File file = new File(
				"C:\\Users\\peter\\OneDrive\\WKU\\_ 2018 Fall\\Algorithms\\Projects\\Project 2\\src\\test\\words.txt");

		// Create scanner object
		Scanner scanner = new Scanner(file);
		// Holds the word
		String word;
		String key;
		// Iterate through each word in the file
		while (scanner.hasNextLine()) {
			// Grab each word form each line of the file
			word = scanner.nextLine();

			// Only add words to the dictionary that are the correct length
			if (word.length() == 3 || word.length() == 4 || word.length() == 6 || word.length() == 7
					|| word.length() == 10) {

				// Generate the key
				key = GetKey(word);

//				System.out.println(key + " " + word);

				// If the key does not exist, create a new list and store it into the map
				if (!map.containsKey(key)) {
					List<String> newList = new ArrayList<String>();
					newList.add(word);
					map.put(key, newList);
				}
				// Grab the old list and add the new word to it
				else {
					List<String> currentList = map.get(key);
					currentList.add(word);
				}
			}

		}
//		PrintMap(map);
	}

	// Converts words into key values
	public static String GetKey(String word) {
		String newWord = "";
		int numWord = 0;

		// Convert each word to upper case
		word = word.toUpperCase();

		// Iterate through each char in word to build a key
		for (int i = 0; i < word.length(); i++) {

			if (word.charAt(i) == 'A' || word.charAt(i) == 'B' || word.charAt(i) == 'C') {
				newWord = newWord + '2';
			} else if (word.charAt(i) == 'D' || word.charAt(i) == 'E' || word.charAt(i) == 'F') {
				newWord = newWord + '3';
			} else if (word.charAt(i) == 'G' || word.charAt(i) == 'H' || word.charAt(i) == 'I') {
				newWord = newWord + '4';
			} else if (word.charAt(i) == 'J' || word.charAt(i) == 'K' || word.charAt(i) == 'L') {
				newWord = newWord + '5';
			} else if (word.charAt(i) == 'M' || word.charAt(i) == 'N' || word.charAt(i) == 'O') {
				newWord = newWord + '6';
			} else if (word.charAt(i) == 'P' || word.charAt(i) == 'Q' || word.charAt(i) == 'R'
					|| word.charAt(i) == 'S') {
				newWord = newWord + '7';
			} else if (word.charAt(i) == 'T' || word.charAt(i) == 'U' || word.charAt(i) == 'V') {
				newWord = newWord + '8';
			} else if (word.charAt(i) == 'W' || word.charAt(i) == 'X' || word.charAt(i) == 'Y'
					|| word.charAt(i) == 'Z') {
				newWord = newWord + '9';
			}
		}

		// Return the key as a long
		return newWord;
	}

	// Print out the map
	public static void PrintMap(HashMap<String, List<String>> map) {
		// Iterate through each value in the map
		for (Entry<String, List<String>> entry : map.entrySet()) {
			String key2 = entry.getKey();
			List<String> value = entry.getValue();
			// Print Key
			System.out.println("\nKey is " + key2);
			// Print value
			PrintAreaExchangeNumber(value);

		}
	}

	public static void PrintArea(List<String> value, String exchange, String number) {
		// Print the values
		for (int i = 0; i < value.size(); i++) {
			System.out.println(value.get(i) + "-" + exchange + "-" + number);
		}
	}

	public static void PrintExchange(List<String> value, String area, String number) {
		// Print the values
		for (int i = 0; i < value.size(); i++) {
			System.out.println(area + "-" + value.get(i) + "-" + number);
		}
	}

	public static void PrintNumber(List<String> value, String area, String exchange) {
		// Print the values
		for (int i = 0; i < value.size(); i++) {
			System.out.println(area + "-" + exchange + "-" + value.get(i));
		}
	}

	public static void PrintAreaExchange(List<String> value, String number) {
		// Print the values
		for (int i = 0; i < value.size(); i++) {
			System.out.println(value.get(i) + "-" + number);
		}
	}
	public static void PrintExchangeNumber(List<String> value, String area) {
		// Print the values
		for (int i = 0; i < value.size(); i++) {
			System.out.println(area + "-" + value.get(i));
		}
	}
	public static void PrintAreaExchangeNumber(List<String> value) {
		// Print the values
		for (int i = 0; i < value.size(); i++) {
			System.out.println(value.get(i));
		}
	}

	public static List<String> GetMapValue(String key, HashMap<String, List<String>> map) {
		List<String> value = map.get(key);
		return value;
	}

}

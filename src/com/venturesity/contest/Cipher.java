package com.venturesity.contest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Codes and Ciphers implementation
 *
 * @author Ranjith Manickam
 * @contact +91 81221 21840
 * @email ranji3886@gmail.com
 */
public class Cipher
{
	private final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * method to convert plain text to cipher text based on the key value
	 * 
	 * @param key
	 * @param plainText
	 * @return
	 */
	public String encrypt(String key, String plainText) {
		return convertText(key, plainText, true);
	}

	/**
	 * method to convert cipher text to plain text based on the key value
	 * 
	 * @param key
	 * @param cipherText
	 * @return
	 */
	public String decrypt(String key, String cipherText) {
		return convertText(key, cipherText, false);
	}

	/**
	 * method to convert the text based on the key value
	 * 
	 * @param key
	 * @param plainText
	 * @param isEncrypt
	 * @return
	 */
	private String convertText(String key, String plainText, boolean isEncrypt) {
		try {
			char[] keyArray = key.toCharArray();
			char[] inputTextArray = plainText.toCharArray();

			List<Integer> keyAscii = new ArrayList<Integer>();
			List<Integer> inputTextAscii = new ArrayList<Integer>();

			// convert key value into equivalent ascii
			for (char character : keyArray) {
				keyAscii.add(convertCharToASCII(character));
			}

			// convert key value into equivalent ascii
			for (char character : inputTextArray) {
				inputTextAscii.add(convertCharToASCII(character));
			}

			List<Integer> outputTextAscii = new ArrayList<Integer>();
			int keyIndex = 0;

			// performing cipher operation
			for (int plainTextIndex = 0; plainTextIndex < inputTextAscii.size(); plainTextIndex++) {
				if (inputTextAscii.get(plainTextIndex) <= this.alphabet.length) {
					try {
						keyAscii.get(keyIndex);
					} catch (IndexOutOfBoundsException ex) {
						keyIndex = 0;
					}
					if (isEncrypt) {
						outputTextAscii.add(inputTextAscii.get(plainTextIndex) + keyAscii.get(keyIndex));
					} else {
						outputTextAscii.add(inputTextAscii.get(plainTextIndex) - keyAscii.get(keyIndex));
					}
					keyIndex++;
				} else {
					outputTextAscii.add(inputTextAscii.get(plainTextIndex));
				}
			}

			List<Character> outputTextArray = new ArrayList<Character>();
			for (Integer ascii : outputTextAscii) {
				if (ascii <= this.alphabet.length * 2) {
					if (isEncrypt) {
						ascii = (ascii % this.alphabet.length) + 1;
					} else {
						if (ascii != 0) {
							ascii = (((ascii % this.alphabet.length) + this.alphabet.length) % this.alphabet.length) - 1;
						} else {
							ascii = this.alphabet.length - 1;
						}
					}
				}
				outputTextArray.add(convertASCIIToChar(ascii));
			}

			// building output text
			String outputText = null;
			boolean isUpperCase = false;
			for (int index = 0; index < outputTextArray.size(); index++) {
				if (Character.isUpperCase(inputTextArray[index])) {
					isUpperCase = true;
				} else {
					isUpperCase = false;
				}
				String text = "";
				text = String.valueOf(outputTextArray.get(index));

				if (isUpperCase) {
					text = text.toUpperCase();
				}

				if (outputText == null) {
					outputText = text;
				} else {
					outputText = outputText.concat(text);
				}
			}
			return outputText;
		} catch (Exception ex) {
			if (isEncrypt) {
				System.err.println("Error occured while converting text from Plain text to Cipher text: " + ex);
			} else {
				System.err.println("Error occured while converting text from Cipher text to Plain text: " + ex);
			}
		}
		return null;
	}

	/**
	 * method to convert Character to ASCII
	 * 
	 * @param character
	 * @return
	 */
	private int convertCharToASCII(final char character) {
		for (int index = 0; index < this.alphabet.length; index++) {
			if (!isBlank(this.alphabet[index]) && !isBlank(character) && String.valueOf(this.alphabet[index]).equalsIgnoreCase(String.valueOf(character))) {
				return index + 1;
			}
		}
		if (character == ' ') {
			return 0;
		}
		int ascii = (int) character + 999;
		return ascii;
	}

	/**
	 * method to convert ASCII to Character
	 * 
	 * @param ascii
	 * @return
	 */
	private char convertASCIIToChar(final int ascii) {
		try {
			if (ascii <= this.alphabet.length) {
				return this.alphabet[ascii - 1];
			} else {
				// for non alphabetical characters
				int temp = ascii - 999;
				return (char) temp;
			}
		} catch (IndexOutOfBoundsException ex) {
			return ' ';
		}
	}

	/**
	 * method to check the character is empty
	 * 
	 * @param character
	 * @return
	 */
	private boolean isBlank(char character) {
		return String.valueOf(character).isEmpty();
	}

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = null;
		try {
			// scanner to read data from user
			scanner = new Scanner(System.in);

			System.out.println("Key:");
			String key = scanner.nextLine();

			System.out.println("Is message plaintext (Y/N):");
			boolean isEncrypt = Boolean.valueOf(scanner.nextLine().equalsIgnoreCase("Y") ? "true" : "false");

			System.out.println("Plaintext:");
			String inputText = scanner.nextLine();

			// creating object for this class
			Cipher cipher = new Cipher();

			String outputText = null;
			if (isEncrypt) {
				// cipher text - encrypt
				outputText = "Cipher Text: " + cipher.encrypt(key, inputText);
			} else {
				// plain text - decrypt
				outputText = "Plain Text: " + cipher.decrypt(key, outputText);
			}

			System.out.println("Output:");
			System.out.println("-------");
			System.out.println(outputText);

		} catch (Exception ex) {
			System.err.println("Error:" + ex);
		} finally {
			scanner.close();
		}

		// author details
		System.out.println("\n\nImplemented by,");
		System.out.println("Author\t: Ranjith Manickam");
		System.out.println("Contact\t: +91 81221 21840");
		System.out.println("Email\t: ranji3886@gmail.com");
	}
}

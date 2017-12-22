/**
 *   File Name: WebElementsHelper.java<br>
 *
 *   LastName, Roman<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Jean-francois Nepton<br>
 *   Created: Dec 21, 2017
 *   
 */

package com.willowtreeapps;

/**
 * WebElementsHelper //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 * 
 * @author FirstName, Andrii
 * @version 1.0.0
 * @since 1.0
 *
 */
public class WebElementsHelper {

	public static String attempts = "//span[@class='attempts']";

	public static String correct = "//span[@class='correct']";

	public static String name = "//span[@id='name']";
	public static String streak = "//span[@class='streak']";

	public static String selectedPerson(String name) {
		return "//div[text()='" + name + "']/..";

	}

}

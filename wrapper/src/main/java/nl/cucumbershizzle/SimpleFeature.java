package nl.cucumbershizzle;

import static nl.cucumbershizzle.CZucumber.Given;
import static nl.cucumbershizzle.CZucumber.When;
import static nl.cucumbershizzle.CZucumber.Then;
import static nl.cucumbershizzle.CZucumber.assertEquals;
import static nl.cucumbershizzle.CZucumber.assertNull;
import static nl.cucumbershizzle.CZucumber.assertFalse;

public class SimpleFeature extends AbstractCZucumberFeature {
	
	private String username;
	private boolean loggedIn;
	
	@Komkommer
	public void testje() {
		Given("^Een ingelogde gebruiker$", () -> {
        	username = "Henk";
        });
		When("^Gebruiker zijn naam veranderd in Jaap$", () -> {
        	username = "Jaap";
        });
		Then("^Gebruiksnaam ook veranderd in Jaap$", () -> {
        	assertEquals("Jaap", username);
        });
	}
	
	@Komkommer
	/**
	 * Deze test methode gaat via methodes op de base class.
	 * Bij de given wordt een wrapper gebruikt waardoor het mogelijk wordt
	 * om de juiste data in te laden aan de hand van de test methode naam.
	 */
	public void testje2() {
		given("^Een ingelogde gebruiker$", () -> {
        	username = "Henk";
        });
		when("^Gebruiker zijn naam is veranderd in Jaap$", () -> {
        	username = "Jaap";
        });
		then("^Gebruiksnaam ook veranderd in Jaap$", () -> {
        	assertEquals("Jaap", username);
        });
	}

	@Komkommer
	public void logout() {
		given("^Een ingelogde gebruiker$", () -> {
        	username = "Henk";
        	loggedIn = true;
        });
		when("^Gebruiker logt uit$", () -> {
        	username = null;
        	loggedIn = false;
        });
		then("^Gebruikersnaam is null$", () -> {
        	assertNull(username);
        });
		and("^Gebruiker is uitgelogd$", () -> {
        	assertFalse(loggedIn);
        });
	}

}

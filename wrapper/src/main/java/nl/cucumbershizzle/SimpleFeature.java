package nl.cucumbershizzle;

import static nl.cucumbershizzle.CZucumber.Given;
import static nl.cucumbershizzle.CZucumber.When;
import static nl.cucumbershizzle.CZucumber.Then;
import static nl.cucumbershizzle.CZucumber.assertEquals;

public class SimpleFeature extends AbstractCZucumberFeature {
	
	private String username;
	
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


}

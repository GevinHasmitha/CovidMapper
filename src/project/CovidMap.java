package project;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import module5.CommonMarker;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import java.util.HashMap;
import java.util.Map;


import de.fhpotsdam.unfolding.marker.Marker;

/**
 * Visualizes covid status in different countries. 
 * 
 * It loads the country shapes from a GeoJSON file via a data reader, and loads the population density values from
 * another CSV file (provided by the World Bank). The data value is encoded to transparency via a simplistic linear
 * mapping.
 */
public class CovidMap extends PApplet {

	UnfoldingMap map;
	Map<String, Float> covidCasesByCountry;
	
	Map<String, Float> covidCasesByCountry1;
	//Map<String, Float> covidDeathsByCountry1;
	//Map<String, Float> covidVaccinationsByCountry1;
	List<Feature> countries;
	List<Marker> countryMarkers;
	int loadValue=0;

	public void setup() {
		size(1400,800, OPENGL);
		map = new UnfoldingMap(this, 200, 20, 1000, 750, new Google.GoogleMapProvider());
		//Microsoft.RoadProvider() --a map provider
		//map = new UnfoldingMap(this, 100, 50, 700, 500, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);

		// Load lifeExpectancy data
		//if (loadValue==0) {
		  background(200);
		  covidCasesByCountry = loadCovidCasesFromCSV("owid-covid-data.csv");
		  println("Loaded " + covidCasesByCountry.size() + " data entries");
	//	}

		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		
		// Country markers are shaded according to life expectancy (only once)
		if (loadValue==0) {
		  shadeCountries_newCases();
	//	  covidCasesByCountry1 = loadCovidCasesFromCSV("owid-covid-data.csv");
		}else if (loadValue==1) {
		  shadeCountries_deaths();
	//	  covidDeathsByCountry1 = loadCovidCasesFromCSV("owid-covid-data.csv");
		}else if (loadValue==2) {
		  shadeCountries_vaccinations();
	//	  covidVaccinationsByCountry1 = loadCovidCasesFromCSV("owid-covid-data.csv");
		}
	}
	
	
	
	
	boolean clicked = false;
	
	public void mouseReleased()
	{
		if(mouseX>1200 && mouseX<1200+120 && mouseY>380 && mouseY<380+40 && clicked) {	
			  System.out.println("yesss");
			  loadValue=0;
			  setup();
		}else if (mouseX>1200 && mouseX<1200+120 && mouseY>450 && mouseY<450+40 && clicked) {	
			  System.out.println("yesss");
			  loadValue=1;
			  setup();
		}else if (mouseX>1200 && mouseX<1200+120 && mouseY>520 && mouseY<520+40 && clicked) {	
			  System.out.println("yesss");
			  loadValue=2;
			  setup();
		}
	}
	
	public void mouseMoved() {
		//background(200);
		for (Marker marker : countryMarkers ) {
			if(marker.isInside(map,mouseX, mouseY)) {
				//data of deaths, vaccines,cases
				System.out.println(covidCasesByCountry.get(marker.getId()));
			//	fill(0,0,0);
			//	rect(100,600,180,100);
			//	text("Tot covid cases", 100, 600);
				text("Country:  Austrailia", 100, 580);
				text("No of deaths", 100, 600);
				text("10 302", 100, 620);
				//background(200);
			//	System.out.println("1"+covidCasesByCountry1.get(marker.getId()));
			//	System.out.println("2:"+covidDeathsByCountry1.get(marker.getId()));
			//	System.out.println("3"+covidVaccinationsByCountry1.get(marker.getId()));
			}
		}
	}
	public void draw() {
		// Draw map tiles and country markers
		map.draw();
		
		fill(100,149,237);
		rect(1200,380,120,40,28);
		fill(205, 92, 92);
		rect(1200,450,120,40,28);
		fill(188, 233, 84);
		rect(1200,520,120,40,28);
		
		if (mousePressed) {
			clicked = !clicked;
		}
		if(loadValue==0) {
			fill(255,255,255);
			rect(20, 20, 210, 380);
			
			fill(21, 27, 141);
			rect(50, 80, 10, 15);
			
			fill(21, 105, 199);
			rect(50, 120, 10, 15);
			
			fill(72, 138, 199);
			rect(50, 160, 10, 15);
			
			fill(56, 172, 236);
			rect(50, 200, 10, 15);
			
			fill(130, 202, 199);
			rect(50, 240, 10,15);
			
			fill(255,216,1);
			rect(50, 280, 10, 15);
			
			fill(255,255,255);
			rect(50, 320, 10, 15);
			
			fill(150, 150, 150);
			rect(50, 360, 10, 15);
			
			fill(0,0,0);
			textSize(13);
			text("> 5,000,000", 70, 92);
			text("500,000 - 5,000,000", 70, 132);
			text("50,000 - 500,000", 70, 172);
			text("5000 - 50,000", 70, 212);
			text("1 - 5000", 70, 252);
			text("0", 70, 292);
			text("No reported data", 70, 332);
			text("Not Applicable", 70, 372);
			
			textSize(16);
			text("No. of Cases", 70, 52);
			
		}
		if(loadValue==1) {
			fill(255,255,255);
			rect(20, 20, 210, 380);
			
			fill(94, 25, 20);
			rect(50, 80, 10, 15);
			
			fill(150, 0, 24);
			rect(50, 120, 10, 15);
			
			fill(194, 24, 7);
			rect(50, 160, 10, 15);
			
			fill(255, 36, 0);
			rect(50, 200, 10, 15);
			
			fill(250, 128, 114);
			rect(50, 240, 10,15);
			
			fill(255,216,1);
			rect(50, 280, 10, 15);
			
			fill(255,255,255);
			rect(50, 320, 10, 15);
			
			fill(150, 150, 150);
			rect(50, 360, 10, 15);
			
			fill(0,0,0);
			textSize(13);
			text("> 500,000", 70, 92);
			text("50,000 - 500,000", 70, 132);
			text("500 - 50,000", 70, 172);
			text("50 - 500", 70, 212);
			text("1 - 50", 70, 252);
			text("0", 70, 292);
			text("No reported data", 70, 332);
			text("Not Applicable", 70, 372);
						
			textSize(16);
			text("Total No. of Deaths", 58, 52);
			
			
		}
		if(loadValue==2) {
			fill(255,255,255);
			rect(20, 20, 210, 380);
			
			fill(0,128,0);
			rect(50, 80, 10, 15);
			
			fill(74, 160, 44);
			rect(50, 120, 10, 15);
			
			fill(108, 187, 60);
			rect(50, 160, 10, 15);
			
			fill(156, 176, 113);
			rect(50, 200, 10, 15);
			
			fill(250, 128, 114);
			rect(50, 240, 10,15);
			
			fill(255, 36, 0);
			rect(50, 280, 10, 15);
			
			fill(255,255,255);
			rect(50, 320, 10, 15);
			
			fill(150, 150, 150);
			rect(50, 360, 10, 15);
			
			fill(0,0,0);
			textSize(13);
			text(">= 100", 70, 92);
			text("70-100", 70, 132);
			text("60 - 70", 70, 172);
			text("40 - 60", 70, 212);
			text("20 - 40", 70, 252);
			text("< 20", 70, 292);
			text("No reported data", 70, 332);
			text("Not Applicable", 70, 372);
						
			textSize(16);
			text("Vaccinations per", 70, 44);
			text("100 people", 74, 60);
			
			
		}
		
	
	}

	//Helper method to color each country based on life expectancy
	//Red-orange indicates low (near 40)
	//Blue indicates high (near 100)
	private void shadeCountries_newCases() {
		int rubber_ducky = color(255,216,1);
		int light_sky = color(130, 202, 199);
		int butterfly = color(56, 172, 236);
		int silk = color(72, 138, 199);
		int blue_eyes = color(21, 105, 199);
		int denim = color(21, 27, 141);
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();
		
			//System.out.println(lifeExpByCountry);
			if (covidCasesByCountry.containsKey(countryId)) {
				//System.out.println(countryId);
				float newCases = covidCasesByCountry.get(countryId);
				System.out.println(covidCasesByCountry);
				//System.out.println(lifeExp);
				if (newCases == 0) {
					marker.setColor(light_sky);
				}else if (newCases >= 1 && newCases < 5000) {
					 marker.setColor(rubber_ducky);
				}else if(newCases >= 5000 && newCases < 50000) {
					marker.setColor(butterfly);
				}else if(newCases >= 50000 && newCases < 500000) {
					marker.setColor(silk);
				}else if(newCases >= 50000 && newCases < 5000000) {
					marker.setColor(blue_eyes);
				}else if(newCases >= 5000000) {
					marker.setColor(denim);			
				}else {
				   marker.setColor(color(150,150,150));
				}
			}
		}
	}
	
	private void shadeCountries_deaths() {
		int rubber_ducky = color(255,216,1);
		int salmon = color(250, 128, 114);
		int scarlet = color(255, 36, 0);
		int clilli_red = color(194, 24, 7);
		int carmine = color(150, 0, 24);
		int sangria = color(94, 25, 20);
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();
		
			//System.out.println(lifeExpByCountry);
			if (covidCasesByCountry.containsKey(countryId)) {
				//System.out.println(countryId);
				float deaths = covidCasesByCountry.get(countryId);
			System.out.println(covidCasesByCountry);
				//System.out.println(lifeExp);
				if (deaths == 0) {
					marker.setColor(rubber_ducky);
				}else if (deaths >= 1 && deaths < 50) {
					 marker.setColor(salmon);
				}else if(deaths >= 50 && deaths < 500) {
					marker.setColor(scarlet);
				}else if(deaths >= 500 && deaths < 50000) {
					marker.setColor(clilli_red);
				}else if(deaths >= 50000 && deaths < 500000) {
					marker.setColor(carmine);
				}else if(deaths >= 500000) {
					marker.setColor(sangria);			
				}else {
				   marker.setColor(color(150,150,150));
				}
			}
		}
	}

	
	private void shadeCountries_vaccinations() {
		int scarlet = color(255, 36, 0);
		int salmon = color(250, 128, 114);
		int iguana = color(156, 176, 113);
		int green_snake = color(108, 187, 60);
		int spring = color(74, 160, 44);
		int green = color(0,128,0);
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();
		
			//System.out.println(lifeExpByCountry);
			if (covidCasesByCountry.containsKey(countryId)) {
				//System.out.println(countryId);
				float vaccinations = covidCasesByCountry.get(countryId);
			//System.out.println(covidCasesByCountry);
				//System.out.println(lifeExp);
				if (vaccinations <=20 ) {
					marker.setColor(scarlet);
				}else if (vaccinations >= 20 && vaccinations < 40) {
					 marker.setColor(salmon);
				}else if(vaccinations >= 40 && vaccinations < 60) {
					marker.setColor(iguana);
				}else if(vaccinations >= 60 && vaccinations < 70) {
					marker.setColor(green_snake);
				}else if(vaccinations >= 70 && vaccinations < 100) {
					marker.setColor(spring);
				}else if(vaccinations >= 100) {
					marker.setColor(green);			
				}else {
				   marker.setColor(color(150,150,150));
				}
			}
		}
	}	
	
	
	
	
	//Helper method to load life expectancy data from file
	private Map<String, Float> loadCovidCasesFromCSV(String fileName) {
		Map<String, Float> CovidMap = new HashMap<String, Float>();

		String[] rows = loadStrings(fileName);
		for (String row : rows) {
		//	System.out.println(row);
			String[] columns = row.split(",");
			try {
			//if (columns.length == 6 &&  !columns[5].equals("..")) {
				if (loadValue==0) {
					CovidMap.put(columns[0], Float.parseFloat(columns[4]));
				}else if (loadValue==1) {
					CovidMap.put(columns[0], Float.parseFloat(columns[7]));
				}else if (loadValue==2) {
					CovidMap.put(columns[0], Float.parseFloat(columns[40]));
				}
			//}
			}catch(Exception e) {
				continue;
			}
		}

		return CovidMap;
	}

}

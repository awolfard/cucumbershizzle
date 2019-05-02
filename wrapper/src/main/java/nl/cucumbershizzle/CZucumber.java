package nl.cucumbershizzle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CZucumber {

	private static final List<Feature> FEATURES = new ArrayList<>();
	private static Feature CURRENTFEATURE = null;
	private static Scenario CURRENTSCENARIO = null;
	
	private static final StringBuilder REPORT = new StringBuilder();

	private CZucumber() {
	}
	
	public static void newFeature() {
		CURRENTFEATURE = new Feature();
		FEATURES.add(CURRENTFEATURE);
	}
	
	public static void newScenario() {
		if (CURRENTFEATURE == null) {
			newFeature();
		}
		CURRENTSCENARIO = new Scenario();
		CURRENTFEATURE.scenarios.add(CURRENTSCENARIO);
	}

	public static void Background(String description, AO action) {
		CURRENTFEATURE.backgroundMap.put(description, action);
	}

	public static void Given(String description, AO action) {
		CURRENTSCENARIO.givenMap.put(description, action);
	}

	public static void When(String description, AO action) {
		CURRENTSCENARIO.whenMap.put(description, action);
	}

	public static void Then(String description, AO action) {
		CURRENTSCENARIO.thenMap.put(description, action);
	}

	public static void And(String description, AO action) {
		CURRENTSCENARIO.andMap.put(description, action);
	}

	public static void execute() {
		REPORT.append("Nr of features:" + FEATURES.size() + "\n");
		FEATURES.forEach(f -> f.execute());
		System.out.println(REPORT.toString());
	}

	public static void assertEquals(String expected, String real) {
		if (!expected.equals(real)) {
			throw new RuntimeException("Oeps");
		}
	}
	
	public static void run(Class<?>...features) {
		for (Class<?> clazz : features) {
			try {
				clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		execute();
	}
	
	private static class Feature {
		private final List<Scenario> scenarios = new ArrayList<>();
		private final Map<String, AO> backgroundMap = new LinkedHashMap<>();
		
		void execute() {
			REPORT.append("---------------------------------------------------\n");
			REPORT.append("Nr of scenarios in feature:" + scenarios.size() + "\n");
			final IntValueHolder ok = new IntValueHolder(0);
			final IntValueHolder nok = new IntValueHolder(0);
			scenarios.forEach(s -> {
				try {
					backgroundMap.entrySet().forEach(b -> b.getValue().accept());
					s.execute();
					ok.inc();
				} catch (Exception e) {
					nok.inc();
				}
			});
			REPORT.append("OK scenarios:" + ok.value() + "\n");
			REPORT.append("Failed scenarios:" + nok.value() + "\n");
			REPORT.append("---------------------------------------------------\n");
		}
		
		private static class IntValueHolder {
			private int value;
			
			IntValueHolder(int value) {
				this.value = value;
			}
			
			int value() {
				return this.value;
			}
			
			void inc() {
				this.value++;
			}
			
		}
	}
	
	private static class Scenario {
		private Map<String, AO> givenMap = new LinkedHashMap<String, AO>();
		private Map<String, AO> whenMap = new LinkedHashMap<String, AO>();
		private Map<String, AO> thenMap = new LinkedHashMap<String, AO>();
		private Map<String, AO> andMap = new LinkedHashMap<String, AO>();
		
		void execute() {
			givenMap.entrySet().forEach(e -> {
				e.getValue().accept();
			});
			whenMap.entrySet().forEach(e -> {
				e.getValue().accept();
			});
			thenMap.entrySet().forEach(e -> {
				e.getValue().accept();
			});
			andMap.entrySet().forEach(e -> {
				e.getValue().accept();
			});
		}
	}

}

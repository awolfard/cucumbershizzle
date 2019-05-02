package nl.cucumbershizzle;

import java.lang.reflect.Method;
import static nl.cucumbershizzle.CZucumber.Given;
import static nl.cucumbershizzle.CZucumber.When;
import static nl.cucumbershizzle.CZucumber.Then;
import static nl.cucumbershizzle.CZucumber.And;
import static nl.cucumbershizzle.CZucumber.Background;

public class AbstractCZucumberFeature {

	public AbstractCZucumberFeature() {
		CZucumber.newFeature();
		registerScenarios();
	}

	private void registerScenarios() {
		try {
			for (Method method : this.getClass().getDeclaredMethods()) {
				if (method.getAnnotation(Komkommer.class) != null) {
					CZucumber.newScenario();
					method.invoke(this);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Whoops", e);
		}
	}

	protected void background(String description, AO action) {
		Background(description, new AOWrapper(action));
	}

	protected void given(String description, AO action) {
		Given(description, new AOWrapper(action));
	}

	protected void when(String description, AO action) {
		When(description, action);
	}

	protected void then(String description, AO action) {
		Then(description, action);
	}

	protected void and(String description, AO action) {
		And(description, new AOWrapper(action));
	}

	private static class AOWrapper implements AO {

		private AO wrappedAO;
		private Method testMethod;

		private AOWrapper(AO wrappedAO) {
			this.wrappedAO = wrappedAO;

			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			for (StackTraceElement stackTraceElement : stackTraceElements) {
				String className = stackTraceElement.getClassName();
				String methodName = stackTraceElement.getMethodName();

				findKomkommerMethod(className, methodName);

				if (this.testMethod != null) {
					break;
				}
			}
		}

		private void findKomkommerMethod(String className, String methodName) {
			try {
				Method[] methods = Class.forName(className).getDeclaredMethods();
				for (Method method : methods) {
					if (methodName.equals(method.getName()) && method.getAnnotation(Komkommer.class) != null) {
						this.testMethod = method;
						break;
					}
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void accept() {
			System.out.println("GIVEN - create database based on method:" + this.testMethod);
			wrappedAO.accept();
		}

	}

}

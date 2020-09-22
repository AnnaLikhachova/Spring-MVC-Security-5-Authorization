package guide.tour.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Application init class,
 * extends {@link AbstractAnnotationConfigDispatcherServletInitializer} class.
 *
 * @author Anna Likhachova
 * @version 1.0
 */

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {AppConfig.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
 
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}

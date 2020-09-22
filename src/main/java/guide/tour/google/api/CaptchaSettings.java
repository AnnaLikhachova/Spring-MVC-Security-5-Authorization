package guide.tour.google.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value= {"classpath:application.properties"})
public class CaptchaSettings {
	@Value("${google.recaptcha.key.site}")
    private String site;
	 @Value("${google.recaptcha.key.secret}")
	private String secret;
    
    
    private  String siteV3;
    
   
    private  String secretV3;
    
    private float threshold;
    
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}
	public String getSiteV3() {
		return siteV3;
	}
	public String getSecretV3() {
		return secretV3;
	}
    public void setSiteV3(String siteV3) {
		this.siteV3 = siteV3;
	}
	public void setSecretV3(String secretV3) {
		this.secretV3 = secretV3;
	}
   
}

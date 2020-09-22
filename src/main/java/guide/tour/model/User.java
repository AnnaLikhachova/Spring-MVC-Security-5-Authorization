package guide.tour.model;

import org.jboss.aerogear.security.otp.api.Base32;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="APP_USER", schema = "guide")
public class User {
 
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@NotNull
    private String name;
	@NotNull
    private String password;
    @Transient
    private String confirmPassword;
    private String email;
    @Transient
    private String portrait;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "APP_USER_USER_PROFILE", 
             joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID", referencedColumnName = "ID") })
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>();
    @OneToOne(mappedBy = "user")
    VerificationToken token;
    private boolean green;
    
    @Transient
    private boolean isUsing2FA;
    
    @Transient
    private String secret;

    public User() {
        super();
        this.green = false;
        this.secret = Base32.random();
    }

	public String getPassword() {
		return this.password;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (green ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isUsing2FA ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((portrait == null) ? 0 : portrait.hashCode());
		result = prime * result + ((secret == null) ? 0 : secret.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((userProfiles == null) ? 0 : userProfiles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (green != other.green)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isUsing2FA != other.isUsing2FA)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (portrait == null) {
			if (other.portrait != null)
				return false;
		} else if (!portrait.equals(other.portrait))
			return false;
		if (secret == null) {
			if (other.secret != null)
				return false;
		} else if (!secret.equals(other.secret))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (userProfiles == null) {
			if (other.userProfiles != null)
				return false;
		} else if (!userProfiles.equals(other.userProfiles))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	public VerificationToken getToken() {
		return token;
	}

	public void setToken(VerificationToken token) {
		this.token = token;
	}

	public boolean isGreen() {
		return green;
	}

	public void setGreen(boolean green) {
		this.green = green;
	}

	public boolean isUsing2FA() {
		return isUsing2FA;
	}

	public void setUsing2FA(boolean using2FA) {
		isUsing2FA = using2FA;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
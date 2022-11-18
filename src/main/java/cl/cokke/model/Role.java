package cl.cokke.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ADMIN, CLIENT, USER;
	
	public String getAuthority() {
		return name();
	}
}

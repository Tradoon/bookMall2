package com.tradoon.bookMall.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradoon.bookMall.model.UmsAdmin;
import com.tradoon.bookMall.model.UmsResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * author:tradoon
 * desciption:
 * date:2022/ / /
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"enabled","accountNonExpired", "accountNonLocked",
        "credentialsNonExpired", "authorities","password","username"})
public class AdminUserDetails implements UserDetails {
    UmsAdmin umsAdmin;
    List<UmsResource> resourceList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return resourceList.stream().map(data->new SimpleGrantedAuthority(data.getValue())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

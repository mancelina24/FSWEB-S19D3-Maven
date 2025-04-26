package com.workintech.s19d2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "member", schema = "bank")
//UserDetails interfacei memberın username, password bilgisini getiren ve memberın sisteme giriş yapıp yapamayacağını
//belirleyen methodlar içeriyor.
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    //Member çağırıldığında Role bizimle birlikte gelmemeli, çünkü sistem yavaşlar, ekstra sqller çalıştığı için.
    //Ama şimdi EAGER olarak tanımladığımızda ManyToMany ilişki için rollerinde gelmesini sağlıyoruz, normalde LAZYdir.
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "member_role", schema = "bank", joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    //Memberın authoritieslerini nereden okuyacağını söyleyen method
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    //Kullanıcımızın durumlarını anlatıyor 4 method, bunların herhangi bir false olursa kullanıcı sisteme giriş yapamaz.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Accountu locklamak için banlamak için. false olursa hesabı banlanır.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //Maile tıklayıp accountu aktif olursa true olur.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //Bazen kullanıcılar sistemden belli bir süre silinmez ama durur, false olursa sisteme giriş yapamaz.
    @Override
    public boolean isEnabled() {
        return true;
    }
}
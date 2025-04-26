package com.workintech.s19d2.entity;




//GrantedAuthority getAuthorities methodu içeriyor ama Lombok zaten onu yazdığı için Override etmemize gerek yok
//Bu şekilde memberın hangi role sahip olduğunu getiriyoruz.

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "role", schema = "bank")
//GrantedAuthority getAuthorities methodu içeriyor ama Lombok zaten onu yazdığı için Override etmemize gerek yok
//Bu şekilde memberın hangi role sahip olduğunu getiriyoruz.
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority")
    private String authority;

    //Role üstünden membera gitmemize gerek kalmadığı için şuanda Uni-directional çalışıyoruz.
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "member_role", schema = "bank", joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "member_id"))
//    private Member member;
}
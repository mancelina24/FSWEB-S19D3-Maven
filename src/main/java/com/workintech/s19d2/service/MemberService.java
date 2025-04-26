package com.workintech.s19d2.service;

import com.workintech.s19d2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//MemberService'de memberRepository ile o kullanıcının var olup olmadığını kontrol ediyoruz. Logic işlemleri gerçekleş-
//tiriyoruz.
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository)
    {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username not found!");
        });
    }
}
package com.eralparda.PerfumeWeb.Service;

import com.eralparda.PerfumeWeb.Entity.Perfume;
import com.eralparda.PerfumeWeb.Entity.User;
import com.eralparda.PerfumeWeb.Exception.BadRequestException;
import com.eralparda.PerfumeWeb.Exception.NotFoundException;
import com.eralparda.PerfumeWeb.Repository.PerfumeRepository;
import com.eralparda.PerfumeWeb.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserRepository userRepository;
    private final PerfumeRepository perfumeRepository;

    @Transactional
    public List<Perfume> addFavorite(Long perfumeId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new NotFoundException("Perfume not found!"));
        if(user.getFavorites().contains(perfume)){
            throw new BadRequestException("Bu parfüm zaten bulunuyor!");
        }
        user.getFavorites().add(perfume);
        userRepository.save(user);
        return user.getFavorites();
    }

    @Transactional
    public List<Perfume> removeFavorite(Long perfumeId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        Perfume perfume = perfumeRepository.findById(perfumeId).
                orElseThrow(()-> new NotFoundException("Perfume not found!"));
        user.getFavorites().remove(perfume);
        userRepository.save(user);
        return user.getFavorites();
    }

    public List<Perfume> getFavorites(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        return  user.getFavorites();
    }
}

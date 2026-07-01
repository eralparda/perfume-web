package com.eralparda.PerfumeWeb.Controller;

import com.eralparda.PerfumeWeb.Entity.Perfume;
import com.eralparda.PerfumeWeb.Service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{perfumeId}")
    public List<Perfume> addFavorite(@PathVariable Long perfumeId){
        return favoriteService.addFavorite(perfumeId);
    }

    @DeleteMapping("/{perfumeId}")
    public List<Perfume> removeFavorite(@PathVariable Long perfumeId){
        return favoriteService.removeFavorite(perfumeId);
    }

    @GetMapping()
    public  List<Perfume> getFavorites(){
        return  favoriteService.getFavorites();
    }
}

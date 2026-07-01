package com.eralparda.PerfumeWeb.Service;

import  com.eralparda.PerfumeWeb.DTO.LoginRequest;
import com.eralparda.PerfumeWeb.DTO.RegisterRequest;
import com.eralparda.PerfumeWeb.DTO.UserRequest;
import com.eralparda.PerfumeWeb.Entity.User;
import com.eralparda.PerfumeWeb.Enum.Role;
import com.eralparda.PerfumeWeb.Exception.BadRequestException;
import com.eralparda.PerfumeWeb.Exception.NotFoundException;
import com.eralparda.PerfumeWeb.Exception.UnauthorizedException;
import com.eralparda.PerfumeWeb.Repository.UserRepository;
import com.eralparda.PerfumeWeb.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //REGISTER
    public User register(RegisterRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BadRequestException("Email already exist!");
        }

        User user = new User();
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());
        //PASSWORD ENCRYPT
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        //DEFAULT ROLE USER
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    //LOGIN
    public String login(LoginRequest request){
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
            throw new UnauthorizedException("Wrong password!");
        }
        String token = jwtService.generateToken(userDetails);
        return token;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User not found!"));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User not found!"));
    }

    public User updateUser(Long id, UserRequest request){
        User user = getUserById(id);
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        if(request.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public boolean isAdmin(User user){
       return user.getRole() == Role.ADMIN;
    }
}

package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public boolean DuplicatUser(String username) {
    	return userRepository.findByUsername(username).isPresent();
    }

    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        Period period = Period.between(birthDate, currentDate);
        if (currentDate.getMonthValue() < birthDate.getMonthValue() ||
                (currentDate.getMonthValue() == birthDate.getMonthValue() && currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
            return period.getYears() - 1;
        }
        return period.getYears();
    }

    public User register(String username, String password, LocalDate userbirth) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);  // 실제 서비스에서는 비밀번호를 암호화해야 합니다.
        user.setUserbirth(userbirth);

        LocalDate today = LocalDate.now();
        user.setUserage(calculateAge(userbirth, today));

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user); // 저장 메서드 사용, 만약 user가 이미 존재하면 업데이트됨
    }

    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
                             .filter(user -> user.getPassword().equals(password));
    }
    
}

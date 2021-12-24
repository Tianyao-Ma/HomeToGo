package com.mty.hometogo.repository;

import com.mty.hometogo.model.Stay;
import com.mty.hometogo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StayRepository  extends JpaRepository<Stay, Long> {
    List<Stay> findByHost(User user);
}

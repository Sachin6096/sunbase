package com.example.Sunbase_project.Repository;

import com.example.Sunbase_project.Model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {
Optional<UserInfo> findByName(String username);
}


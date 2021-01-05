package com.tanzu.zen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SayingsRepository extends JpaRepository<Saying, Long> {
}

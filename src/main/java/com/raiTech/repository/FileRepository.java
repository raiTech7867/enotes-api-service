package com.raiTech.repository;

import com.raiTech.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDetails,Integer> {

}

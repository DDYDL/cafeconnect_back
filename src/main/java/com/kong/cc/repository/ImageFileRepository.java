package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.ImageFile;

public interface ImageFileRepository extends JpaRepository<ImageFile, Integer> {

}

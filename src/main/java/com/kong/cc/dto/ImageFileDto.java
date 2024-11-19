package com.kong.cc.dto;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.kong.cc.entity.Item;
import com.kong.cc.entity.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageFileDto {
	private Integer fileNum;
	
	private String fileContentType;
	private String fileDirectory;
	private String fileName;
	private Long fileSize;
	private Date fileUploadDate;
}

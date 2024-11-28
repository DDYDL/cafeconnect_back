package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

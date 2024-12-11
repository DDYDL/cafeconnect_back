package com.kong.cc.dto;

import com.kong.cc.entity.Menu;
import com.kong.cc.entity.Sales;
import com.kong.cc.entity.Store;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesDto {
	private Integer salesNum;
	private Integer storeCode;

	private Date salesDate;
	private String menuName;
	private String menuCode;

	private Integer salesCount;
	private Integer salesStatus;
	private Integer salesAmount;

	public Sales toEntity() {
		return Sales.builder()
				.salesDate(salesDate)
				.salesCount(salesCount)
				.salesAmount(salesAmount)
				.salesStatus(salesStatus)
				.menu(Menu.builder().menuCode(menuCode).build())
				.build();
	}
}

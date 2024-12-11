package com.kong.cc.entity;

import com.kong.cc.dto.SalesDto;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@DynamicInsert
public class Sales {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer salesNum;

	private Date salesDate;
	private Integer salesCount;
	private Integer salesStatus;
	private Integer salesAmount;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="storeCode")
	private Store storeSa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="menuCode")
	private Menu menu;

	public SalesDto toDto() {
		return SalesDto.builder()
				.salesDate(salesDate)
				.salesCount(salesCount)
				.salesAmount(salesAmount)
				.salesStatus(salesStatus)
				.storeCode(storeSa.getStoreCode())
				.menuCode(menu.getMenuCode())
				.menuName(menu.getMenuName())
				.build();
	}
}

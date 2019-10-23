package com.hospital.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the appointment database table.
 * 
 */
@Entity
@Table(name = "medicine")
@NamedQuery(name="medicine", query="SELECT a FROM Medicine a")
public class Medicine implements Serializable {

	private static final long serialVersionUID = 5207854116375888017L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="medicine_id")
	private Long medicineId;

	@Temporal(TemporalType.TIMESTAMP) 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="stock_date")
	private Date  stockDate;
	
	@JoinColumn(name="name") 
	private String name;
	
	@JoinColumn(name="price") 
	private BigDecimal price;
	
	@JoinColumn(name="quantity") 
	private int quantity;
	
	@Lob
	@JoinColumn(name="function") 
	private String function;

	@JoinColumn(name="status") 
	private boolean status;
	 
	@Column(name = "created_date")
	private String createdDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "modified_date")
	private String modifiedDate;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Medicine() {
	}
 
	public boolean isStatus() {
		return status;
	}

	public Long getMedicineId() {
		return medicineId;
	}


	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}


	public Date getStockDate() {
		return stockDate;
	}


	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getFunction() {
		return function;
	}


	public void setFunction(String function) {
		this.function = function;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}
 
}
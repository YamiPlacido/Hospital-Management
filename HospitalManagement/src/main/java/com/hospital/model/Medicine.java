package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the medicine database table.
 * 
 */
@Entity
@NamedQuery(name="Medicine.findAll", query="SELECT m FROM Medicine m")
public class Medicine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="medicine_id")
	private String medicineId;

	private String function;

	private String name;

	private BigDecimal price;

	private BigInteger quantity;

	private int status;

	@Column(name="stock_date")
	private Timestamp stockDate;

	//bi-directional many-to-one association to MedicineIllness
	@OneToMany(mappedBy="medicine")
	private List<MedicineIllness> medicineIllnesses;

	//bi-directional many-to-one association to PrescriptionMedicine
	@OneToMany(mappedBy="medicine")
	private List<PrescriptionMedicine> prescriptionMedicines;

	public Medicine() {
	}

	public String getMedicineId() {
		return this.medicineId;
	}

	public void setMedicineId(String medicineId) {
		this.medicineId = medicineId;
	}

	public String getFunction() {
		return this.function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigInteger getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getStockDate() {
		return this.stockDate;
	}

	public void setStockDate(Timestamp stockDate) {
		this.stockDate = stockDate;
	}

	public List<MedicineIllness> getMedicineIllnesses() {
		return this.medicineIllnesses;
	}

	public void setMedicineIllnesses(List<MedicineIllness> medicineIllnesses) {
		this.medicineIllnesses = medicineIllnesses;
	}

	public MedicineIllness addMedicineIllness(MedicineIllness medicineIllness) {
		getMedicineIllnesses().add(medicineIllness);
		medicineIllness.setMedicine(this);

		return medicineIllness;
	}

	public MedicineIllness removeMedicineIllness(MedicineIllness medicineIllness) {
		getMedicineIllnesses().remove(medicineIllness);
		medicineIllness.setMedicine(null);

		return medicineIllness;
	}

	public List<PrescriptionMedicine> getPrescriptionMedicines() {
		return this.prescriptionMedicines;
	}

	public void setPrescriptionMedicines(List<PrescriptionMedicine> prescriptionMedicines) {
		this.prescriptionMedicines = prescriptionMedicines;
	}

	public PrescriptionMedicine addPrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
		getPrescriptionMedicines().add(prescriptionMedicine);
		prescriptionMedicine.setMedicine(this);

		return prescriptionMedicine;
	}

	public PrescriptionMedicine removePrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
		getPrescriptionMedicines().remove(prescriptionMedicine);
		prescriptionMedicine.setMedicine(null);

		return prescriptionMedicine;
	}

}
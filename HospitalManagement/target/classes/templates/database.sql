drop table patient;
create table patient(
	patient_id int not null auto_increment primary key, 
	name varchar(255) not null, 
	DOB TIMESTAMP not null default CURRENT_TIMESTAMP(), 
	address varchar(255),
	email varchar(255),
	phone varchar(255),
	image_url blob,
	patient_note text,
	createdDate datetime,
	createdBy varchar(50),
	modifiedDate datetime,
	modifiedBy varchar(50)
);

insert into patient(name,DOB,address,email,phone) values 
('Nguyen Thi Thuy Trang', '2000-09-17', 'Nguyen Van Troi Street', 'trangnguyen121293@gmail.com','098737374');

create table speciality(
    speciality_id int primary key,
    name char(255) not null,
    description char(255) not null,
    note char(255),
	createdDate datetime,
	createdBy varchar(50),
	modifiedDate datetime,
	modifiedBy varchar(50)
);
INSERT INTO speciality(speciality_id, name, description, note) VALUES (1,'Speciality 1','Speciality 1','');
-- Table DOCTOR 

create table employee_position(
    employee_position_id int primary key,
    name char(255) not null,
    description char(255) not null,
    disable boolean,
	createdDate datetime,
	createdBy varchar(50),
	modifiedDate datetime,
	modifiedBy varchar(50)
);
INSERT INTO employee_position(employee_position_id, name, description, disable) VALUES (1,'doctor','a doctor',false);

create table employee(
    employee_id int primary key,
    speciality_id int not null,
    employee_position_id int not null,
    code char(11) not null unique,
    name char(255) not null,
    dob date not null,
    address char(255) not null,
    note char(255) not null,
    status char(1),
    phone char(20) not null,
    image_path char(255) not null,
	createdDate datetime,
	createdBy varchar(50),
	modifiedDate datetime,
	modifiedBy varchar(50),
    foreign key (speciality_id) references speciality(speciality_id),
    foreign key (employee_position_id) references employee_position(employee_position_id)
);

INSERT INTO employee(employee_id,speciality_id, employee_position_id, code, name, dob, address, note, phone, image_path) VALUES (1,1,1,'DOC1','John Cena','2019-12-11','abc street','',12345678,'image path');
   
   

create table illness_type(
    illness_type_id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    note varchar(255),
	createdDate datetime,
		createdBy varchar(50),
	modifiedDate datetime,
	modifiedBy varchar(50)
    );
insert into illness_type(name,note) VALUES
('Nose',''),
('Throat',''),
('Ear','');    
    
 create table speciality_illness_type(
    speciality_illness_type_id bigint AUTO_INCREMENT PRIMARY KEY,
    speciality_id int not null,
    illness_type_id bigint not null,
	createdDate datetime,
	createdBy varchar(50),
	modifiedDate datetime,
	modifiedBy varchar(50),
    FOREIGN KEY(speciality_id) REFERENCES speciality(speciality_id),
    FOREIGN KEY(illness_type_id) REFERENCES illness_type(illness_type_id)
    );
    
create table patient_illness_type(
    patient_illness_type_id bigint AUTO_INCREMENT PRIMARY KEY,
    patient_id int not null,
    illness_type_id bigint not null,
	createdDate datetime,
		createdBy varchar(50),
	modifiedDate datetime,
	modifiedBy varchar(50),
    FOREIGN KEY(patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY(illness_type_id) REFERENCES illness_type(illness_type_id)
    );
    

drop table appointment;
-- Table APPOINTMENT
create table appointment(
	app_id int auto_increment primary key, 
	patient_id int,
	employee_id int default null,
	date timestamp default CURRENT_TIMESTAMP(), 
	starttime varchar(255),
	location varchar(255),
	status boolean default false,
	note text,
	createdDate datetime,
	createdBy varchar(50),
	modifiedDate datetime,
	modifiedBy varchar(50),
	foreign key (patient_id) references patient(patient_id),
	foreign key (employee_id) references employee(employee_id)
);
insert into appointment(patient_id,employee_id,date,starttime,location,status) values
(1,1,'2019-12-11','11:30','Nguyen Van Troi Street',false);
 
--TABLE examination
drop table examination
create table examination(
	ex_id  int not null auto_increment primary key, 
	patient_id int not null, 
	employee_id int,
	name varchar(255),
	date TIMESTAMP not null default CURRENT_TIMESTAMP(),
	status boolean not null default false,
	price decimal,
	note text,
	app_id int,
	createdDate datetime,
	createdBy varchar(50),
	modifiedDate datetime,
	modifiedBy varchar(50),
	foreign key (patient_id) references patient(patient_id),
	foreign key (employee_id) references employee(employee_id),
	foreign key (app_id) references appointment(app_id)
);

insert into examination(ex_id,patient_id,name,price, date,status) values
(1,1,'Blood Test',12,'2019-09-10',true);


drop table invoice;
create table invoice(
    invoice_id int AUTO_INCREMENT primary KEY,
    id_id int not null,
    patient_id int not null,  
    createdDate TIMESTAMP default CURRENT_TIMESTAMP(),
    status boolean default false,
    amount decimal,
    foreign key (patient_id) references patient(patient_id), 
	foreign key (id_id) references invoiceDetail(id_id)
    );

insert into invoice(invoice_id,patient_id,id_id,createdDate,status) values
(1,1,1,'2019-12-11',false);

--them invoice_detail (total trong database)
drop table  invoice_detail;
create table invoiceDetail(
	id_id int AUTO_INCREMENT primary KEY,
	patient_id int not null, 
	ex_id int,
	quantity int,
	foreign key (patient_id) references patient(patient_id), 
	foreign key (ex_id) references examination(ex_id) 
	)   
	

insert into invoiceDetail(id_id,patient_id,ex_id,quantity) values
(1,1,1,1);
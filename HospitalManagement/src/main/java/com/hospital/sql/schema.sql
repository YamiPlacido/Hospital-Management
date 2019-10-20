--phuong

create table doctor_position(
    doctor_position_id int primary key AUTO_INCREMENT,
    name char(255) not null unique,
    description char(255),
    disable boolean
);
create table speciality(
    speciality_id int primary key AUTO_INCREMENT,
    position_id int not null,
    name char(255) not null unique,
    description char(255),
    foreign key (position_id) references doctor_position(doctor_position_id)
);
insert into doctor_position(name,disable) values
('Doctor','0'),
('Examinator','0');

create table employee(
    doctor_id int primary key AUTO_INCREMENT,
    speciality_id int not null,
    position_id int not null,
    code char(11) not null unique,
    first_name char(255) not null,
    last_name char(255) not null,
    password char(255) not null,
    dob date not null,
    address char(255) not null,
    email char(255) not null,
    gender char(1) not null, 
    note char(255),
    status char(1),
    phone char(20) not null,
    image_path char(255) not null,
    foreign key (speciality_id) references speciality(speciality_id),
    foreign key (position_id) references doctor_position(doctor_position_id)
);

insert into speciality(position_id,name) values
(1,'Gynecologist'),
(1,'Psychiatrist'),
(1,'Cardiologist'),
(1,'Urologist'),
(1,'Ophthalmologist'),
(1,'Dentist'),
(1,'Oncologist'),
(1,'Neurologist'),
(1,'General Surgery'),
(1,'Radiologist'),
(1,'Pediatrics'),
(1,'Physical Therapist'),
(2,'Type 1'),
(2,'Type 2');

insert into employee(speciality_id, position_id, code, first_name, last_name, password, dob, address, email, gender, status, phone, image_path) values
(1,1,'DR-0001','Cristina', 'Groves','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','f', '1','0983455570','employee-thumb-01.jpg'),
(2,1,'DR-0002','Marie', 'Wells','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','f', '1','0983455571','employee-thumb-02.jpg'),
(3,1,'DR-0003','Henry', 'Daniels','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','m', '1','0983455572','employee-thumb-03.jpg'),
(4,1,'DR-0004','Mark', 'Hunter','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','f', 'm','0983455573','employee-thumb-04.jpg'),
(5,1,'DR-0005','Michael', 'Sullivan','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','m', '1','0983455574','employee-thumb-05.jpg'),
(6,1,'DR-0006','Linda', 'Barrett','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','f', '1','0983455575','employee-thumb-06.jpg'),
(7,1,'DR-0007','Ronald', 'Jacobs','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','m', '1','0983455576','employee-thumb-07.jpg'),
(8,1,'DR-0008','Albert', 'Sandoval','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','m', '1','0983455577','employee-thumb-08.jpg'),
(9,1,'DR-0009','Diana', 'Bailey','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','f', '1','0983455578','employee-thumb-09.jpg'),
(10,1,'DR-0010','Shirley', 'Willis','','1999-12-12', 'United States, San Francisco','cristinagroves@example.com','f', '1','0983455579','employee-thumb-10.jpg');









CREATE TABLE user_group
(
	user_group_id varchar(10),
	name varchar(50),
	PRIMARY KEY (user_group_id)
);

CREATE TABLE users
(
	user_id bigint auto_increment,
	name varchar(20) NOT NULL,
	password varchar(200) NOT NULL,
	first_name varchar(100) NOT NULL,
	last_name varchar(100) NOT NULL,
	address varchar(200) NOT NULL,
	dob date NOT NULL,
	email varchar(100) NOT NULL,
	user_group_id varchar(10),
	PRIMARY KEY (user_id),
	FOREIGN KEY (user_group_id) REFERENCES user_group(user_group_id)
);

CREATE TABLE role
(
	role_id varchar(10),
	role_code varchar(50),
	detail varchar(50),
	PRIMARY KEY (role_id)
);

CREATE TABLE credential
(
	credential_id bigint AUTO_INCREMENT,
    user_group_id varchar(10),
	role_id varchar(10),
	PRIMARY KEY (credential_id),
	FOREIGN KEY (user_group_id) REFERENCES user_group(user_group_id),
	FOREIGN KEY (role_id) REFERENCES role(role_id)
);






create table examinator(
    examinator_id int AUTO_INCREMENT primary KEY,
    name varchar(255) not null
    );

create table patient(
	patient_id int not null auto_increment primary key, 
	name varchar(255) not null, 
	DOB datetime not null default CURRENT_TIMESTAMP(),
	address varchar(255) not null,
	email varchar(255) not null,
	phone varchar(255) not null,
	image_url blob,
	patient_note text
);

create table appointment(
	app_id int not null auto_increment primary key, 
	patient_id int not null,
	doctor_id int,
	date datetime not null default CURRENT_TIMESTAMP(),
	location varchar(255) not null,
	status boolean not null default false,
	note text,
	foreign key (patient_id) references patient(patient_id),
	foreign key (doctor_id) references employee(doctor_id)
);

create table examination(
	ex_id  int not null auto_increment primary key, 
	patient_id int not null, 
	examinator_id int,
	date datetime not null default CURRENT_TIMESTAMP(),
	status boolean not null default false,
	note text,
	app_id int,
	foreign key (patient_id) references patient(patient_id),
	foreign key (examinator_id) references examinator(examinator_id),
	foreign key (app_id) references appointment(app_id)
);



create table illness_type(
    illness_type_id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    note varchar(255)
    );
create table illness(
    illness_id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    description varchar(255) not null,
    season varchar(10) not null,
    illness_type_id bigint not null,
    FOREIGN KEY (illness_type_id) REFERENCES illness_type(illness_type_id)
);


create table prescription(
	app_id int not null primary key,
	payment decimal(13,4),
	foreign key (app_id) references appointment(app_id)
);

create table medicine (
        medicine_id bigint auto_increment primary key,
        name varchar(50) not null,
        function varchar(255) not null,
        price decimal(13,4) not null,
        quantity bigint not null,
        status int not null default 1,
        stock_date timestamp
);

create table prescription_medicine (
        pm_id int auto_increment primary key,
	app_id int not null,
        medicine_id bigint not null,
        quantity bigint not null,
        price decimal(13,4) not null,
        dosage varchar(255),
        FOREIGN KEY (app_id) REFERENCES appointment(app_id),
	FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id)
);

create table medicine_illness(
	medicine_illness_id int not null auto_increment primary key, 
	medicine_id bigint not null,
	illness_id bigint not null,
	foreign key (medicine_id) references medicine(medicine_id),
	foreign key (illness_id) references illness(illness_id)
);

create table symptom(
        symptom_id bigint auto_increment primary key,
        name varchar(255) not null,
        description varchar(255) not null,
        degree varchar(10) default 'medium'
);

create table speciality_illness_type(
    speciality_illness_type_id bigint AUTO_INCREMENT PRIMARY KEY,
    speciality_id int not null,
    illness_type_id bigint not null,
    FOREIGN KEY(speciality_id) REFERENCES speciality(speciality_id),
    FOREIGN KEY(illness_type_id) REFERENCES illness_type(illness_type_id)
    );

create table illness_medicine(
    illness_medicine_id int AUTO_INCREMENT PRIMARY KEY,
    illness_id bigint not null,
    symptom_id bigint not null,
    FOREIGN KEY(illness_id) REFERENCES illness(illness_id),
    FOREIGN KEY(symptom_id) REFERENCES symptom(symptom_id)    
    );

create table illness_symptom(
    illness_medicine_id int AUTO_INCREMENT PRIMARY KEY,
    illness_id bigint not null,
    symptom_id bigint not null,
    FOREIGN KEY(illness_id) REFERENCES illness(illness_id),
    FOREIGN KEY(symptom_id) REFERENCES symptom(symptom_id)    
    );

create table examination_result(
	app_id int not null primary key,
	detail varchar(255) not null,
	treatment_method varchar(255) not null,
	result_time datetime not null,
	followup_date date not null
);

create table diagnosis(
	diagnosis_id int auto_increment primary key,
	app_id int not null,
	illness_id bigint not null,
	degree varchar(25),
	dianogsis_time datetime not null default CURRENT_TIMESTAMP(),
    FOREIGN KEY(illness_id) REFERENCES illness(illness_id),
    FOREIGN KEY(app_id) REFERENCES appointment(app_id)
);

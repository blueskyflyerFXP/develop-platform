create table log (
	log_level varchar2(45),
	log_type varchar2(45),
	patient_sys_code varchar2(45),
	sys_code varchar2(45),
	method_code varchar2(45),
	timestamp varchar2(45),
	log_context varchar2(45),
	thead_id varchar2(45),
	class_name varchar2(45),
	method_name varchar2(45),
	file_name varchar2(45),
	line number(16),
	throwable varchar2(45),
	params varchar2(45),
	id varchar2(45)
);

comment on column log.id is '唯一ID';

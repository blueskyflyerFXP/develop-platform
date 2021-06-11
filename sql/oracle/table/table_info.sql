create table table_info (
	table_id varchar2(45),
	database_name varchar2(45),
	table_name varchar2(45),
	table_alias_name varchar2(45),
	table_comment varchar2(45),
	table_type varchar2(45),
	table_charset varchar2(45),
	table_engine varchar2(45),
	create_time varchar2(45),
	create_user varchar2(45),
	modify_time varchar2(45),
	modify_user varchar2(45),
	table_column_list varchar2(45)
);

alter table table_info add constraint table_info_primary_key primary key(table_id);

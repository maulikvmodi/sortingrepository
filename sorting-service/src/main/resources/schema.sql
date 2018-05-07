create table sortedvalues
(
	id integer not null auto_increment,
	originalstring varchar2(1000) not null,
	sortedstring varchar2(1000) not null,
	createddate timestamp,
	primary key(id)
);
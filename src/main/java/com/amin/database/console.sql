LOAD DATA LOCAL INFILE 'C:/Users/AminAbvaal/Desktop/javas/kasri/assets/data/00Z_01 _Jan _2017.csv'
INTO TABLE dat
FIELDS TERMINATED BY ';'
ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 0 LINES
(PRES,HGHT,TEMP,DWPT,RELH,MIXR,DRCT,SKNT,THTA,THTE,THTV);

LOAD DATA LOCAL INFILE 'C:/Users/AminAbvaal/Desktop/javas/kasri/assets/data/00Z_01 _Jan _2017.csv'
INTO TABLE table00z_01_jan_2017
FIELDS TERMINATED BY ';'
ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 2 LINES
(PRES,HGHT,TEMP,DWPT,RELH,MIXR,DRCT,SKNT,THTA,THTE,THTV);

create table f(a float);

insert into f (a) values (23.343434343);


select DRCT  from table100z_01_jan_2017 where HGHT='20091';
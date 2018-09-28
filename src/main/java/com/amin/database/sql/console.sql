
# select * from armenia_37789_00z_05_oct_1973 where  SKNT<96;

#
insert into armenia
select 37789,'1973-02-10','00Z' ,
         PRES, HGHT, TEMP, DWPT, RELH, MIXR, DRCT, SKNT, THTA, THTE, THTV
    from armenia_37789_00z_02_oct_1973;

# select PRES, HGHT, TEMP, DWPT, RELH, MIXR, DRCT, SKNT, THTA, THTE, THTV
# from armenia_37789_00z_05_oct_1973;
select *
from armenia where HGHT<1115 and HGHT>1111;





-- auto-generated definition
create table armenia
(
    station  int(5)                   not null,
    col_date date                     not null,
    zone     varchar(3) default '00Z' not null,
    PRES     float                    null,
    HGHT     float                    not null,
    TEMP     float                    null,
    DWPT     float                    null,
    RELH     float                    null,
    MIXR     float                    null,
    DRCT     float                    null,
    SKNT     float                    null,
    THTA     float                    null,
    THTE     float                    null,
    THTV     float                    null,
    primary key (station, col_date, zone, HGHT)
);


select avg(SKNT)
from armenia where HGHT<4323 and HGHT>=1000 and col_date ='1973-01-03';



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

DELETE FROM iran__islamic_rep
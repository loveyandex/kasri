#!/usr/bin/env python
from ecmwfapi import ECMWFDataServer
    
server = ECMWFDataServer()
    
server.retrieve({
    'stream'    : "oper",
    'levtype'   : "sfc",
    'param'     : "167.128",
    'repres'    : "gg",
    'step'      : "0",
    'time'      : "12",
    'date'      : "1986-08-01/to/1986-08-31",
    'dataset'   : "era15",
    'type'      : "an",
    'class'     : "er",
    'target'    : "Loera15_1986-08-01to1986-08-31_12.grib"
})
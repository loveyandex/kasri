from ecmwfapi import ECMWFDataServer
    
server = ECMWFDataServer()
    
server.retrieve({
    'stream'    : "oper",
    'levelist'  : "1/2/3/5/7/10/20/30/50/70/100/150/200/250/300/400/500/600/700/775/850/925/1000",
    'levtype'   : "pl",
    'param'     : "130.128/131.128/132.128/157.128",
    'dataset'   : "era40",
    'step'      : "0",
    'time'      : "00/06/12/18",
    'date'      : "2002-08-01/to/2002-08-31",
    'type'      : "an",
    'class'     : "e4",
    'target'    : "era40_2002-08-01to2002-08-31_00061218.grib"
})
from ecmwfapi import ECMWFDataServer

# To run this example, you need an API key
# available from https://api.ecmwf.int/v1/key/

server = ECMWFDataServer()
server.retrieve({
    'dataset' : 'tigge',
    'step'    : '24',
    'number'  : 'all',
    'levtype' : 'sl',
    'date'    : '20071001',
    'time'    : '00',
    'origin'  : 'all',
    'type'    : 'pf',
    'param'   : 'tp',
    'area'    : '70/-130/30/-60',
    'grid'    : '2/2',
    'target'  : 'data.grib'
})
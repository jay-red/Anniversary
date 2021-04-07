# Python script to analyze OldPBR packs

import os
import sys
from PIL import Image, ImageColor

def join_path( r, *p_list ):
    s = r
    for p in p_list:
        s = os.path.join( s, p )
    return s
        

def parse_specular( rp_path, name ):
    emissive_path = name + "_emissive.png"
    emissive = Image.open( emissive_path ).convert( "RGBA" )
    emissive_data = list( emissive.getdata() )
    emissive_height = emissive.height
    emissive_width = emissive.width
    emissive.close()

    specular_data = []
    for y in range( emissive_height ):
        for x in range( emissive_width ):
            px = emissive_data[ y * emissive_width + x ]
            if px[ 3 ] > 0:
                specular_data.append( ( 0, 0, 255, 255 ) )
            else:
                specular_data.append( ( 0, 0, 0, 0 ) )
    specular = Image.new("RGBA", ( emissive_width, emissive_height ), ( 0, 0, 0, 0 ) )
    specular.putdata( specular_data );
    specular.save( name + "_s.png" )

parse_specular( ".", "guardian_elder2" )

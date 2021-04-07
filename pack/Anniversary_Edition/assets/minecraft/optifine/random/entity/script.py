# Python script to analyze OldPBR packs

import os
import sys
from PIL import Image, ImageColor
from shutil import copyfile

def join_path( r, *p_list ):
    s = r
    for p in p_list:
        s = os.path.join( s, p )
    return s
        

def parse_specular( file_path ):
    specular_data = []
    
    albedo_path = file_path + ".png"
    albedo = Image.open( albedo_path ).convert( "RGBA" )
    albedo_data = list( albedo.getdata() )
    albedo_height = albedo.height
    albedo_width = albedo.width
    albedo.close()
    for y in range( albedo_height ):
        for x in range( albedo_width ):
            px = albedo_data[ y * albedo_width + x ]
            if px[ 3 ] > 0:
                specular_data.append( ( 192, 255, 0, 255 ) )
            else:
                specular_data.append( ( 0, 0, 0, 0 ) )
    
    emissive_path = file_path + "_emissive.png"
    emissive = Image.open( emissive_path ).convert( "RGBA" )
    emissive_data = list( emissive.getdata() )
    emissive_height = emissive.height
    emissive_width = emissive.width
    emissive.close()

    for y in range( emissive_height ):
        for x in range( emissive_width ):
            idx = y * emissive_width + x
            px = emissive_data[ idx ]
            if px[ 3 ] > 0:
                specular_data[ idx ] = ( 0, 0, 255, 255 )
    specular = Image.new("RGBA", ( emissive_width, emissive_height ), ( 0, 0, 0, 0 ) )
    specular.putdata( specular_data );
    specular.save( file_path + "_s.png" )

parse_specular( "guardian_elder" )

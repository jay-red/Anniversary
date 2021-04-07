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
    emissive_path = file_path + "_emissive.png"
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
    specular.save( file_path + "_s.png" )

def parse_rp( base_path ):
    rp_path = join_path( base_path )
    smoothness_dict = {}
    for root, dirs, files in os.walk( rp_path, topdown=False ):
        for name in files:
            full_path = join_path( root, name )
            if name.find( "_emissive.png.mcmeta" ) != -1 and name[ -20 :  ] == "_emissive.png.mcmeta": 
                copyfile( full_path, full_path[ : -20 ] + "_s.png.mcmeta" )
            elif name.find( "_emissive.png" ) != -1 and name[ -13 : ] == "_emissive.png":
                parse_specular( full_path[ : -13 ] )

parse_rp( "." )

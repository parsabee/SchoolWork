//Parsa Bagheri; CIS314; assignment2_2.71

//A) the code is incorrect because it doesn't take into account the negative numbers
//B)
#include <stdio.h>
typedef unsigned packed_t;

int xbyte(packed_t word, int bytenum)
{
    // Move special byte to the most significant byte
    int byte = word << ((3 - bytenum) << 3);
    // sign extend to 32 bit int(signed)
    return byte >> 24;
}

int main() {
    printf("0x%X\n",xbyte(0x80030201, 0));
    printf("0x%X\n",xbyte(0x80030201, 1));
    printf("0x%X\n",xbyte(0x80030201, 2));
    printf("0x%X\n",xbyte(0x80030201, 3));
}
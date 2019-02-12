#include <stdio.h>
unsigned replace_byte(unsigned x, int i, unsigned char b){
    unsigned int y = 0xFF;
    y = y<<i*8;
    y = ~y;
    x = x&y;
    unsigned int z = b<< i*8;
    unsigned int result = x | z;
    printf("result: 0x%X\n", result);
}

int main() {
    replace_byte(0x12345678, 0, 0xAB);
    replace_byte(0x12345678, 2, 0xAB);
}
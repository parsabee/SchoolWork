#include <stdio.h>

typedef unsigned char *byte_pointer;
void show_bytes(byte_pointer start, int len) {
    int i;
    for (i = 0; i < len; i++)
        printf(" %.2x", start[i]);
    printf("\n");
}
void show_short(short x){
    show_bytes((byte_pointer) &x, sizeof(short));
}
void show_long(long x){
    show_bytes((byte_pointer) &x, sizeof(long));
}
void show_double(double x){
    show_bytes((byte_pointer) &x, sizeof(double));
}
void test_show_bytes(int val) {
    int ival = val;
    short sval = (short) ival;
    long lval = (long) ival;
    double dval = (double) ival;
    show_short(sval);
    show_long(lval);
    show_double(dval);
}

int main() {
    int val = 0x87654321;
    byte_pointer valp = (byte_pointer) &val;
    test_show_bytes(val);
}
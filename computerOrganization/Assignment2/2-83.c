//Parsa Bagheri; CIS 314; Assignment_2 2-83;

#include <stdio.h>

unsigned f2u(float f){
    return *((unsigned*)&f);
}
int float_le(float x, float y) {
    unsigned ux = f2u(x);
    unsigned uy = f2u(y);
    /* Get the sign bits */
    unsigned sx = ux >> 31;
    unsigned sy = uy >> 31;
    /* Give an expression using only ux, uy, sx, and sy */
    // returns 1 if the second argument is less than or equal to the first argument, otherwise returns 0.
    return (!(ux << 1 ^ ~0) && !(uy << 1 ^ ~0)) || /* ux = uy = +0 or -0 */
           (!sx && sy) ||                          /* sx >= 0, sy < 0    */
           (!sx && !sy && ux >= uy) ||             /* x >= 0, y >= 0     */
           (sx && sy && ux <= uy);                 /* x < 0, y < 0       */
}

int main() {
    //function main tests whether the second value is less than or equal to the first value
    printf("%d\n", float_le(5.5, 4.5));
    printf("%d\n", float_le(5.5, 6.5));
    printf("%d\n", float_le(5.5, -4.5));
    printf("%d\n", float_le(-5.5, -4.5));
    printf("%d\n", float_le(-5.5, 4.5));
    printf("%d\n", float_le(-5.5, -6.5));
    printf("%d\n", float_le(-5.5, -5.5));
    printf("%d\n", float_le(6.5, 6.5));
    printf("%d\n", float_le(-5.5, 5.5));
    printf("%d\n", float_le(6.5, -6.5));
}
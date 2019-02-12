#include <stdio.h>
#include <stdlib.h>
typedef unsigned char *byte_pointer;

struct Block{
    unsigned char valid;
    unsigned int tag;
    unsigned int value;
};
unsigned int getTag(unsigned int address) {
    //getting the tag of the address
    return (address >> 6);
}
unsigned int getSetNumber(unsigned int address) {
    //getting the set index of the address
    return (address >> 2) & 3;
}
unsigned int getOffset(unsigned int address) {
    //getting the offset at the address
    return address & 1;
}
void show_bytes(byte_pointer start, int len) {
    //showing the bytes of the value.
    int i;
    for (i = 0; i < len; i++)
        printf(" %.2x", start[i]);
    printf("\n");
}
void show_byte(byte_pointer start, int offset) {
    //showing the byte at a certain offset.
    printf(" %.2x\n", start[offset]);
}


int main(){
    int size = 16;
    //allocating 16 blocks(1 block per set)
    struct Block* cache =malloc(size * sizeof(struct Block));
    unsigned int i;
    for (i=0; i < 16; i++) {
        cache[i].valid = 0;
    }

    char c = 0;
    unsigned int setNumber;
    unsigned int offset;
    unsigned int tag;
    unsigned int a;
    unsigned int v;
    printf("Enter 'r' for read, 'w' for write, 'p' to print, 'q' to quit: ");
    while(c != 'q'){
        scanf("%c", &c);
        if(c == 'r'){
            //if read
            printf("Enter 32-bit unsigned hex address: ");
            scanf("%x", &a);
            //hex address
            setNumber = getSetNumber(a);
            offset = getOffset(a);
            tag = getTag(a);

            printf("looking for set: %x - tag: %x \n", setNumber, tag);

            struct Block* block = &cache[setNumber];

            if(block->valid) {

                if(block->valid) {
                //checking if the block is valid
                    printf("found set: %x - tag: %x -offset: %x - valid: %c - value: ",setNumber, block->tag, offset, block->valid);
                    show_byte((byte_pointer)&block->value, offset);
                    if(tag == block->tag)
                        //checking if tags match
                        printf("hit!\n");
                    else
                        printf("tags don't match - miss!\n");
                }
            }
            else
                printf("no valid set found - miss!\n");
            printf("Enter 'r' for read, 'w' for write, 'p' to print, 'q' to quit: ");
            scanf("%c", &c);
        }
        else if(c == 'w') {
            //if write
            printf("Enter 32-bit unsigned hex address:");
            scanf("%x", &a);
            //hex address
            printf("Enter 32-bit unsigned hex value:");
            scanf("%x", &v);
            // hex value
            setNumber = getSetNumber(a);
            offset = getOffset(a);
            tag = getTag(a);
            struct Block * block = &cache[setNumber];
            if(block->valid) {
                //block is valid and needs to evict
                //retrieve the current block value;
                printf("evicting block - set: %x - tag: %x - valid: %c - value:",setNumber, block->tag, block->valid);
                show_bytes((byte_pointer)&block->value, sizeof(int));
            }
            block->valid = '1';
            block->value = v;
            block->tag = tag;
            //retrieve the value we write
            //print out all the info
            printf("wrote set: %x - tag: %x - valid: %c - value: ",setNumber, block->tag, block->valid);
            show_bytes((byte_pointer)&v, sizeof(int));
            printf("Enter 'r' for read, 'w' for write, 'p' to print, 'q' to quit: ");
            scanf("%c", &c);
        }

        else if(c == 'p') {
            //if print
            unsigned int i;
            for(i = 0; i < 16; i++) {
                struct Block * block = &cache[i];
                if(block->valid) {
                    //block is valid
                    //retrieve value of current block
                    //print out all info
                    printf("set: %x - tag: %x - valid: %c - value: ",i, block->tag, block->valid);
                    show_bytes((byte_pointer)&block->value, sizeof(int));
                }
            }
            printf("Enter 'r' for read, 'w' for write, 'p' to print, 'q' to quit: ");
            scanf("%c", &c);
        }
        else if(c =='q'){
            //if q: quit the program.
            continue;
        }
        else{
            //if characters other than "w, r, p, q" entered.
            printf("Enter 'r' for read, 'w' for write, 'p' to print, 'q' to quit: ");
            scanf("%c", &c);
        }
    }
    exit(0);
}


#include <stdio.h>
#include <stdlib.h>


 int mod(char a1)
{
  int v1; // r3

  if ( a1 )
    v1 = 1;
  else
    v1 = 66;
  return v1;
}

int min(int a1, int a2)
{
  int v2; // r3

  if ( a1 <= a2 )
    v2 = a2 % 2 + 1;
  else
    v2 = a1 - a2;
  return v2;
}

int sum(int a1, int a2)
{
  return -a2;
}



unsigned short magic4(unsigned short a1)
{
  unsigned short v2; // [sp+7h] [bp-Dh]
  int v3; // [sp+Ch] [bp-8h]

  v2 = a1;
  v3 = 0;
  while ( v3 <= 2 )
  {
    ++v3;
    --v2;
  }
  return v2;
}
unsigned short magic3(unsigned short a1)
{
  unsigned short v1; // r0

  v1 = magic4(a1);
  return (unsigned short)(v1 - sum(v1, 1));
}

unsigned short magic2(unsigned short a1)
{
  unsigned short v1; // r0

  v1 = magic3(a1);
  return (unsigned short)(mod(v1) + v1);
}

unsigned short magic(unsigned short a1)
{
  unsigned short v1; // ST07_1

  v1 = magic2(a1);
  return (unsigned short)(min(3, 2) + v1);
}
int main () {
  unsigned short v20 = magic(105^0x27),v22 = magic(2*v20-51),v23=magic(0x42),v24=magic(8*(5-1)|1);
  printf("%c",magic(105-8));
  printf("%c",v20);
  printf("%c",magic(105+11));
  printf("%c",magic(2*v20-51));
  printf("%c",magic(0x42));
  printf("%c",magic(8*(5-1)|1));
  unsigned short v27 = v23+v24+v22;
  unsigned short v26 = (v27^(v22+v24+66))+101;
  printf("%c",magic(v26));
}


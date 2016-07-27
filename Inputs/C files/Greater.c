#include<stdio.h>
int main(void)
{
	int a, b, c;
	scanf("%d %d",&a,&b);
	if(a>b)
	{
		c=a;
	}
	else
	{   
	   c=b;
	}	
	  printf("The greater in %d and %d is %d",a,b,c);
	return 0;
}

#include<stdio.h>

int main(void)
{
	int a, b, c, d;
	scanf("%d %d %d",&a,&b,&d);

	c=0;      

  	if(a>b)
	{
		if(a>d)
		{
		   c=a;
		}
		else
		{
		   c=d;
		}	
	}
	else
	{   
	   if(b>d)
	   {
	      c=b;
	   }
	   else
	   {
		  c=d;
	   }
	}	

	printf("The greatest in %d, %d, and %d is %d",a,b,d,c);	

	return 0;
}

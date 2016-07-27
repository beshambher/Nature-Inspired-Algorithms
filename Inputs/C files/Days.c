#include<stdio.h>
#include<conio.h>
int main(void)
{
    int n;
    printf("Enter number of week's day(1-7): ");
    scanf("%d",&n);   
	printf("\n\nThe day is:  "); 
    switch(n)
    {
		case 1: puts("Sunday"); break;
		case 2: puts("Monday"); break;
		case 3: puts("Tuesday"); break;
		case 4: puts("Wednesday"); break;
		case 5: puts("Thursday"); break;
		case 6: puts("Friday"); break;
		case 7: puts("Saturay"); break;
		default: puts("Not Valid");
    }    
    getch();
    return 0;
}         

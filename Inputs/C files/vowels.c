#include<stdio.h>
int main(void)
{
	char C;
	printf("Enter the alphabet: ");
	scanf("%c",&C);
		if(((C>='a')&&(C<='z'))||((C>='A')&&(C<='Z'))) {
			switch(C)
			{
				case 'a':
				case 'A': printf("Alphabet '%c' is a vowel.", C);
						  break;
				case 'e':
				case 'E': printf("Alphabet '%c' is a vowel.", C);
						  break;
				case 'i':
				case 'I': printf("Alphabet '%c' is a vowel.", C);
						  break;
				case 'o':
				case 'O': printf("Alphabet '%c' is a vowel.", C);
						  break;
				case 'u':
				case 'U': printf("Alphabet '%c' is a vowel.", C);
						  break; 
				default:  printf("Alphabet '%c' is not a vowel.", C);
			}
		}
		else {
			    printf("Alphabet '%c' is an invalid alphabet.", C);
		}
	return 0;
}

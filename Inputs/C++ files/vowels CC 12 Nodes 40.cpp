#include<iostream>
using namespace std;
int main()
{
	char a;	
	cout<<"Enter the alphabet: ";
	cin>>a;
	if(((a>='a')&&(a<='z'))||((a>='A')&&(a<='Z')))
	{
		cout<<"Alphabet '";
		switch(a)
		{
			case 'a': cout<<a<<"' is a lowercase vowel."<<endl;
					  break;
			case 'A': cout<<a<<"' is a uppercase vowel."<<endl;
					  break;
			case 'e': cout<<a<<"' is a lowercase vowel."<<endl;
					  break;
			case 'E': cout<<a<<"' is a uppercase vowel."<<endl;
					  break;
			case 'i': cout<<a<<"' is a lowercase vowel."<<endl;
					  break;
			case 'I': cout<<a<<"' is a uppercase vowel."<<endl;
					  break;
			case 'o': cout<<a<<"' is a lowercase vowel."<<endl;
					  break;
			case 'O': cout<<a<<"' is a uppercase vowel."<<endl;
					  break;
			case 'u': cout<<a<<"' is a lowercase vowel."<<endl;
					  break;
			case 'U': cout<<a<<"' is a uppercase vowel."<<endl;
					  break; 
			default: cout<<a<<"' is not a vowel."<<endl;
					  break;
		}
	}
	else
	{
		cout<<"Alphabet '"<<a<<"' is an invalid alphabet."<<endl;
	}
	return 0;
}

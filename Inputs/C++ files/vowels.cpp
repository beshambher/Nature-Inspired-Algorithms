#include<iostream>
using namespace std;
int main()
{
	char a;	
	cout<<"Enter the alphabet: ";
	cin>>a;
	if(((a>='a')&&(a<='z'))||((a>='A')&&(a<='Z'))) {
		cout<<"Alphabet '";
		switch(a)
		{
			case 'a':
			case 'A': cout<<a<<"' is a vowel."<<endl;
					  break;
			case 'e':
			case 'E': cout<<a<<"' is a vowel."<<endl;
					  break;
			case 'i':
			case 'I': cout<<a<<"' is a vowel."<<endl;
					  break;
			case 'o':
			case 'O': cout<<a<<"' is a vowel."<<endl;
					  break;
			case 'u':
			case 'U': cout<<a<<"' is a vowel."<<endl;
					  break; 
			default: cout<<a<<"' is not a vowel."<<endl;
					  break;
		}
	}
	else {
		cout<<"Alphabet '"<<a<<"' is an invalid alphabet."<<endl;
	}
	return 0;
}

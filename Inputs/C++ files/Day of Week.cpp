#include<iostream>
using namespace std;
#include<conio.h>
int main()
{
    int n;
    cout<<"Enter number of week's day(1-7): ";
    cin>>n;
	if((n>0)&&(n<8)) {   
		cout<<"\nThe day is: "; 
	    switch(n)
	    {
			case 1: cout<<"\t"<<"Sunday"<<endl;
					 break;
			case 2: cout<<"\t"<<"Monday"<<endl;
					 break;
			case 3: cout<<"\t"<<"Tuesday"<<endl;
					 break;
			case 4: cout<<"\t"<<"Wednesday"<<endl;
					 break;
			case 5: cout<<"\t"<<"Thursday"<<endl;
					 break;
			case 6: cout<<"\t"<<"Friday"<<endl;
					 break;
			case 7: cout<<"\t"<<"Saturday"<<endl;
					 break;
			default: cout<<"\t"<<"Invalid"<<endl;
	    }  
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}  
    return 0;
}

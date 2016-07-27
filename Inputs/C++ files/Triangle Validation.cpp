#include<iostream>
using namespace std;
int main()
{
	int a, b, c;
	cout<<"Enter the sides of the triangle (between 1-100): ";
	cin>>a>>b>>c;
	if((a>0)&&(a<101)&&(b>0)&&(b<101)&&(c>0)&&(c<101)) {
		if((a+b)>c) {
			if((a+c)>b)	{
				if((b+c)>a)	{
					cout<<"Valid triangle."<<endl;
				}
				else {
					cout<<"Inalid triangle."<<endl;
				}
			}
			else {
					cout<<"Inalid triangle."<<endl;
			}
		}
		else {
					cout<<"Inalid triangle."<<endl;
		}
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}
	return 0;
}

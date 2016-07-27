#include<iostream>
using namespace std;
int main()
{
	double a, b, c, a1, a2, a3;
	int valid=0;
	cout<<"Enter the sides of the triangle (between 1-100)"<<endl;
	cout<<"Enter first side of the triangle: ";
	cin>>a;
	cout<<"Enter second side of the triangle: ";
	cin>>b;
	cout<<"Enter third side of the triangle: ";
	cin>>c;
	if((a>0)&&(a<101)&&(b>0)&&(b<101)&&(c>0)&&(c<101)) {
		if((a+b)>c) {
			if((a+c)>b) {
				if((b+c)>a) {
					valid=1;
				} else {
					valid=-1;
				}
			} else {
				valid=-1;
			}
		} else {
			valid=-1;
		}
	}
    if(valid==1) {
	   	a1=(a*a+b*b)/(c*c);
	   	a1=(a*a+c*c)/(b*b);
	   	a1=(c*c+b*b)/(a*a);
	   	if(a1<1||a2<1||a3<1) {
	   		cout<<"Obtuse Angled Triangle."<<endl;
		}
		else if(a1==1||a2==1||a3==1) {
	   		cout<<"Right Angled Triangle."<<endl;
		}
		else {
			cout<<"Acute Angled Triangle."<<endl;
		}
    }
    else if(valid==-1) {
   		cout<<"Invalid Triangle"<<endl;
    }
	else {
		    cout<<"Inputs out of range."<<endl;
	}
	return 0;
}

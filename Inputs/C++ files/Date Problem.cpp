#include<iostream>
using namespace std;
int main()
{
	int D, M, Y, y1, m, date, valid=0, leap=0;
	cout<<"Enter Day: ";
	cin>>D;
	cout<<"Enter Month: ";
	cin>>M;
	cout<<"Enter Year (between 1900 and 2058): ";
	cin>>Y;
	if(Y>=1900 && Y<=2058) {
		if(Y%4==0) {
			leap=1;
			if((Y%100)==0 && (Y%400)!=0) {
				leap=0;
			}
		}		
		if(M==4 || M==6 || M==9 || M==11) {
			if(D>=1 && D<=30) {
				valid=1;
			}
			else {
				valid=0;
			}
		}
		else if(M==2) {
			if(leap==1) {
				if(D>=1 && D<=29) {
					valid=1;
				}
				else {
					valid=0;
				}
			}
			else {
				if(D>=1 && D<=28) {
					valid=1;
				}
				else {
					valid=0;
				}
			}
		 }
		 else if(M>=1 && M<=12) {
			if(D>=1 && D<=31) {
				valid=1;
			}
			else {
				valid=0;
			}
		}
		 else {
			valid=0;
		 }
   }
   else {
		valid=0;
   }	
	if(valid==1) {
		cout<<"\n\tThe date is valid: "<<D<<"/"<<M<<"/"<<Y<<endl;
	}
	else {
		cout<<"\n\tInvalid Date!!!\n\n"<<endl;
	}	
}

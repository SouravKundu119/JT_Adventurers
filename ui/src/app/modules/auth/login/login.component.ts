import {
  Component,
  ElementRef,
  HostListener,
  ViewChild,
  signal,
} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { BookingService } from 'src/app/core/services/booking.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  @ViewChild('userName') userNameRef!: ElementRef;
  @ViewChild('password') passwordRef!: ElementRef;
  @ViewChild('eyeL') eyeL!: ElementRef;
  @ViewChild('eyeR') eyeR!: ElementRef;
  @ViewChild('handL') handL!: ElementRef;
  @ViewChild('handR') handR!: ElementRef;

  users: any = [];
  login!: FormGroup;
  isLoggedIn: boolean = false;

  result: any;

  constructor(
    private authService: AuthService,
    private router: Router,
    private bookingService: BookingService
  ) {
    this.login = new FormGroup({
      userName: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }
  ngOnInit() {}

  isLoggedInFun() {
    if (this.isLoggedIn) {
      this.router.navigateByUrl('');
    } else {
      alert('check userName & password');
    }
  }

  onLogin() {
    // this.authService.getUserByCode(this.login.value.userName).subscribe((res) => {
    //   console.log(res);
    //   this.result = res
    //   console.log(this.result);
    //   if(this.result[0].password == this.login.value.password){
    //     console.log("if called");
    //     sessionStorage.setItem('userName',this.result[0].userName)
    //     sessionStorage.setItem('role',this.result[0].role)
    //     this.router.navigate([''])
    //   }else{
    //     alert('contact admin')
    //   }
    //   this.authService.isLoggedIn.next(this.isLoggedIn);
    //   // this.isLoggedInFun();
    // });

    // ===============
    // this.authService.loginUser(this.login.value).subscribe(
    //   (res) => {
    //     console.log(res.status);
    //     if (res.status === 200) {
    //       this.result = res.body;
    //       console.log(this.result);
    //       sessionStorage.setItem('_token', this.result.token);
    //       sessionStorage.setItem('role', this.result.role);
    //       sessionStorage.setItem('userName', this.login.value.userName);
    //       // this.authService.updateLoginState(true);
    //       this.authService.updateLoginStateTest(true);
    //       this.authService.updateLoggedHaiKya(true);
    //       if(this.bookingService.navigatedFromTourList()){ // has coming from booking page & after login it will redirect to again booking page
    //         this.router.navigateByUrl('/booking')
    //       }else{
    //         this.router.navigateByUrl('')
    //       }
    //       // if(this.result.token){
    //       //   console.log("token called");
    //       //   // this.authService.isLoggedIn.next(true);
    //       //   this.authService.updateLoginState(true);

    //       // }
    //     }
    //   },
    //   (error) => {
    //     alert(error);
    //   }
    // );
    // ===============
    this.authService.loginUser(this.login.value).subscribe(
      (res) => {
        console.log(res);
        // if(res.status == 200){
          
        // }
        this.result = res;
        console.log(this.result);
        sessionStorage.setItem('_token', this.result.body.token);
        sessionStorage.setItem('role', this.result.body.role);
        sessionStorage.setItem('userName', this.login.value.userName);
        sessionStorage.setItem('_isLoggedIn',"true")
        // this.authService.updateLoginState(true);
        this.authService.updateLoginStateTest(true);
        if(this.bookingService.navigatedFromTourList()){ // has coming from booking page & after login it will redirect to again booking page
          this.router.navigateByUrl('/booking')
        }else{
          this.router.navigateByUrl('')
        }
        
      }
    );
  }

  normalEyeStyle(): void {
    this.eyeL.nativeElement.style.left = '0.6em';
    this.eyeL.nativeElement.style.top = '0.6em';
    this.eyeR.nativeElement.style.right = '0.6em';
    this.eyeR.nativeElement.style.top = '0.6em';
  }

  normalHandStyle(): void {
    this.handL.nativeElement.style.height = '2.81em';
    this.handL.nativeElement.style.top = '8.4em';
    this.handL.nativeElement.style.left = '7.5em';
    this.handL.nativeElement.style.transform = 'rotate(0deg)';

    this.handR.nativeElement.style.height = '2.81em';
    this.handR.nativeElement.style.top = '8.4em';
    this.handR.nativeElement.style.right = '7.5em';
    this.handR.nativeElement.style.transform = 'rotate(0deg)';
  }

  @HostListener('document:click', ['$event'])
  onClick(event: Event): void {
    const clickedElem = event.target as HTMLElement;
    if (
      clickedElem !== this.userNameRef.nativeElement &&
      clickedElem !== this.passwordRef.nativeElement
    ) {
      this.normalEyeStyle();
      this.normalHandStyle();
    }
  }

  onFocusUserName(): void {
    this.eyeL.nativeElement.style.left = '0.75em';
    this.eyeL.nativeElement.style.top = '1.12em';
    this.eyeR.nativeElement.style.right = '0.75em';
    this.eyeR.nativeElement.style.top = '1.12em';
    this.normalHandStyle();
  }

  onFocusPassword(): void {
    this.handL.nativeElement.style.height = '6.56em';
    this.handL.nativeElement.style.top = '3.87em';
    this.handL.nativeElement.style.left = '11.75em';
    this.handL.nativeElement.style.transform = 'rotate(-155deg)';

    this.handR.nativeElement.style.height = '6.56em';
    this.handR.nativeElement.style.top = '3.87em';
    this.handR.nativeElement.style.right = '11.75em';
    this.handR.nativeElement.style.transform = 'rotate(155deg)';
    this.normalEyeStyle();
  }
}
